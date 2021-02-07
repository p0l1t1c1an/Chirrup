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
	
		
		msg += "<p>Secret Key: " + sk.getEncoded() + "</p>\n";
		msg += "<p>Public Key: " + kp.getPublic().getEncoded() + "</p>\n";
		msg += "<p>Private Key: " + kp.getPrivate().getEncoded() + "</p>\n";

		String text = "Hello World Wid"; // Seems to only allow 15 bytes to get encrypted?

		byte[] encryptedText = EncryptionSuite.encrypt(sk, iv, text.getBytes("UTF-8"));
		byte[] decryptedText = EncryptionSuite.decrypt(sk, iv, encryptedText); 

		msg += "<p>Unencrypted Msg: " + new String(text) + " = " + Arrays.toString(text.getBytes("UTF-8")) + "</p>\n";
		msg += "<p>Encrypted Msg: " + new String(encryptedText) + " = " + Arrays.toString(encryptedText) + "</p>\n";
		msg += "<p>Decrypted Msg: " + new String(decryptedText) + " = " + Arrays.toString(decryptedText) + "</p>\n";

		return msg;
    }
}
