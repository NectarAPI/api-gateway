package ke.co.nectar.api.service.publickeys;

import ke.co.nectar.api.domain.PublicKey;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;

public interface PublicKeysService {

    List<PublicKey> getPublicKeys(String requestId,
                                  String userRef,
                                  boolean activated)
            throws ApiResponseException;

    PublicKey createPublicKey(String requestId,
                              String userRef,
                              String name,
                              String publicKey,
                              boolean activated)
            throws ApiResponseException;

    void activatePublicKey(String requestId, String userRef, String ref)
            throws ApiResponseException;

    void deactivatePublicKey(String requestId, String userRef, String ref)
            throws ApiResponseException;
}
