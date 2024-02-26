package ke.co.nectar.api.service.payment;

import ke.co.nectar.api.controllers.payments.PaymentRequest;
import ke.co.nectar.api.domain.Payment;

import java.util.List;
import java.util.Map;


public interface PaymentsService {

    List<Payment> getPayments(String requestId, String userRef) throws Exception;

    Payment  getPayment(String requestId, String paymentRef) throws Exception;

    String schedulePayment(String requestId, String userRef, PaymentRequest paymentRequest) throws Exception;

    String processSchedulePaymentCallback(String requestId, String paymentResult) throws Exception;

    String processPaymentTimeout(String requestId, String paymentResult) throws Exception;

    String  validatePayment(String requestId, String paymentResult) throws Exception;

    Payment processPaymentValidation(String requestId, String paymentResult) throws Exception;

}
