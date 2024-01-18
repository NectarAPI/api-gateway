package ke.co.nectar.api.service.credits;

import ke.co.nectar.api.domain.Credits;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

public interface CreditsService {

    Credits getCredits(String requestId, String userRef)
                throws ApiResponseException;

    Credits getCreditsAndConsumption(String requestId, String userRef)
            throws ApiResponseException;
}
