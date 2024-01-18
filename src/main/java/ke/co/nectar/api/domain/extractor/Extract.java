package ke.co.nectar.api.domain.extractor;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;

public interface Extract<T> {

    T extractFrom(ApiResponse response) throws InvalidConfigurationTypeException;
}
