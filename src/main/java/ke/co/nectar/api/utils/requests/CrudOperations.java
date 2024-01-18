package ke.co.nectar.api.utils.requests;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;

public interface CrudOperations {

    ApiResponse get(BasicAuthCredentials credential,
                    String path)
            throws ApiResponseException;

    ApiResponse post(BasicAuthCredentials credential,
                     String path,
                     String payload)
            throws ApiResponseException;

    ApiResponse delete(BasicAuthCredentials credential,
                       String path)
            throws ApiResponseException;

    ApiResponse put(BasicAuthCredentials credential,
                    String path,
                    String payload)
            throws ApiResponseException;

}
