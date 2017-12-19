/*package com.cascadiaoccidental.deadeye.util.mwsApi;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class ApiRequestBuilder  {

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
