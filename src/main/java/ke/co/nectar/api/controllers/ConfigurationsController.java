package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.configuration.STSConfiguration;
import ke.co.nectar.api.service.configurations.ConfigurationsService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class ConfigurationsController extends BaseController {

    @Autowired
    private ConfigurationsService configurationsService;

    @PreAuthorize("hasPermission(#request,'create_configurations')")
    @PostMapping(path = "/configurations",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createConfiguration(HttpServletRequest request,
                                           @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            STSConfiguration config = configurationsService.createConfiguration(requestId,
                    APIAuthorizationManager.user.getRef(),
                    params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_CONFIGURATION,
                                    requestId,
                                    generateData("data", config));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_configurations')")
    @GetMapping(path = "/configurations",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getConfigurations(HttpServletRequest request,
                                         @RequestParam("ref") String ref,
                                         @RequestParam("detailed") Boolean detailed) {
        String requestId = UUidUtils.generateRef();

        try {
            STSConfiguration configuration = configurationsService.getConfiguration(
                    requestId,
                    ref,
                    detailed);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_CONFIGURATION,
                                    requestId,
                                    generateData("data", configuration));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_configurations')")
    @PutMapping(path = "/configurations",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activateConfiguration(HttpServletRequest request,
                                             @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            configurationsService.activateConfiguration(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_ACTIVATED_CONFIGURATION,
                                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'deactivate_configurations')")
    @DeleteMapping(path = "/configurations",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivateConfiguration(HttpServletRequest request,
                                               @RequestParam(value = "ref") String ref) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            configurationsService.deactivateConfigurations(requestId, userRef, ref);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_DEACTIVATED_CONFIGURATION,
                                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
