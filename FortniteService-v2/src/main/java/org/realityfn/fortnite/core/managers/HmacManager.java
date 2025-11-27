package org.realityfn.fortnite.core.managers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HmacManager {

    public static String generate(String payloadBase64, String secret) {
        try {
            byte[] data = payloadBase64.getBytes(StandardCharsets.UTF_8);

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));

            byte[] hmacBytes = mac.doFinal(data);

            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
