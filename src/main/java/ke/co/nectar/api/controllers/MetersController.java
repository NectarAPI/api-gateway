package ke.co.nectar.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.MeterType;
import ke.co.nectar.api.service.meters.MetersService;
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
public class MetersController extends BaseController {

    @Autowired
    private MetersService metersService;

    @PreAuthorize("hasPermission(#request,'get_meter')")
    @GetMapping(path = "/meters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getMeter(HttpServletRequest request,
                                @NotNull @RequestParam("ref") String meterRef) {
        String requestId = UUidUtils.generateRef();
        try {
            Meter meterDetails = metersService.getMeter(requestId, meterRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_METER_DETAILS,
                    requestId,
                    generateData("data", meterDetails));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_meter_types')")
    @GetMapping(path = "/meters/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getMeterTypes(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            List<MeterType> meterTypes = metersService.getMeterTypes(requestId);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_METER_TYPES_DETAILS,
                    requestId,
                    generateData("data", meterTypes));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'create_meter')")
    @PostMapping(path = "/meters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createMeter(HttpServletRequest request,
                                @NotNull @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            String addedMeterRef = metersService.addMeter(requestId, userRef, params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_METER,
                                    requestId,
                                    generateData("ref", addedMeterRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'update_meter')")
    @PutMapping(path = "/meters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateMeter(HttpServletRequest request,
                                   @NotNull @RequestParam(value = "meter_ref") String meterRef,
                                   @NotNull @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            String updatedMeterRef = metersService.updateMeter(requestId, userRef, meterRef, params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_UPDATED_METER,
                    requestId,
                    generateData("ref", updatedMeterRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_meter')")
    @PutMapping(path = "/meters/{meter_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activateMeter(HttpServletRequest request,
                                     @NotNull @PathVariable(value = "meter_ref") String meterRef) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            metersService.activateMeter(requestId, userRef, meterRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_ACTIVATED_METER,
                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'deactivate_meter')")
    @DeleteMapping(path = "/meters/{meter_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivateMeter(HttpServletRequest request,
                                       @NotNull @PathVariable(value = "meter_ref") String meterRef) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            metersService.deactivateMeter(requestId, userRef, meterRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_DEACTIVATED_METER,
                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
