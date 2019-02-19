package com.pawpaw.framework.core.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.Security;

public class SHAUtil {

    public static String SHA256Str(String str) {
        try {
            String hex = DigestUtils.sha256Hex(str);
            return hex;
        } catch (Exception e) {
            throw new RuntimeException("cal SHA256 fail");
        }
    }

    public static String encrytSHA256(String content, String secret) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF8"),
                    "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] digest = mac.doFinal(content.getBytes("UTF-8"));
            return new HexBinaryAdapter().marshal(digest);
        } catch (Exception e) {
            throw new RuntimeCryptoException("加密异常");
        }

    }

}
