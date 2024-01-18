package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class User  implements Extract<User> {

    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String username;
    private String password;

    @JsonProperty("phone_no")
    private String phoneNo;

    @JsonProperty("image_url")
    private String imageUrl;
    private String ref;
    private String email;
    private boolean activated;

    @JsonProperty("created_at")
    private Instant createdAt;

    public User() {}

    public User(String ref, String firstName, String lastName,
                String username, String password, String phoneNo,
                String email, String imageUrl, Instant createdAt) {
        setRef(ref);
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setPassword(password);
        setPhoneNo(phoneNo);
        setEmail(email);
        setImageUrl(imageUrl);
        setCreatedAt(createdAt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("User {firstName:%s,lastName:%s,username:%s," +
                "password: %s,phoneNo:%s,email:%s,activated:%b,imageURL:%s}",
                firstName, lastName, username, password,
                phoneNo, email, activated, imageUrl);
    }

    @Override
    public User extractFrom(ApiResponse response) {
        LinkedHashMap user = (LinkedHashMap) response.getData().get("user");
        return new User((String) user.get("ref"),
                (String) user.get("first_name"),
                (String) user.get("last_name"),
                (String) user.get("username"),
                (String) user.get("password"),
                (String) user.get("phone_no"),
                (String) user.get("email"),
                user.containsKey("image_url") ?
                        (String) user.get("image_url") : "",
                Instant.parse((String) user.get("created_at")));
    }
}
