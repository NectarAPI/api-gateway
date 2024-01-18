package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials implements Extract<Credentials> {

    private String key;
    private String secret;
    private String ref;
    private boolean activated;
    private User user;
    private List<Permissions> permissions;

    public Credentials() {}

    public Credentials(String key, String secret, String ref,
                       boolean activated, User user, List<Permissions> permissions) {
        setKey(key);
        setSecret(secret);
        setRef(ref);
        setActivated(activated);
        setUser(user);
        setPermissions(permissions);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Credentials extractFrom(ApiResponse response) {
        LinkedHashMap credentials = (LinkedHashMap) response.getData().get("credentials");
        return new Credentials((String) credentials.get("key"),
                (String) credentials.get("secret"),
                (String) credentials.get("ref"),
                (Boolean) credentials.get("activated"),
                extractUser((LinkedHashMap) credentials.get("user")),
                extractPermissions((List<LinkedHashMap>) credentials.get("permissions")));
    }

    private User extractUser(LinkedHashMap user) {
        return new User((String) user.get("ref"),
                        (String) user.get("first_name"),
                        (String) user.get("last_name"),
                        (String) user.get("username"),
                        "", // empty password
                        (String) user.get("phone_no"),
                        (String) user.get("email"),
                        user.containsKey("image_url") ?
                                (String) user.get("image_url") : "",
                        Instant.parse((String) user.get("created_at")));
    }

    private List<Permissions> extractPermissions(List<LinkedHashMap> permissions) {
        ArrayList<Permissions> generated = new ArrayList<>();
        permissions.forEach(currPermission -> {
            Permissions p = new Permissions();
            p.setName((String) currPermission.get("name"));
            p.setIdentifier((String) currPermission.get("identifier"));
            p.setRef((String) currPermission.get("ref"));
            p.setNotes((String) currPermission.get("notes"));
            generated.add(p);
        });
        return generated;
    }
}
