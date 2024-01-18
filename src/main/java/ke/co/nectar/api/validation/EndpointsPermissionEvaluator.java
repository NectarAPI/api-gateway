package ke.co.nectar.api.validation;

import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Component
public class EndpointsPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private APIAuthorizationManager authorizationManager;

    @Override
    public boolean hasPermission(
            Authentication authentication, Object accessType, Object permission) {
        try {
            if (authentication != null &&
                    accessType instanceof HttpServletRequest &&
                    permission instanceof String) {
                return authorizationManager.validate((HttpServletRequest) accessType,
                        (String) permission);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
