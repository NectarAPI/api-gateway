package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credits;
import ke.co.nectar.api.service.credits.CreditsService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/v1")
public class CreditsController extends BaseController {

    @Autowired
    private CreditsService creditsService;

    @PreAuthorize("hasPermission(#request,'get_credits')")
    @GetMapping(path = "/credits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getCredits(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            Credits credits = creditsService.getCredits(requestId,
                    APIAuthorizationManager.user.getRef());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_GENERATED_TOKEN,
                                    requestId,
                                    generateData("data", credits));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_transactions')")
    @GetMapping(path = "/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getTransactions(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            Credits credits = creditsService.getCreditsAndConsumption(requestId,
                    APIAuthorizationManager.user.getRef());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_GENERATED_TOKEN,
                                    requestId,
                                    generateData("data", credits));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

}
