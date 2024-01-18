package ke.co.nectar.api.service.beescallback.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.service.beescallback.BeesCallbackForwardService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BeesCallbackForwardServiceImpl implements BeesCallbackForwardService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Value("${endpoints.bees.forward-url}")
    private String beesForwardUrl;

    @Value("${endpoints.bees.username}")
    private String beesBasicAuthUsername;

    @Value("${endpoints.bees.password}")
    private String beesBasicAuthPassword;

    @Override
    public String forwardPaymentResult(String requestId, String forward)
        throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(beesBasicAuthUsername,
                        beesBasicAuthPassword),
                String.format("%s?request_id=%s",
                        beesForwardUrl, requestId),
                forward);
        assert response != null;
        if (response.getStatus().getCode() == 200) {
            return response.getStatus().getRequestId();
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }

}
