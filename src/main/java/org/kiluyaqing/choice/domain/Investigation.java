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
 * A Investigation.
 */
@Entity
@Table(name = "investigation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Investigation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 1024)
    @Column(name = "content", length = 1024)
    private String content;

    @Size(max = 1024)
    @Column(name = "remark", length = 1024)
    private String remark;

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

    @ManyToOne
    private ChoiceUser choiceUser;

    @OneToMany(mappedBy = "investigation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Investigation content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public Investigation remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Investigation createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Investigation lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Investigation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Investigation lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ChoiceUser getChoiceUser() {
        return choiceUser;
    }

    public Investigation choiceUser(ChoiceUser choiceUser) {
        this.choiceUser = choiceUser;
        return this;
    }

    public void setChoiceUser(ChoiceUser choiceUser) {
        this.choiceUser = choiceUser;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Investigation questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Investigation addQuestion(Question question) {
        questions.add(question);
        question.setInvestigation(this);
        return this;
    }

    public Investigation removeQuestion(Question question) {
        questions.remove(question);
        question.setInvestigation(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investigation investigation = (Investigation) o;
        if (investigation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, investigation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Investigation{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", remark='" + remark + "'" +
            ", createdDate='" + createdDate + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
