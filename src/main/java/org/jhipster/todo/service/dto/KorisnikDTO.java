package org.jhipster.todo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Korisnik entity.
 */
public class KorisnikDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String password;


    private Long rolaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRolaId() {
        return rolaId;
    }

    public void setRolaId(Long rolaId) {
        this.rolaId = rolaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KorisnikDTO korisnikDTO = (KorisnikDTO) o;

        if ( ! Objects.equals(id, korisnikDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KorisnikDTO{" +
            "id=" + id +
            ", username='" + username + "'" +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
