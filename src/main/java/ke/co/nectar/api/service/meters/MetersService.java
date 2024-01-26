package ke.co.nectar.api.service.meters;

import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.MeterType;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;
import java.util.Map;

public interface MetersService {

    Meter getMeter(String requestId, String meterRef)
        throws ApiResponseException;

    List<MeterType> getMeterTypes(String requestId)
            throws ApiResponseException;

    String addMeter(String requestId,
                   String userRef,
                   Map<String, Object> params)
        throws ApiResponseException;

    String updateMeter(String requestId,
                     String userRef,
                     String meterRef,
                     Map<String, Object> params)
        throws ApiResponseException;

    void activateMeter(String requestId,
                       String userRef,
                       String ref)
        throws ApiResponseException;

    void deactivateMeter(String requestId,
                         String userRef,
                         String ref)
        throws ApiResponseException;
}
