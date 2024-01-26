package ke.co.nectar.api.service.meters.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.MeterType;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.meters.MetersService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetersServiceImpl implements MetersService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Meter meter;

    @Autowired
    private MeterType meterType;

    @Value("${endpoints.meters.host}")
    private String metersEndpoint;

    @Value("${endpoints.meters.username}")
    private String metersBasicAuthUsername;

    @Value("${endpoints.meters.password}")
    private String metersBasicAuthPassword;

    @Override
    public Meter getMeter(String requestId, String meterRef)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", metersEndpoint, meterRef, requestId));
        return meter.extractFrom(response);
    }

    @Override
    public List<MeterType> getMeterTypes(String requestId)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("", metersEndpoint, requestId));
        return meterType.extractMultipleFrom(response);
    }

    @Override
    public String addMeter(String requestId,
                   String userRef,
                   Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("%s?request_id=%s&user_ref=%s",
                        metersEndpoint, requestId, userRef),
                new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("meter")).get("ref");
    }

    @Override
    public String updateMeter(String requestId,
                     String userRef,
                     String meterRef,
                     Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.put(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("%s?request_id=%s&meter_ref=%s&user_ref=%s",
                        metersEndpoint, requestId, meterRef, userRef),
                new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("meter")).get("ref");
    }

    @Override
    public void activateMeter(String requestId,
                       String userRef,
                       String ref)
            throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("%s/%s?user_ref=%s&request_id=%s",
                        metersEndpoint, ref, userRef, requestId),
                null);
    }

    @Override
    public void deactivateMeter(String requestId,
                         String userRef,
                         String ref)
            throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(metersBasicAuthUsername,
                        metersBasicAuthPassword),
                String.format("%s/%s?user_ref=%s&request_id=%s",
                        metersEndpoint, ref, userRef, requestId));
    }
}
