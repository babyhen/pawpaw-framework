package com.pawpaw.framework.common.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SHAUtil {

	public static String SHA256Str(String str) {
		try {
			String hex = DigestUtils.sha256Hex(str);
			return hex;
		} catch (Exception e) {
			throw new RuntimeException("cal SHA256 fail");
		}
	}

	public static String HMACSHA256(String secret, String str) {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			byte[] data = sha256_HMAC.doFinal(str.getBytes("utf-8"));
			String hash = Hex.encodeHexString(data);
			return hash;
		} catch (Exception e) {
			throw new RuntimeException("cal HMACSHA256 fail");
		}
	}

}
