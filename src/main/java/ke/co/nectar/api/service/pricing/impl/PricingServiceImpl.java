package ke.co.nectar.api.service.pricing.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Pricing;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.pricing.PricingService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Pricing pricing;

    @Value("${endpoints.pricing.host}")
    private String pricingEndpoint;

    @Value("${endpoints.pricing.username}")
    private String pricingBasicAuthUsername;

    @Value("${endpoints.pricing.password}")
    private String pricingBasicAuthPassword;

    @Override
    public Pricing getPricing(String requestId)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(pricingBasicAuthUsername,
                        pricingBasicAuthPassword),
                String.format("%s?request_id=%s",
                        pricingEndpoint, requestId));
        return pricing.extractFrom(response);
    }
}
