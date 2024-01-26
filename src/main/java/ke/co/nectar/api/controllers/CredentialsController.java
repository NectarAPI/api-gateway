package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credentials;
import ke.co.nectar.api.service.credentials.CredentialsService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/v1")
public class CredentialsController extends BaseController {

    @Autowired
    private CredentialsService credentialsService;

    @PreAuthorize("hasPermission(#request,'get_credentials')")
    @GetMapping(path = "/credentials",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getCredentials(HttpServletRequest request,
                                      @RequestParam("ref") String ref) {
        String requestId = UUidUtils.generateRef();
        try {
            Credentials credentials = credentialsService.getCredentialsByRef(
                    requestId,
                    ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_CREDENTIALS,
                                    requestId,
                                    generateData("data", credentials));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_credentials')")
    @PutMapping(path = "/credentials",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activateCredentials(HttpServletRequest request,
                                           @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            credentialsService.activateCredentials(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_ACTIVATED_CREDENTIALS,
                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'deactivate_credentials')")
    @DeleteMapping(path = "/credentials",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivateCredentials(HttpServletRequest request,
                                             @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            credentialsService.deactivateCredentials(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_DEACTIVATED_CREDENTIALS,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }
}
