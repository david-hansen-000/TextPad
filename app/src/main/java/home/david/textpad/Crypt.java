package home.david.textpad;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by david on 12/18/16.
 */
public class Crypt {

    private String password;
    private Cipher cipher;
    private char c;

    public Crypt() {

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public void setPassword(String password) throws Exception {
        this.password = password;
    }

    public void setBuffer(char c) {
        this.c = c;
    }

    public byte[] encrypt(String clear) throws Exception {
        byte[] encrypted_text = null;
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(getKey());
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        encrypted_text = cipher.doFinal(clear.getBytes());
        byte[] encryptedWithIV = new byte[16 + encrypted_text.length];
        System.arraycopy(iv, 0, encryptedWithIV, 0, 16);
        System.arraycopy(encrypted_text, 0, encryptedWithIV, 16, encrypted_text.length);
        return encryptedWithIV;
    }

    public byte[] decryptToBytes(byte[] encrypted) throws Exception {
        byte[] iv = new byte[16];
        System.arraycopy(encrypted, 0, iv, 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        int es = encrypted.length - 16;
        byte[] encryptedNoSalt = new byte[es];
        System.arraycopy(encrypted, 16, encryptedNoSalt, 0, es);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(getKey());
        byte[] keyBytes = new byte[16];
        System.arraycopy(md.digest(), 0, keyBytes, 0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] temp = cipher.doFinal(encryptedNoSalt);
        return temp;
    }

    public String decrypt(byte[] encrypted) throws Exception {
        String decrypted_text = null;
        decrypted_text = new String(decryptToBytes(encrypted));
        return decrypted_text;
    }

    private byte[] getKey() throws Exception {
        String pw = password;
        if (pw.length() < 16) {
            StringBuilder buff = new StringBuilder(pw);
            for (int i = 0; i < 16 - pw.length(); i++) {
                buff.append(c);
            }
            pw = buff.toString();
        }
        if (pw.length() > 16) {
            pw = pw.substring(0, 16);
        }
        return pw.getBytes("UTF-8");
    }
}
