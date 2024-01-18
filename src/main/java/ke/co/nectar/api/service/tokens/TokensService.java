package ke.co.nectar.api.service.tokens;

import ke.co.nectar.api.domain.Token;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;
import java.util.Map;

public interface TokensService {

    List<Token> generateToken(String requestId, String userRef, Map<String, Object> params)
        throws Exception;

    Token getTokenWithRef(String requestId, String ref)
        throws ApiResponseException;

    Token getTokenWithTokenNo(String tokenNo);

    Map<String, Object>  decodeToken(String requestID, String userRef, String token, Map<String, Object> params)
            throws Exception;
}
