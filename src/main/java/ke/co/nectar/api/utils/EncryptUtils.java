package ke.co.nectar.api.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class EncryptUtils {

    public static String md5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte[] digest = md.digest();
        return Hex.encodeHexString(digest).toUpperCase();
    }

    public static String generateNonce() {
        final int NO_OF_CHARS = 32;
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < NO_OF_CHARS)
            sb.append(Integer.toHexString(r.nextInt()));
        return sb.toString().substring(0, NO_OF_CHARS);
    }

    public static String generateBase64HMAC(String secret, String method, String path,
                                            String md5, String contentType, String date,
                                            String  nonce)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        final String str = String.format("%s%s%s%s%s%s", method, path, md5, contentType, date, nonce);
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        String hashed = new BigInteger(1, sha256Hmac.doFinal(str.getBytes(StandardCharsets.UTF_8))).toString(16);
        return Base64.getEncoder().encodeToString(hashed.getBytes(StandardCharsets.UTF_8));
    }
}
