package com.naga.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

    /**
     * Encrypt byte array.
     */
    public static byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(source);
        return md.digest();
    }

    /**
     * Encrypt string
     */
    public static String encrypt(String source, String algorithm) throws NoSuchAlgorithmException {
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);
        return MyCommonUtil.toHexString(resByteArray);
    }
    
    /**
     * Encrypt string using MD5 algorithm
     */
    public static String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }

        String result = "";
        try {
            result = encrypt(source, "MD5");
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Encrypt string using SHA algorithm
     */
    public static String encryptSHA(String source) {
        if (source == null) {
            source = "";
        }

        String result = "";
        try {
            result = encrypt(source, "SHA");
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }

	public static String encryptSHA512(String source) {

        String result = "";
        if (source == null) {
            source = "";
        }
        
        result = DigestUtils.sha512Hex(source);
		return result;
	}

    public static void main(String[] args) {
       System.out.println(encryptSHA512("a222222"));
    }
}