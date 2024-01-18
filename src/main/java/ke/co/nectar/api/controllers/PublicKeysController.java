package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.PublicKey;
import ke.co.nectar.api.service.publickeys.PublicKeysService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class PublicKeysController extends BaseController {

    @Autowired
    private PublicKeysService publicKeysService;

    @PreAuthorize("hasPermission(#request,'get_public_keys')")
    @GetMapping(path = "/public_keys",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getPublicKeys(HttpServletRequest request,
                                     @RequestParam(value = "activated", required = false)  boolean activated) {
        String requestId = UUidUtils.generateRef();
        try {
            List<PublicKey> publicKeys = publicKeysService.getPublicKeys(requestId,
                    APIAuthorizationManager.user.getRef(),
                    activated);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_PUBLIC_KEYS_DETAILS,
                                    requestId,
                                    generateData("data", publicKeys));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'create_public_keys')")
    @PostMapping(path = "/public_keys",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createPublicKey(HttpServletRequest request,
                                       @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            PublicKey generatedPublicKey = publicKeysService.createPublicKey(
                    requestId,
                    APIAuthorizationManager.user.getRef(),
                    (String) params.get("name"),
                    (String) params.get("key"),
                    (Boolean) params.get("activated"));
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_PUBLIC_KEY,
                                    requestId,
                                    generateData("data", generatedPublicKey));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_public_keys')")
    @PutMapping(path = "/public_keys",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activatePublicKey(HttpServletRequest request,
                                         @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            publicKeysService.activatePublicKey(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_ACTIVATED_PUBLIC_KEY,
                                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'deactivate_public_keys')")
    @DeleteMapping(path = "/public_keys",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivatePublicKey(HttpServletRequest request,
                                           @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            publicKeysService.deactivatePublicKey(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_DEACTIVATED_PUBLIC_KEY,
                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
