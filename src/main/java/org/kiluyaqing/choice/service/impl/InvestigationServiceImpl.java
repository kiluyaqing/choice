package org.kiluyaqing.choice.service.impl;

import org.kiluyaqing.choice.service.InvestigationService;
import org.kiluyaqing.choice.domain.Investigation;
import org.kiluyaqing.choice.repository.InvestigationRepository;
import org.kiluyaqing.choice.service.dto.InvestigationDTO;
import org.kiluyaqing.choice.service.mapper.InvestigationMapper;
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
 * Service Implementation for managing Investigation.
 */
@Service
@Transactional
public class InvestigationServiceImpl implements InvestigationService{

    private final Logger log = LoggerFactory.getLogger(InvestigationServiceImpl.class);
    
    @Inject
    private InvestigationRepository investigationRepository;

    @Inject
    private InvestigationMapper investigationMapper;

    /**
     * Save a investigation.
     *
     * @param investigationDTO the entity to save
     * @return the persisted entity
     */
    public InvestigationDTO save(InvestigationDTO investigationDTO) {
        log.debug("Request to save Investigation : {}", investigationDTO);
        Investigation investigation = investigationMapper.investigationDTOToInvestigation(investigationDTO);
        investigation = investigationRepository.save(investigation);
        InvestigationDTO result = investigationMapper.investigationToInvestigationDTO(investigation);
        return result;
    }

    /**
     *  Get all the investigations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<InvestigationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Investigations");
        Page<Investigation> result = investigationRepository.findAll(pageable);
        return result.map(investigation -> investigationMapper.investigationToInvestigationDTO(investigation));
    }

    /**
     *  Get one investigation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InvestigationDTO findOne(Long id) {
        log.debug("Request to get Investigation : {}", id);
        Investigation investigation = investigationRepository.findOne(id);
        InvestigationDTO investigationDTO = investigationMapper.investigationToInvestigationDTO(investigation);
        return investigationDTO;
    }

    /**
     *  Delete the  investigation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Investigation : {}", id);
        investigationRepository.delete(id);
    }
}
