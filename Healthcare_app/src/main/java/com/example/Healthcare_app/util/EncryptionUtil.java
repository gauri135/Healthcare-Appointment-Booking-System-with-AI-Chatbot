package com.example.Healthcare_app.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

	 private static final String ALGORITHM = "AES";
	    private static final String KEY = "16ByteSecretKey!"; // 16-byte key

	    public static String encrypt(String data) throws Exception {
	        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encrypted = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encrypted);
	    }

	    public static String decrypt(String encryptedData) throws Exception {
	        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decrypted);
	    }

}
