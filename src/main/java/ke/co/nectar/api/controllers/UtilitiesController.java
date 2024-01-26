package ke.co.nectar.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.Utility;
import ke.co.nectar.api.service.users.UserService;
import ke.co.nectar.api.service.utilities.UtilitiesService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class UtilitiesController extends  BaseController {

    @Autowired
    private UtilitiesService utilitiesService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasPermission(#request,'get_utilities')")
    @GetMapping(path = "/utilities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getUtilities(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            String userRef = APIAuthorizationManager.user.getRef();
            List<Utility> utilitiesDetails = userService.getUtilities(requestId, userRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_UTILITY_DETAILS,
                                    requestId,
                                    generateData("data", utilitiesDetails));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_utility')")
    @GetMapping(path = "/utilities", produces = MediaType.APPLICATION_JSON_VALUE,
            params = { "utility_ref" })
    public ApiResponse getUtility(HttpServletRequest request,
                                  @NotNull @RequestParam("utility_ref") String utilityRef) {
        String requestId = UUidUtils.generateRef();
        try {
            Utility utilityDetails = utilitiesService.getUtility(requestId, utilityRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_UTILITY_DETAILS,
                                    requestId,
                                    generateData("data", utilityDetails));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_utility_meters')")
    @GetMapping(path = "/utilities/{utility_ref}/meters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getMetersForUtility(HttpServletRequest request,
                                           @NotNull @PathVariable("utility_ref") String utilityRef) {
        String requestId = UUidUtils.generateRef();
        try {
            List<Meter> utilityMeters = utilitiesService.getUtilityMeters(requestId, utilityRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_UTILITY_METERS_DETAILS,
                    requestId,
                    generateData("data", utilityMeters));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'create_utility')")
    @PostMapping(path = "/utilities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createUtility(HttpServletRequest request,
                                  @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            String utilityRef = utilitiesService.createUtility(requestId,
                                        APIAuthorizationManager.user.getRef(),
                                        params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_UTILITY,
                                    requestId,
                                    generateData("ref", utilityRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'update_utility')")
    @PutMapping(path = "/utilities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateUtility(HttpServletRequest request,
                                     @RequestParam("utility_ref") String utilityRef,
                                     @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            String updatedUtilityRef = utilitiesService.updateUtility(requestId, utilityRef,
                                            userRef, params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_UPDATED_UTILITY,
                                    requestId,
                                    generateData("ref", updatedUtilityRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_utility')")
    @PutMapping(path = "/utilities/{utility_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activateUtility(HttpServletRequest request,
                                       @NotNull @PathVariable("utility_ref") String utilityRef) {
        String requestId = UUidUtils.generateRef();
        try {
            utilitiesService.activateUtility(requestId,
                    APIAuthorizationManager.user.getRef(),
                    utilityRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_ACTIVATED_UTILITY,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_utility')")
    @DeleteMapping(path = "/utilities/{utility_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivateUtility(HttpServletRequest request,
                                         @NotNull @PathVariable("utility_ref") String utilityRef) {
        String requestId = UUidUtils.generateRef();
        try {
            utilitiesService.deactivateUtility(requestId,
                                                APIAuthorizationManager.user.getRef(),
                                                utilityRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_DEACTIVATED_UTILITY,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
