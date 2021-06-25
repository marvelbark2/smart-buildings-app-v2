package edu.episen.si.ing1.pds.backend.server.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtils {
    private static final String algorithm = "AES/CBC/PKCS5Padding";
    private static final SecretKey key = AESProperties.INSTANCE.getKey();
    private static final IvParameterSpec iv = AESProperties.INSTANCE.getIv();

    public static String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        byte[] dataBytes = Base64.getEncoder().encode(cipherText);
        return new String(dataBytes, StandardCharsets.UTF_8);
    }

    public static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
}
