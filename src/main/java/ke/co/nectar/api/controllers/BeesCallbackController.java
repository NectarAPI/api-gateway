package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.service.beescallback.BeesCallbackForwardService;
import ke.co.nectar.api.utils.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;

@RestController
public class BeesCallbackController {

    @Autowired
    private BeesCallbackForwardService service;

    @PostMapping(value = "${endpoints.bees.callback-url}", consumes = "application/json" )
    public ApiResponse processCallbackResult(@RequestBody @NotNull String response) {
        ApiResponse apiResponse;
        try {
            String requestId = UUidUtils.generateRef();
            String transactionRef = service.forwardPaymentResult(requestId, response);
            apiResponse = new ApiResponse(StringConstants.SUCCESS_CODE,
                                            StringConstants.FORWARD_CALLBACK_RESPONSE_RECEIVED,
                                            transactionRef);

        } catch (Exception e) {
            apiResponse = new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    "");
        }
        return apiResponse;
    }
}
