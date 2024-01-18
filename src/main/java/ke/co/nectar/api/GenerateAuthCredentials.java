package ke.co.nectar.api;

import ke.co.nectar.api.utils.EncryptUtils;

public class GenerateAuthCredentials {

    public static void main(String[] args) {
        GenerateAuthCredentials credentials = new GenerateAuthCredentials();
        System.out.println(credentials.generateChecksum());
    }

    private String generateChecksum() {
        String checksum = "";

        String secret = "2fb70fe3-8800-449c-a08e-108e22228da5";
        String method = "GET";
        String path = "/v1/tokens";
        String contentType = "application/json";
        String date = "Thu, 15 Aug 2020 18:48:58 GMT";
        String nonce = "2659c837e161e039ecf23fe47e6db42f";

        try {
            String md5 = EncryptUtils.md5("{\"class\":\"0\",\"subclass\":\"0\",\"decoder_reference_number\":\"0179220610010\",\"amount\":\"10\",\"config_ref\":\"cbf43d1f-8c2d-44a0-95a9-9c3c13ec846c\"}");
            checksum = EncryptUtils.generateBase64HMAC(secret, method, path, md5,
                    contentType, date, nonce);

        } catch (Exception e) {
            System.out.println(e);
        }
        return checksum;
    }

}
