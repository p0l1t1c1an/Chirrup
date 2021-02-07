package PGPtest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import PGPtest.Encryption.EncryptionSuite;
import java.security.KeyPair;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.util.Arrays;

/**
 * Web page that displays encrypted and decrypted text
 *
 * @author Jacob Boicken
 */

@RestController
class KeyTestController {

    @GetMapping("/pgptest")
	public String pgptest() throws Exception {
		String msg = new String();
		IvParameterSpec iv = EncryptionSuite.genIvSpec();
		SecretKey sk = EncryptionSuite.genSecret(256);
		KeyPair kp = EncryptionSuite.genKeyPair(4096);		
		
		msg += "<p>----------------- KEY GENERATION ------------------</p>\n";
		msg += "<p>Secret Key: " + Arrays.toString(sk.getEncoded()) + "</p>\n";
		msg += "<p>Public Key: " + Arrays.toString(kp.getPublic().getEncoded()).substring(0, 147) + "..." + "</p>\n";
		msg += "<p>Private Key: " + Arrays.toString(kp.getPrivate().getEncoded()).substring(0, 147) + "..." + "</p>\n";

		byte[] encryptedKey = EncryptionSuite.encryptRSA(kp.getPublic(), sk.getEncoded());
		byte[] decryptedKey = EncryptionSuite.decryptRSA(kp.getPrivate(), encryptedKey);

		msg += "<p>--------------- KEY PAIR ENCRYPTS SECRET KEY ----------------</p>\n";
		msg += "<p>Encrypted Secret Key: " + Arrays.toString(encryptedKey).substring(0, 147) + "..." + "</p>\n";
		msg += "<p>Decrypted Secret Key: " + Arrays.toString(decryptedKey) + "</p>\n";
		msg += "<p>Keys Match: " +  Arrays.equals(sk.getEncoded(), decryptedKey) + "</p>\n";
		
		// How many bytes can this encrypt????   
		String text = "Hello! This is a very long message to test how " +
					  "many bytes this can encrypt and decrypt at once."; 
		
		byte[] encryptedText = EncryptionSuite.encryptAES(sk, iv, text.getBytes("UTF-8"));
		byte[] decryptedText = EncryptionSuite.decryptAES(sk, iv, encryptedText); 

		msg += "<p>--------------- SECRET KEY ENCRYPTS MESSAGE ----------------</p>\n";
		msg += "<p>Unencrypted Msg: " + text + " = " + Arrays.toString(text.getBytes("UTF-8")) + "</p>\n";
		msg += "<p>Encrypted Msg: " + new String(encryptedText) + " = " + Arrays.toString(encryptedText) + "</p>\n";
		msg += "<p>Decrypted Msg: " + new String(decryptedText) + " = " + Arrays.toString(decryptedText) + "</p>\n";
		msg += "<p>Msg Match: " +  Arrays.equals(text.getBytes("UTF-8"), decryptedText) + "</p>\n";

		return msg;
    }
}
