package ke.co.nectar.api.service.pricing;

import ke.co.nectar.api.domain.Pricing;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

public interface PricingService {

    Pricing getPricing(String requestId)
            throws ApiResponseException;
}
