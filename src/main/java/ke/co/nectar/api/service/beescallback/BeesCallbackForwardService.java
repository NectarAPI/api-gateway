package ke.co.nectar.api.service.beescallback;

import ke.co.nectar.api.service.exceptions.ApiResponseException;

public interface BeesCallbackForwardService {

    String forwardPaymentResult(String requestId, String forward)
                throws ApiResponseException;
}
