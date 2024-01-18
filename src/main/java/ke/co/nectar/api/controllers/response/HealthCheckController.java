package ke.co.nectar.api.controllers.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@ResponseStatus(HttpStatus.OK)
public class HealthCheckController {

    @GetMapping(path = "/health-check")
    public String healthCheck(HttpServletRequest request) {
        return "Healthy";
    }
}
