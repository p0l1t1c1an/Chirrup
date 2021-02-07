package PGPtest.Encryption;

import java.util.Arrays;
import java.util.Base64;

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
 * Some simple methods to for RSA and AES keys
 *
 * @author Jacob Boicken
 */


public class EncryptionSuite {
	

	public static IvParameterSpec genIvSpec() {
		SecureRandom srand = new SecureRandom();
		byte[] ivspec = new byte[16];
		srand.nextBytes(ivspec);	
		return new IvParameterSpec(ivspec);
	}

	public static SecretKey genSecret(int len) throws Exception{
		SecureRandom srand = new SecureRandom();
		KeyGenerator keySecGen = KeyGenerator.getInstance("AES");
		keySecGen.init(len, srand);
		return keySecGen.generateKey();      
	}

	public static KeyPair genKeyPair(int len) throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(len);
		return keyPairGen.generateKeyPair();      
	}
	
	public static byte[] encrypt(Key k, IvParameterSpec iv, byte[] plaintext) throws Exception{
		//Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		//Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); 
		cipher.init(Cipher.ENCRYPT_MODE, k, iv);
	  
		cipher.update(plaintext);
		byte[] encryptedText = cipher.doFinal();	 
		
		return Base64.getEncoder().encode(encryptedText);
	}

	public static byte[] decrypt(Key k, IvParameterSpec iv, byte[] encryptedText) throws Exception{
		//Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		//Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); 
		cipher.init(Cipher.DECRYPT_MODE, k, iv);
		
		cipher.update(Base64.getDecoder().decode(encryptedText));
		byte[] decryptedText = cipher.doFinal();	 
		          	
		return decryptedText;
	}
}
