package org.kiluyaqing.choice.service.mapper;

import org.kiluyaqing.choice.domain.*;
import org.kiluyaqing.choice.service.dto.QuestionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionMapper {

    @Mapping(source = "investigation.id", target = "investigationId")
    QuestionDTO questionToQuestionDTO(Question question);

    List<QuestionDTO> questionsToQuestionDTOs(List<Question> questions);

    @Mapping(source = "investigationId", target = "investigation")
    @Mapping(target = "answers", ignore = true)
    Question questionDTOToQuestion(QuestionDTO questionDTO);

    List<Question> questionDTOsToQuestions(List<QuestionDTO> questionDTOs);

    default Investigation investigationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Investigation investigation = new Investigation();
        investigation.setId(id);
        return investigation;
    }
}
