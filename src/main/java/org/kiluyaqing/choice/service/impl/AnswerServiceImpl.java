package org.kiluyaqing.choice.service.impl;

import org.kiluyaqing.choice.service.AnswerService;
import org.kiluyaqing.choice.domain.Answer;
import org.kiluyaqing.choice.repository.AnswerRepository;
import org.kiluyaqing.choice.service.dto.AnswerDTO;
import org.kiluyaqing.choice.service.mapper.AnswerMapper;
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
 * Service Implementation for managing Answer.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);
    
    @Inject
    private AnswerRepository answerRepository;

    @Inject
    private AnswerMapper answerMapper;

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save
     * @return the persisted entity
     */
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.answerDTOToAnswer(answerDTO);
        answer = answerRepository.save(answer);
        AnswerDTO result = answerMapper.answerToAnswerDTO(answer);
        return result;
    }

    /**
     *  Get all the answers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Answers");
        Page<Answer> result = answerRepository.findAll(pageable);
        return result.map(answer -> answerMapper.answerToAnswerDTO(answer));
    }

    /**
     *  Get one answer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AnswerDTO findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        Answer answer = answerRepository.findOne(id);
        AnswerDTO answerDTO = answerMapper.answerToAnswerDTO(answer);
        return answerDTO;
    }

    /**
     *  Delete the  answer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.delete(id);
    }
}
