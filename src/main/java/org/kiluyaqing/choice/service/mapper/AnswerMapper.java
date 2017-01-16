package org.kiluyaqing.choice.service.mapper;

import org.kiluyaqing.choice.domain.*;
import org.kiluyaqing.choice.service.dto.AnswerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnswerMapper {

    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO answerToAnswerDTO(Answer answer);

    List<AnswerDTO> answersToAnswerDTOs(List<Answer> answers);

    @Mapping(source = "questionId", target = "question")
    Answer answerDTOToAnswer(AnswerDTO answerDTO);

    List<Answer> answerDTOsToAnswers(List<AnswerDTO> answerDTOs);

    default Question questionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
