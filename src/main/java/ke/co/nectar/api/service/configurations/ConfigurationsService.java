package ke.co.nectar.api.service.configurations;

import ke.co.nectar.api.domain.configuration.STSConfiguration;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.Map;

public interface ConfigurationsService {

    STSConfiguration createConfiguration(String requestId,
                               String userRef,
                               Map<String, Object> params)
            throws ApiResponseException, InvalidConfigurationTypeException;

    STSConfiguration getConfiguration(String requestId,
                                      String ref,
                                      boolean detailed)
            throws ApiResponseException, InvalidConfigurationTypeException;

    void activateConfiguration(String requestId, String userRef, String ref)
            throws ApiResponseException;

    void deactivateConfigurations(String requestId, String userRef, String ref)
            throws ApiResponseException;


}
