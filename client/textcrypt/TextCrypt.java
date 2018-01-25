package client.textcrypt;

import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  

/**
 * Simple String Encrypt and Decrypt Class
 * by TG
 * ver. 1.2
 */
public class TextCrypt {

  /**
   * Constructor
   * Just print a message to console. Shows the crypt module was loaded by program. 
   */
  public TextCrypt() {
    System.out.print("Crypt ready");
  }

  /**
   * Creates a SecretKey use the seed string.
   * If there is no seed (keySeed is ""), then use "DTJ^#@" as the seed to generate the SecretKey.
   * @param keySeed is the seed for SecretKey.
   * @return SecretKey.
   */  
  public static SecretKey getKey(String keySeed) {    
        if (keySeed == null) {    
            keySeed = System.getenv("AES_SYS_KEY");    
        }    
        if (keySeed == null) {    
            keySeed = System.getProperty("AES_SYS_KEY");    
        }    
        if (keySeed == null || keySeed.trim().length() == 0) {    
            keySeed = "DTJ^#@";// 默认种子    
        }    
        try {    
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");    
            secureRandom.setSeed(keySeed.getBytes());    
            KeyGenerator generator = KeyGenerator.getInstance("AES");    
            generator.init(secureRandom);    
            return generator.generateKey();    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
  }

  /**  
    * Encrypt the plain text plainText according to the key.
    *
    * @param plainText plain text.
    * @param secretKey encrypt use this AES key. 
    * @return encrypted ciphertext.
    */  
  public static final String encrypt(String plainText, SecretKey secretKey) {  
    try {  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);  
        byte[] p = plainText.getBytes("UTF-8");  
        byte[] result = cipher.doFinal(p);  
        BASE64Encoder encoder = new BASE64Encoder();  
        String encoded = encoder.encode(result);  
        return encoded;  
    } catch (Exception e) {  
        throw new RuntimeException(e);  
    }   
  }  

  /**
    * Decrypt the specified ciphertext cipherText according to the key.
    *
    * @param cipherText ciphertext
    * @param secretKey decrypt use this AES key. 
    * @return decrypted plaintext.
    */
  public static final String decrypt(String cipherText, SecretKey secretKey) {  
    try {  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, secretKey);  
        BASE64Decoder decoder = new BASE64Decoder();  
        byte[] c = decoder.decodeBuffer(cipherText);  
        byte[] result = cipher.doFinal(c);  
        String plainText = new String(result, "UTF-8");  
        return plainText;  
    } catch (Exception e) {  
        throw new RuntimeException(e);  
    }  
  }  

}  
