package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.User;
import ke.co.nectar.api.service.users.UserService;
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
public class UsersController extends BaseController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasPermission(#request,'get_user')")
    @GetMapping(path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getUser(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            User obtainedUser = userService.getUser(
                    requestId,
                    APIAuthorizationManager.user.getUsername());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_USER_DETAILS,
                                    requestId,
                                    generateData("data", obtainedUser));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'create_user')")
    @PostMapping(path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createUser(HttpServletRequest request,
                                  @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            String userRef = APIAuthorizationManager.user.getRef();
            String createdUserRef = userService.createUser(requestId, userRef, params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_USER,
                                    requestId,
                                    generateData("user_ref", createdUserRef));

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'update_user')")
    @PutMapping(path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateUser(HttpServletRequest request,
                                  @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
             userService.updateUser(requestId,
                    APIAuthorizationManager.user.getRef(),
                    params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_CREATED_USER,
                                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'delete_user')")
    @DeleteMapping(path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteUser(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            userService.deleteUser(requestId,
                    APIAuthorizationManager.user.getRef());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_DELETED_USER,
                    requestId);

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
