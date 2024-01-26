package ke.co.nectar.api.service.utilities;

import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.Utility;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;
import java.util.Map;

public interface UtilitiesService {

    Utility getUtility(String requestId,
                       String utilityRef)
        throws ApiResponseException;

    List<Meter> getUtilityMeters(String requestId,
                                 String utilityRef)
        throws ApiResponseException;

    String createUtility(String requestId,
                         String userRef,
                         Map<String, Object> params)
        throws ApiResponseException;

    String updateUtility(String requestId,
                       String utilityRef,
                       String userRef,
                       Map<String, Object> params)
        throws ApiResponseException;

    void activateUtility(String requestId,
                         String userRef,
                         String ref)
        throws ApiResponseException;

    void deactivateUtility(String requestId,
                              String userRef,
                              String ref)
        throws ApiResponseException;
}
