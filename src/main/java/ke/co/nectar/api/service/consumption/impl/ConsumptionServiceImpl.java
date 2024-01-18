package ke.co.nectar.api.service.consumption.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.service.consumption.ConsumptionService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Value("${endpoints.consumption.host}")
    private String consumptionEndpoint;

    @Value("${endpoints.consumption.username}")
    private String consumptionBasicAuthUsername;

    @Value("${endpoints.consumption.password}")
    private String consumptionBasicAuthPassword;

    @Override
    public String postConsumption(String requestId,
                           String userRef,
                           Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(consumptionBasicAuthUsername,
                        consumptionBasicAuthPassword),
                String.format("%s?request_id=%s&user_ref=%s",
                        consumptionEndpoint,
                        requestId,
                        userRef),
                new Payload(params).toJson().toString());
        if (response.getStatus().getCode() == 200) {
            return (String) response.getData().get("consumption");
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }
}
