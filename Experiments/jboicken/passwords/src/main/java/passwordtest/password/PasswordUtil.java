package passwordtest.password;

import java.security.SecureRandom;

import at.favre.lib.crypto.bcrypt;

public class PasswordUtil {
	
	private static final SecureRandom SRAND = new SecureRandom();
	private static final int KEY_ITER = 10000;
	private static final int KEY_LENGTH = 256;

	private PasswordUtil() { }

	public static byte[] generateSalt(){
		byte[] salt = new byte[8];
		SRAND.nextBytes(salt);
		return salt;
	}

	public static byte[] hash(final String passwd, final byte[] salt) throws Exception {
		byte[] passBytes = passwd.getBytes();
		byte[] combined = new byte[passBytes.length + salt.length];
		
		System.arraycopy(passBytes, 0, combined, 0, passBytes.length);
		System.arraycopy(salt, 0, combined, passBytes.length, salt.length);
		
	}
	

		

}


