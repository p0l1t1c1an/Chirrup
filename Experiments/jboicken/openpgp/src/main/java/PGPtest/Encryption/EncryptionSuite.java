package PGPtest.Encryption;


import java.security.Key;
import java.security.PublicKey;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.KeyGenerator;

import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import java.security.SecureRandom;


/**
 * Some simple functions to for RSA and AES keys
 *
 * @author Jacob Boicken
 */


public class EncryptionSuite {

	static final SecureRandom srand = new SecureRandom();

	public static IvParameterSpec genIvSpec() {
		byte[] ivspec = new byte[16];
		srand.nextBytes(ivspec);	
		return new IvParameterSpec(ivspec);
	}

	public static SecretKey genSecret(int len) throws Exception{
		KeyGenerator keySecGen = KeyGenerator.getInstance("AES");
		keySecGen.init(len, srand);
		return keySecGen.generateKey();      
	}

	public static KeyPair genKeyPair(int len) throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(len, srand);
		return keyPairGen.generateKeyPair();      
	}
	
	public static byte[] encryptAES(Key k, IvParameterSpec iv, byte[] plaintext) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		cipher.init(Cipher.ENCRYPT_MODE, k, iv);
	  
		return cipher.doFinal(plaintext);
	}

	public static byte[] decryptAES(Key k, IvParameterSpec iv, byte[] encryptedText) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		cipher.init(Cipher.DECRYPT_MODE, k, iv);
		
		return cipher.doFinal(encryptedText);
	}

	public static byte[] encryptRSA(PublicKey k, byte[] plaintext) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding "); 
		cipher.init(Cipher.ENCRYPT_MODE, k);
	  
		return cipher.doFinal(plaintext);
	}

	public static byte[] decryptRSA(PrivateKey k, byte[] encryptedText) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding "); 
		cipher.init(Cipher.DECRYPT_MODE, k);
		
		return cipher.doFinal(encryptedText);
	}

}
