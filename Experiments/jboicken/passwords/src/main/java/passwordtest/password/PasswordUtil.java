package passwordtest.password;

import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;

public class PasswordUtil {
	
	private static final SecureRandom SRAND = new SecureRandom();

	private PasswordUtil() { }

	public static byte[] hash(final String passwd) throws Exception {
		return BCrypt.with(BCrypt.Version.VERSION_2Y, SRAND, 
					LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2Y))
						.hash(12, passwd.getBytes());		
	}
	
	public static boolean verify(final String passwd, final byte[] hash){
		return BCrypt.verifyer(BCrypt.Version.VERSION_2Y, 
					LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2Y))
						.verify(passwd.getBytes(StandardCharsets.UTF_8), hash)
						.verified;
	}

}


