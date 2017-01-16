package org.kiluyaqing.choice.service;

import org.kiluyaqing.choice.service.dto.ChoiceUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ChoiceUser.
 */
public interface ChoiceUserService {

    /**
     * Save a choiceUser.
     *
     * @param choiceUserDTO the entity to save
     * @return the persisted entity
     */
    ChoiceUserDTO save(ChoiceUserDTO choiceUserDTO);

    /**
     *  Get all the choiceUsers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChoiceUserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" choiceUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChoiceUserDTO findOne(Long id);

    /**
     *  Delete the "id" choiceUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
