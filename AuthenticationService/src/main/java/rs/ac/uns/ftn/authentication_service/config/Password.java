package rs.ac.uns.ftn.authentication_service.config;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

public class Password implements PasswordEncoder {
    // The higher the number of iterations the more 
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /** Computes a salted PBKDF2 hash of given plaintext password
        suitable for storing in a database. 
        Empty passwords are not supported. */
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /** Checks whether given plaintext password corresponds 
        to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndHash = stored.split("\\$");
        if (saltAndHash.length != 2) {
            throw new IllegalStateException(
                "The stored password must have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
        return hashOfInput.equals(saltAndHash[1]);
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
            password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.encodeBase64String(key.getEncoded());
    }

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		try {
			return this.getSaltedHash(rawPassword.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		try {
			return check(rawPassword.toString(), encodedPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
