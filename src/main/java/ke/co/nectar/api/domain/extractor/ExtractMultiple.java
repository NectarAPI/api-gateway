package ke.co.nectar.api.domain.extractor;

import ke.co.nectar.api.controllers.response.ApiResponse;

import java.util.List;

public interface ExtractMultiple<T> {

    T extractFrom(ApiResponse response);

    List<T> extractMultipleFrom(ApiResponse response);
}
