package org.kiluyaqing.choice.service.mapper;

import org.kiluyaqing.choice.domain.*;
import org.kiluyaqing.choice.service.dto.InvestigationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Investigation and its DTO InvestigationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvestigationMapper {

    @Mapping(source = "choiceUser.id", target = "choiceUserId")
    InvestigationDTO investigationToInvestigationDTO(Investigation investigation);

    List<InvestigationDTO> investigationsToInvestigationDTOs(List<Investigation> investigations);

    @Mapping(source = "choiceUserId", target = "choiceUser")
    @Mapping(target = "questions", ignore = true)
    Investigation investigationDTOToInvestigation(InvestigationDTO investigationDTO);

    List<Investigation> investigationDTOsToInvestigations(List<InvestigationDTO> investigationDTOs);

    default ChoiceUser choiceUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        ChoiceUser choiceUser = new ChoiceUser();
        choiceUser.setId(id);
        return choiceUser;
    }
}
