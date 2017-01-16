package org.kiluyaqing.choice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Investigation entity.
 */
public class InvestigationDTO implements Serializable {

    private Long id;

    @Size(max = 1024)
    private String content;

    @Size(max = 1024)
    private String remark;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    @Size(max = 50)
    private String createdBy;

    @Size(max = 50)
    private String lastModifiedBy;


    private Long choiceUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getChoiceUserId() {
        return choiceUserId;
    }

    public void setChoiceUserId(Long choiceUserId) {
        this.choiceUserId = choiceUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvestigationDTO investigationDTO = (InvestigationDTO) o;

        if ( ! Objects.equals(id, investigationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvestigationDTO{" +
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
