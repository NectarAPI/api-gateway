package ke.co.nectar.api.service.users;

import ke.co.nectar.api.domain.User;
import ke.co.nectar.api.domain.Utility;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUser(String requestId,
                 String username)
            throws ApiResponseException;

    String createUser(String requestId,
                    String userRef,
                    Map<String, Object> params)
            throws ApiResponseException;

    void deleteUser(String requestId,
                    String ref)
            throws ApiResponseException;

    void updateUser(String requestId,
                    String ref,
                    Map<String, Object> params)
            throws ApiResponseException;

    List<Utility> getUtilities(String requestId,
                               String userRef)
        throws ApiResponseException;
}
