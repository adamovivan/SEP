package rs.ac.uns.ftn.authentication_service.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.authentication_service.config.Password;
import rs.ac.uns.ftn.authentication_service.exceptions.BadRequestException;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.model.Role;
import rs.ac.uns.ftn.authentication_service.model.Submission;
import rs.ac.uns.ftn.authentication_service.model.SubmissionState;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;
import rs.ac.uns.ftn.authentication_service.repository.SubmissionRepository;
import rs.ac.uns.ftn.authentication_service.util.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentsService.class);

	@Autowired
	private ClientRepository registrationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SubmissionRepository submissionRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

	public Client save(Client client) throws Exception {
		Client newClient  = client;
		newClient.setPassword(Password.getSaltedHash(client.getPassword()));
		newClient.setRole(Role.SELLER);
		try {
			newClient = registrationRepository.save(newClient);
			logger.info("Successfully registered new user" + newClient.getUsername() + ".");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Failed to register user " + newClient.getUsername() + ".");
			
		}
		return newClient;
	}

    public void submit(Submission sub, MultipartFile file)  {

        Submission submission = this.submissionRepository.findByEmailAndStateOrState(sub.getEmail(),
                SubmissionState.ACCEPTED, SubmissionState.NONE).orElse(null);

        if (submission != null) {
            throw new BadRequestException("Company with that email already exists!");
        }

        submission = this.submissionRepository.findByCompanyNameAndStateOrState(sub.getCompanyName(),
                SubmissionState.ACCEPTED, SubmissionState.NONE).orElse(null);

        if (submission != null) {
            throw new BadRequestException("Company with that name already exists!");
        }

        submission = this.submissionRepository.findByPhoneNumberAndStateOrState(sub.getPhoneNumber(),
                SubmissionState.ACCEPTED, SubmissionState.NONE).orElse(null);

        if (submission != null) {
            throw new BadRequestException("Company with that phone number already exists");
        }

        PKCS10CertificationRequest csr = Util.getCSR(file);
        if (csr == null) {
            throw new BadRequestException("Error while reading csr file! Check if the file is properly formatted!");
        }

        X500Name x500Name = csr.getSubject();

        if (!Util.getX500Field(Util.COMMON_NAME, x500Name).equals(sub.getCompanyName())) {
            throw new BadRequestException("Company name does not match the common name from the csr!");
        }

        if (!Util.getX500Field(Util.ORGANIZATION, x500Name).equals(sub.getOrganization())) {
            throw new BadRequestException("Organization does not match the organization from the csr!");
        }

        if (!Util.getX500Field(Util.LOCALE, x500Name).equals(sub.getLocation())) {
            throw new BadRequestException("Location does not match the locality from the csr!");
        }

        if (!Util.getX500Field(Util.STATE, x500Name).equals(sub.getStateLocation())) {
            throw new BadRequestException("State location does not match the state from the csr!");
        }

        if (!Util.getX500Field(Util.COUNTRY, x500Name).equals(sub.getCountry())) {
            throw new BadRequestException("Country does not match the country from the csr!");
        }

        try {
            sub.setCertificateSigningRequest(file.getBytes());
        }
        catch(IOException e) {
            throw new BadRequestException("Error while saving the csr file. Check if the file is properly formatted!");
        }

        sub.setPassword(this.passwordEncoder.encode(sub.getPassword()));
        this.submissionRepository.save(sub);
    }

    public List<Submission> getSubmissions(SubmissionState state) {

        return this.submissionRepository.findAllByState(state);
    }

    public void acceptSubmission(String companyName) {

        Submission submission = this.submissionRepository.findByCompanyNameAndState(companyName, SubmissionState.NONE)
                .orElseThrow(() -> new BadRequestException("Company does not exist or is already processed!"));

        submission.setState(SubmissionState.ACCEPTED);

        Security.addProvider(new BouncyCastleProvider());
        X509Certificate caCert = Util.loadCert("kp.cer");
        PrivateKey caKey = Util.readKey("gateway.key");

        InputStream in = new ByteArrayInputStream(submission.getCertificateSigningRequest());

        String cert = Util.signCSR(new InputStreamReader(in), caKey, caCert);

        Client user = new Client();
        user.setUsername(UUID.randomUUID().toString());
        user.setCompanyName(submission.getCompanyName());
        user.setPhoneNumber(submission.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(submission.getPassword()));
        user.setRole(Role.SELLER);

        submission.setCertificate(cert.getBytes());
        this.submissionRepository.save(submission);

        this.registrationRepository.save(user);

        String cacert = Util.readCrt("kp.crt");

        String message = Util.createMessage(cert, cacert, user.getUsername());

        this.emailService.sendSimpleMessage(
                submission.getEmail(),
                "Your submission was accepted",
                message
        );
    }


    public void declineSubmission(String message, String companyName) {

        Submission submission = this.submissionRepository.findByCompanyNameAndState(companyName, SubmissionState.NONE)
                .orElseThrow(() -> new BadRequestException("Company does not exist or is already processed!"));

        this.emailService.sendSimpleMessage(
                submission.getEmail(),
                "Your submission was declined",
                message
        );

        submission.setState(SubmissionState.REJECTED);
        submission.setCertificateSigningRequest(null);

        this.submissionRepository.save(submission);

    }

    public void registerMerchant(Client user, String username) {

        Client rootMerchant = this.registrationRepository.findByUsername(username);
        if(rootMerchant == null){
            throw new BadRequestException("User with that username does not exist!");
        }

        Client merchant = this.registrationRepository.findByCompanyName(user.getCompanyName()).orElse(null);
        if (merchant != null) {
            throw new BadRequestException("User with that company name already exist!");
        }

        Submission submission = this.submissionRepository.findByCompanyNameAndStateOrState(user.getCompanyName(),
                SubmissionState.ACCEPTED, SubmissionState.NONE).orElse(null);

        if (submission != null) {
            throw new BadRequestException("Submission with that company name exists!");
        }

        merchant = this.registrationRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        if (merchant != null) {
            throw new BadRequestException("User with that phone number exists!");
        }

        submission = this.submissionRepository.findByPhoneNumberAndStateOrState(user.getPhoneNumber(),
                SubmissionState.ACCEPTED, SubmissionState.NONE).orElse(null);

        if (submission != null) {
            throw new BadRequestException("Submission with that phone number exists!");
        }

        user.setUsername(UUID.randomUUID().toString());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.SELLER);
        this.registrationRepository.save(user);

        rootMerchant.getClients().add(user);
        this.registrationRepository.save(rootMerchant);
    }

    public List<Client> getMerchants(String username) {

        Client rootMerchant = this.registrationRepository.findByUsername(username);

        if(rootMerchant == null){
            throw new BadRequestException("User with that username does not exist!");
        }

        List<Client> users = new ArrayList<>(rootMerchant.getClients());

        return users;
    }

}
