package ke.co.nectar.api.domain;

import java.util.Date;

public class Nonce {

    private String nonce;
    private Date createdAt;
    private Date updatedAt;

    public Nonce() {}

    public Nonce(String nonce) {
        setNonce(nonce);
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
