package ke.co.nectar.api.service.payment.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Payment;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.payment.PaymentsService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private RequestUtils requestUtils;

    @Value("${payments.host}")
    private String paymentsHost;

    @Value("${endpoints.payments.callback-url}")
    private String paymentsCallbackUrl;

    @Value("${endpoints.payments.timeout-url}")
    private String paymentsQueueTimeoutUrl;

    @Value("${endpoints.payments.check-payments-url}")
    private String checkPaymentsUrl;

    @Value("${endpoints.payments.schedule-payments-url}")
    private String schedulePaymentsUrl;

    @Value("${endpoints.payments.validate-payments-url}")
    private String validatePaymentUrl;

    @Value("${endpoints.payments.validate-payments-result-url}")
    private String processPaymentValidation;

    @Value("${endpoints.payments.username}")
    private String paymentsBasicAuthUsername;

    @Value("${endpoints.payments.password}")
    private String paymentsBasicAuthPassword;

    @Override
    public List<Payment> getPayments(String requestId, String userRef) throws Exception {
        final String REQUEST_URL = String.format("%s?request_id=%s&user_ref=%s&detailed_param=false",
                requestId, paymentsHost, userRef);
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(paymentsBasicAuthUsername,
                        paymentsBasicAuthPassword), REQUEST_URL);
        if (response.getStatus().getCode() == 200) {
            JSONObject paymentsObj = new JSONObject(response.getData());
            List<Payment> payments = new ArrayList<>();
            paymentsObj.getJSONArray("payments")
                    .forEach(payment -> {
                        JSONObject paymentData = ((JSONObject)  payment);
                        payments.add(new Payment(paymentData.getString("ref"),
                                Instant.parse(paymentData.getString("scheduled")),
                                Instant.parse(paymentData.getString("fulfilled")),
                                paymentData.getString("resultCode"),
                                paymentData.getString("resultDesc"),
                                paymentData.getString("type"),
                                Double.parseDouble(paymentData.get("value").toString()),
                                paymentData.getString("userRef")));
                    });
            return payments;
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }


    @Override
    public Payment  getPayment(String requestId, String paymentRef) throws Exception {
        final String REQUEST_URL = String.format("%s?request_id=%s&ref=%s",
                paymentsHost, requestId, paymentRef);
        ApiResponse response = requestUtils.get(
            new BasicAuthCredentials(paymentsBasicAuthUsername,
                    paymentsBasicAuthPassword), REQUEST_URL);
        if (response.getStatus().getCode() == 200) {
            JSONObject paymentData = new JSONObject(response.getData());
            return new Payment(paymentData.getString("ref"),
                    Instant.parse(paymentData.getString("scheduled")),
                    Instant.parse(paymentData.getString("fulfilled")),
                    paymentData.getString("resultCode"),
                    paymentData.getString("resultDesc"),
                    paymentData.getString("type"),
                    Double.parseDouble(paymentData.get("value").toString()),
                    paymentData.getString("userRef"));
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }

    @Override
    public String schedulePayment(String requestId, String userRef, Map<String, Object> params)
            throws Exception {
        final String REQUEST_URL = String.format("%s?request_id=%s&user_ref=%s",
                paymentsHost, requestId, userRef);
        ApiResponse apiResponse = requestUtils.post(new BasicAuthCredentials(paymentsBasicAuthUsername,
                                                                        paymentsBasicAuthPassword),
                                                REQUEST_URL, new Payload(params).toJson().toString());
        if (apiResponse.getStatus().getCode() == 200) {
            return new JSONObject(apiResponse.getData()).getString("transaction_ref");
        }
        throw new ApiResponseException(apiResponse.getStatus().getMessage());
    }

    @Override
    public String processSchedulePaymentResult(String requestId, String paymentResult)
            throws Exception {
        final String REQUEST_URL = String.format("%s/callback?request_id=%s", paymentsHost,
                requestId);
        ApiResponse response = requestUtils.post(new BasicAuthCredentials(paymentsBasicAuthUsername,
                paymentsBasicAuthPassword),
                REQUEST_URL, paymentResult);
        if (response.getStatus().getCode() == 200) {
            return new JSONObject(response.getData()).getString("transaction_ref");
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }

    @Override
    public String processPaymentTimeout(String requestId, String paymentResult) throws Exception {
        final String REQUEST_URL = String.format("%s/timeout?request_id=%s", paymentsHost,
                requestId);
        ApiResponse response = requestUtils.post(new BasicAuthCredentials(paymentsBasicAuthUsername,
                paymentsBasicAuthPassword),
                REQUEST_URL, paymentResult);
        if (response.getStatus().getCode() == 200) {
            return new JSONObject(response.getData()).getString("transaction_ref");
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }

    @Override
    public String  validatePayment(String requestId, String paymentRequest) throws Exception {
        final String REQUEST_URL = String.format("%s/validate?request_id=%s", paymentsHost,
                requestId);
        ApiResponse response = requestUtils.post(new BasicAuthCredentials(paymentsBasicAuthUsername,
                paymentsBasicAuthPassword),
                REQUEST_URL, paymentRequest);
        if (response.getStatus().getCode() == 200) {
            return new JSONObject(response.getData()).getString("transaction_ref");
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }


    @Override
    public Payment processPaymentValidation(String requestId, String paymentResult) throws Exception {
        final String REQUEST_URL = String.format("%s/validateResult?request_id=%s", paymentsHost,
                requestId);
        ApiResponse response = requestUtils.post(new BasicAuthCredentials(paymentsBasicAuthUsername,
                paymentsBasicAuthPassword),
                REQUEST_URL, paymentResult);
        if (response.getStatus().getCode() == 200) {
            JSONObject paymentData = new JSONObject(response.getData());
            return new Payment(paymentData.getString("ref"),
                    Instant.parse(paymentData.getString("scheduled")),
                    Instant.parse(paymentData.getString("fulfilled")),
                    paymentData.getString("resultCode"),
                    paymentData.getString("resultDesc"),
                    paymentData.getString("type"),
                    Double.parseDouble(paymentData.get("value").toString()),
                    paymentData.getString("userRef"));
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }
}
