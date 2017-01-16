package org.kiluyaqing.choice.service;

import org.kiluyaqing.choice.service.dto.InvestigationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Investigation.
 */
public interface InvestigationService {

    /**
     * Save a investigation.
     *
     * @param investigationDTO the entity to save
     * @return the persisted entity
     */
    InvestigationDTO save(InvestigationDTO investigationDTO);

    /**
     *  Get all the investigations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<InvestigationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" investigation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InvestigationDTO findOne(Long id);

    /**
     *  Delete the "id" investigation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
