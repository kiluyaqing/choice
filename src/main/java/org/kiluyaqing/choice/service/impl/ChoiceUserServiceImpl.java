package org.kiluyaqing.choice.service.impl;

import org.kiluyaqing.choice.service.ChoiceUserService;
import org.kiluyaqing.choice.domain.ChoiceUser;
import org.kiluyaqing.choice.repository.ChoiceUserRepository;
import org.kiluyaqing.choice.service.dto.ChoiceUserDTO;
import org.kiluyaqing.choice.service.mapper.ChoiceUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ChoiceUser.
 */
@Service
@Transactional
public class ChoiceUserServiceImpl implements ChoiceUserService{

    private final Logger log = LoggerFactory.getLogger(ChoiceUserServiceImpl.class);
    
    @Inject
    private ChoiceUserRepository choiceUserRepository;

    @Inject
    private ChoiceUserMapper choiceUserMapper;

    /**
     * Save a choiceUser.
     *
     * @param choiceUserDTO the entity to save
     * @return the persisted entity
     */
    public ChoiceUserDTO save(ChoiceUserDTO choiceUserDTO) {
        log.debug("Request to save ChoiceUser : {}", choiceUserDTO);
        ChoiceUser choiceUser = choiceUserMapper.choiceUserDTOToChoiceUser(choiceUserDTO);
        choiceUser = choiceUserRepository.save(choiceUser);
        ChoiceUserDTO result = choiceUserMapper.choiceUserToChoiceUserDTO(choiceUser);
        return result;
    }

    /**
     *  Get all the choiceUsers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ChoiceUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChoiceUsers");
        Page<ChoiceUser> result = choiceUserRepository.findAll(pageable);
        return result.map(choiceUser -> choiceUserMapper.choiceUserToChoiceUserDTO(choiceUser));
    }

    /**
     *  Get one choiceUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ChoiceUserDTO findOne(Long id) {
        log.debug("Request to get ChoiceUser : {}", id);
        ChoiceUser choiceUser = choiceUserRepository.findOne(id);
        ChoiceUserDTO choiceUserDTO = choiceUserMapper.choiceUserToChoiceUserDTO(choiceUser);
        return choiceUserDTO;
    }

    /**
     *  Delete the  choiceUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChoiceUser : {}", id);
        choiceUserRepository.delete(id);
    }
}
