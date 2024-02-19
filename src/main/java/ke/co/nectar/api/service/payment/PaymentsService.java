package ke.co.nectar.api.service.payment;

import ke.co.nectar.api.service.exceptions.ApiResponseException;

public interface PaymentsService {

    String forwardPaymentResult(String requestId, String forward)
            throws ApiResponseException;
}
