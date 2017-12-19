/*package com.cascadiaoccidental.deadeye.util.mwsApi;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class ApiRequestBuilder  {
    private final String SELLER_ID = "A10B1Y5YAX9LJB";
    private final String MARKETPLACE_ID = "ATVPDKIKX0DER";
    private final String AWS_ACCESS_KEY_ID = "AKIAIBM7Q3KK76F4EAMA";
    private final String SECRET_KEY = "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";
    private final String ASIN;
    public ApiRequestBuilder(String asin) {
        this.ASIN = asin;
    }

    public String hash(String msg) throws ApiRequestException {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(msg.getBytes()));
            System.out.println(hash);
        }
        catch (Exception e){
            throw new ApiRequestException("Hash error");
        }
    }

}
*/