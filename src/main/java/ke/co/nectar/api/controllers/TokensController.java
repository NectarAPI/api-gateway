package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.exceptions.InvalidTokenException;
import ke.co.nectar.api.controllers.exceptions.MissingTokenOrTokenRefException;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Token;
import ke.co.nectar.api.service.tokens.TokensService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/v1")
public class TokensController extends BaseController {

    @Autowired
    private TokensService tokensService;

    @PreAuthorize("hasPermission(#request,'get_token')")
    @GetMapping(path = "/tokens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getToken(HttpServletRequest request,
                                @RequestParam("ref") String tokenRef,
                                @RequestParam("token") Optional<String> token) {

        String requestId = UUidUtils.generateRef();
        try {
            if (!tokenRef.isBlank()) {
                Token tokenDetails = tokensService.getTokenWithRef(requestId, tokenRef);
                return new ApiResponse(StringConstants.SUCCESS_CODE,
                                        StringConstants.SUCCESS_OBTAINED_TOKEN_DETAILS,
                                        requestId,
                                        generateData("data", tokenDetails));

            } else if (!token.isPresent()) {
                if (token.get().matches("^[0-9]{20}$")) {
                    Token tokenDetails = tokensService.getTokenWithTokenNo(token.get());
                    return new ApiResponse(StringConstants.SUCCESS_CODE,
                            StringConstants.SUCCESS_OBTAINED_TOKEN_DETAILS,
                            requestId,
                            generateData("data", tokenDetails));
                }
                throw new InvalidTokenException(String.format("%s %s",
                        StringConstants.INVALID_TOKEN, token.get()));

            } else {
                throw new MissingTokenOrTokenRefException("Missing token or token ref");
            }

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                     requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'generate_token')")
    @PostMapping(path = "/tokens")
    public ApiResponse generateToken(HttpServletRequest request,
                                        @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            List<Token> tokenDetails = tokensService.generateToken(requestId,
                    APIAuthorizationManager.user.getRef(), params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_GENERATED_TOKEN,
                                    requestId,
                                    generateData("data", tokenDetails));

        } catch (Exception e) {
                    return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request, 'decode_token')")
    @PostMapping(path = "/tokens/{token_no}")
    public ApiResponse decodeToken(HttpServletRequest request,
                                   @PathVariable(value = "token_no") @NotNull String tokenNo,
                                   @RequestBody Map<String, Object> params) {
        String requestID = UUidUtils.generateRef();
        try {
            Map<String, Object> tokenDetails = tokensService.decodeToken(requestID,
                    APIAuthorizationManager.user.getRef(), tokenNo, params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_DECODED_TOKEN,
                    requestID,
                    generateData("data", tokenDetails));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestID);
        }
    }
}
