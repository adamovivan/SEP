package rs.ac.uns.ftn.authentication_service.util;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.*;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.bc.BcPKCS12PBEInputDecryptorProviderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.authentication_service.exceptions.BadRequestException;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class Util {

    private static char[] pass = "123".toCharArray();

    public static final String COUNTRY = "2.5.4.6";
    public static final String STATE = "2.5.4.8";
    public static final String LOCALE = "2.5.4.7";
    public static final String ORGANIZATION = "2.5.4.10";
    public static final String COMMON_NAME = "2.5.4.3";

    public static String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) {

        RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));
        String retVal = null;
        for (RDN item : rdnArray) {
            retVal = item.getFirst().getValue().toString();
        }

        return retVal;
    }

    public static PKCS10CertificationRequest getCSR(MultipartFile file) {

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            Reader pemReader = new BufferedReader(new InputStreamReader(in));
            PEMParser pemParser = new PEMParser(pemReader);

            Object parsedObj = pemParser.readObject();

            if (parsedObj instanceof PKCS10CertificationRequest) {
                return (PKCS10CertificationRequest) parsedObj;
            }
        } catch (IOException ex) {
            throw new BadRequestException("Error while reading file!");
        }
        return null;
    }

    public static X509Certificate loadCert(String path) {
        try (InputStream in = new ClassPathResource(path).getInputStream()) {
            X509CertificateHolder holder = (X509CertificateHolder) new PEMParser(
                    new InputStreamReader(in)).readObject();

            CertificateFactory cf = CertificateFactory.getInstance("X509",
                    new BouncyCastleProvider());

            return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(
                    holder.getEncoded()
            ));

        } catch (IOException e) {
            throw new BadRequestException("Error while loading certificate!");
        } catch (CertificateException e) {
            throw new BadRequestException("Certificate error!");
        }
    }

    public static PrivateKey readKey(String path) {
        try {
            PEMParser keyReader = new PEMParser(new InputStreamReader(
                    new ClassPathResource(path).getInputStream()
            ));

            Object object = keyReader.readObject();

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                PrivateKeyInfo pkInfo = ((PKCS8EncryptedPrivateKeyInfo) object).decryptPrivateKeyInfo(new BcPKCS12PBEInputDecryptorProviderBuilder().build(pass));
                return converter.getPrivateKey(pkInfo);
            }

            return null;
        } catch (IOException e) {
            throw new BadRequestException("Error while loading private key!");
        } catch (PKCSException e) {
            throw new BadRequestException("Error while decripting private key!");
        }
    }

    public static String signCSR(Reader pemcsr, PrivateKey cakey, X509Certificate cacert){
        try {
            PEMParser reader = new PEMParser(pemcsr);
            PKCS10CertificationRequest csr = (PKCS10CertificationRequest) reader.readObject();

            //SHA256withRSA
            AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA256withRSA");
            AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);

            X500Name issuer = X500Name.getInstance(cacert.getSubjectX500Principal().getEncoded());

            BigInteger serial = new BigInteger(32, new SecureRandom());

            Calendar cal = Calendar.getInstance();


            Date from = cal.getTime();
            cal.add(Calendar.YEAR, 10);
            Date to = cal.getTime();

            X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                    issuer, serial,
                    from, to, csr.getSubject(), csr.getSubjectPublicKeyInfo());


            ContentSigner signer = new BcRSAContentSignerBuilder(sigAlgId, digAlgId)
                    .build(PrivateKeyFactory.createKey(cakey.getEncoded()));
            X509CertificateHolder holder = certBuilder.build(signer);
            byte[] certencoded = holder.toASN1Structure().getEncoded();


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write("-----BEGIN CERTIFICATE-----\n".getBytes());
            out.write(java.util.Base64.getMimeEncoder(64, "\n".getBytes()).encode(certencoded));
            out.write("\n-----END CERTIFICATE-----\n".getBytes());
            out.close();
            return new String(out.toByteArray());
        } catch (IOException e) {
            throw new BadRequestException("Error while loading certificate signing request!");
        } catch (OperatorCreationException e) {
            throw new BadRequestException("Error while creating signer object!");
        }
    }

    public static String readCrt(String path) {
        try {
            InputStream is = new ClassPathResource(path).getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String strLine;

            StringBuilder sb = new StringBuilder();

            while ((strLine = br.readLine()) != null) {
                sb.append(strLine + "\n");
            }

            is.close();

            return sb.toString();
        } catch (IOException e) {
            throw new BadRequestException("Error while trying to load certificate!");
        }
    }

    public static String createMessage(String generatedCert, String caCert, String id) {

        return "Certificate chain:" + "\n\n" +
            caCert +
            generatedCert + "\n\n" +
               "Id:" + "\n\n" +
            id;

    }
}
