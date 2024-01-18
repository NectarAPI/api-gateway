package ke.co.nectar.api.service.consumption;

import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.Map;

public interface ConsumptionService {

    String postConsumption(String requestId,
                           String userRef,
                           Map<String, Object> params)
            throws ApiResponseException;
}
