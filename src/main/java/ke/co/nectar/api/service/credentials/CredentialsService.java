package ke.co.nectar.api.service.credentials;

import ke.co.nectar.api.domain.Credentials;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

public interface CredentialsService {

    Credentials getCredentialsByRef(String requestId, String ref)
            throws ApiResponseException;

    void activateCredentials(String requestId, String userRef, String ref)
            throws ApiResponseException;

    void deactivateCredentials(String requestId, String userRef, String ref)
            throws ApiResponseException;
}
