package ke.co.nectar.api.validation.authoritization;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credentials;
import ke.co.nectar.api.domain.User;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.EncryptUtils;
import ke.co.nectar.api.utils.RedisManager;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.exceptions.Base64HMACMismatchException;
import ke.co.nectar.api.validation.authoritization.exceptions.InvalidNonceException;
import ke.co.nectar.api.validation.authoritization.exceptions.MD5ComparisonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Component
public class APIAuthorizationManager {

    private String method;
    private String path;
    private String contentType;
    private String contentMD5;
    private String date;
    private String nonce;
    private String credentialsKey;
    private String base64HMACValue;
    private String requestBody;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Credentials credentials;

    @Value("${endpoints.credentials.host}")
    private String CREDENTIALS_ENDPOINT;

    @Value("${endpoints.credentials.username}")
    private String credentialsBasicAuthUsername;

    @Value("${endpoints.credentials.password}")
    private String getCredentialsBasicAuthPassword;

    public static User user;

    public boolean validate(HttpServletRequest request, String permission)
        throws IOException, NoSuchAlgorithmException, InvalidKeyException,
                Base64HMACMismatchException, MD5ComparisonException,
                InvalidNonceException, ApiResponseException {
        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
        getRequestParams(requestWrapper);
        getRequestBody(requestWrapper);

        if (requestParamsValid()) {
            Credentials credentials = getCredentials(credentialsKey);
            user = credentials.getUser();
            if (credentials != null && credentials.isActivated()) {
                if (checkPermission(credentials, permission)) {
                    compareHeaderValues(credentials);
                    checkAndUpdate(nonce);
                    return true;
                }
            }
        }
        return false;
    }

    private void getRequestParams(HttpServletRequest request) {
        final String AUTHORIZATION_WORD = "nectar ";
        method = request.getMethod();
        path = request.getRequestURI();
        contentType = request.getContentType();
        contentMD5 = request.getHeader("Content-md5");
        date = request.getHeader("Date");
        nonce = request.getHeader("nonce");
        credentialsKey = request.getHeader("authorization")
                                .substring(AUTHORIZATION_WORD.length())
                                .split(":")[0];
        base64HMACValue = request.getHeader("authorization")
                                    .substring(AUTHORIZATION_WORD.length())
                                    .split(":")[1];
    }

    private void getRequestBody(HttpServletRequest request)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        requestBody = new String(((ContentCachingRequestWrapper) request).getContentAsByteArray());
    }

    private boolean requestParamsValid() {
        return !contentType.isBlank() &&
                !contentMD5.isBlank() &&
                !date.isBlank() &&
                !nonce.isBlank() &&
                !credentialsKey.isBlank() &&
                !base64HMACValue.isBlank();
    }

    private Credentials getCredentials(String key)
        throws ApiResponseException  {
        final String REQUEST_ID = UUidUtils.generateRef();
        final String HOST = String.format("%s?key=%s&request_id=%s", CREDENTIALS_ENDPOINT, key, REQUEST_ID);
        ApiResponse response = requestUtils.get(new BasicAuthCredentials(credentialsBasicAuthUsername,
                                                    getCredentialsBasicAuthPassword),
                                                HOST);
        return credentials.extractFrom(response);
    }

    private boolean checkPermission(Credentials credentials, String permission) {
        return credentials.getPermissions().stream()
                .anyMatch(permissions -> permissions.getIdentifier().equals(permission));
    }

    private void compareHeaderValues(Credentials credentials)
        throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException,
                Base64HMACMismatchException, MD5ComparisonException {
        String generatedBase64HMACValue
                = EncryptUtils.generateBase64HMAC(credentials.getSecret(),
                                                    method,
                                                    path,
                                                    contentMD5,
                                                    contentType,
                                                    date,
                                                    nonce);
        System.out.println(String.format("md5: %s", EncryptUtils.md5(requestBody)));
        System.out.println(String.format("base64Hmac: %s", EncryptUtils.generateBase64HMAC(credentials.getSecret(),
                method,
                path,
                EncryptUtils.md5(requestBody),
                contentType,
                date,
                nonce)));
        if (!generatedBase64HMACValue.equals(base64HMACValue)) {
           throw new Base64HMACMismatchException();
        }

        if (!EncryptUtils.md5(requestBody).equals(contentMD5)) {
            throw new MD5ComparisonException();
        }
    }

    private void checkAndUpdate(String nonce)
        throws InvalidNonceException {
        if (!isNonceAlreadySaved(nonce)) {
            save(nonce);
        } else {
            throw new InvalidNonceException();
        }
    }

    private boolean isNonceAlreadySaved(String nonce) {
        return redisManager.hasKey(nonce);
    }

    private void save(String nonce) {
        redisManager.store(nonce, Instant.now().toString(),60);
    }
}
