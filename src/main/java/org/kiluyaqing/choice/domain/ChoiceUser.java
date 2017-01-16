package org.kiluyaqing.choice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ChoiceUser.
 */
@Entity
@Table(name = "choice_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChoiceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @Column(name = "user_name", length = 100)
    private String userName;

    @Size(max = 60)
    @Column(name = "password_hash", length = 60)
    private String passwordHash;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @OneToMany(mappedBy = "choiceUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Investigation> investigations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public ChoiceUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public ChoiceUser passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public ChoiceUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public ChoiceUser createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ChoiceUser lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ChoiceUser createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ChoiceUser lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Investigation> getInvestigations() {
        return investigations;
    }

    public ChoiceUser investigations(Set<Investigation> investigations) {
        this.investigations = investigations;
        return this;
    }

    public ChoiceUser addInvestigation(Investigation investigation) {
        investigations.add(investigation);
        investigation.setChoiceUser(this);
        return this;
    }

    public ChoiceUser removeInvestigation(Investigation investigation) {
        investigations.remove(investigation);
        investigation.setChoiceUser(null);
        return this;
    }

    public void setInvestigations(Set<Investigation> investigations) {
        this.investigations = investigations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChoiceUser choiceUser = (ChoiceUser) o;
        if (choiceUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, choiceUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChoiceUser{" +
            "id=" + id +
            ", userName='" + userName + "'" +
            ", passwordHash='" + passwordHash + "'" +
            ", email='" + email + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
