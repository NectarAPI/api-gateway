package ke.co.nectar.api.service.users.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.User;
import ke.co.nectar.api.domain.Utility;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.users.UserService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private User user;

    @Autowired
    private Utility utility;

    @Value("${endpoints.users.host}")
    private String usersEndpoint;

    @Value("${endpoints.users.username}")
    private String usersBasicAuthUsername;

    @Value("${endpoints.users.password}")
    private String usersBasicAuthPassword;

    @Override
    public User getUser(String requestId, String username)
        throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(usersBasicAuthUsername,
                                        usersBasicAuthPassword),
                String.format("%s?username=%s&request_id=%s", usersEndpoint, username, requestId));
        return user.extractFrom(response);
    }

    @Override
    public String createUser(String requestId,
                           String userRef,
                           Map<String, Object> params)
        throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                    new BasicAuthCredentials(usersBasicAuthUsername,
                            usersBasicAuthPassword),
                    String.format("%s?request_id=%s&user_ref=%s",
                            usersEndpoint, requestId, userRef),
                    new Payload(params).toJson().toString());
        return (String) response.getData().get("user_ref");
    }

    @Override
    public void deleteUser(String requestId, String ref)
        throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(usersBasicAuthUsername,
                        usersBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s",
                        usersEndpoint, ref, requestId));
    }

    @Override
    public void updateUser(String requestId, String ref, Map<String, Object> params)
        throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(usersBasicAuthUsername,
                        usersBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", usersEndpoint, ref, requestId),
                    new Payload(params).toJson().toString());
    }

    @Override
    public List<Utility> getUtilities(String requestId,
                                      String userRef)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(usersBasicAuthUsername,
                        usersBasicAuthPassword),
                String.format("%s/%s/utility?request_id=%s", usersEndpoint, userRef, requestId));
        return utility.extractMultipleFrom(response);
    }
}
