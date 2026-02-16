package dev.nelit.server.utils;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class TelegramInitDataVerifier {

    public TelegramInitDataVerifier() {
    }

    public static boolean verify(String initData, String botToken) {
        if (initData == null || initData.isBlank() || botToken == null || botToken.isBlank()) {
            return false;
        }

        Map<String, String> params = parseParams(initData);
        String receivedHash = params.remove("hash");

        if (receivedHash == null || receivedHash.isEmpty()) {
            return false;
        }

        String dataCheckString = params.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("\n"));

        try {
            byte[] secretKey = createSecretKey(botToken);
            String computedHash = computeHmacHex(secretKey, dataCheckString);
            return computedHash.equals(receivedHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }

    private static byte[] createSecretKey(String botToken)
        throws NoSuchAlgorithmException, InvalidKeyException {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec("WebAppData".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(botToken.getBytes(StandardCharsets.UTF_8));
    }

    private static String computeHmacHex(byte[] key, String data)
        throws NoSuchAlgorithmException, InvalidKeyException {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        byte[] hmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder(hmac.length * 2);
        for (byte b : hmac) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    private static Map<String, String> parseParams(String query) {
        return Arrays.stream(query.split("&"))
            .map(part -> part.split("=", 2))
            .collect(Collectors.toMap(
                arr -> arr[0],
                arr -> {
                    String value = arr.length == 2 ? arr[1] : "";
                    try {
                        return java.net.URLDecoder.decode(value, StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        return value;
                    }
                },
                (v1, v2) -> v1,
                LinkedHashMap::new
            ));
    }
}