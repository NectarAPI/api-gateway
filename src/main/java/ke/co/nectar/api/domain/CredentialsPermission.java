package ke.co.nectar.api.domain;

public class CredentialsPermission {

    private Credentials credentials;
    private Permissions permissions;

    public CredentialsPermission() {}

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Permissions getPermission() {
        return permissions;
    }

    public void setPermission(Permissions permissions) {
        this.permissions = permissions;
    }
}
