package org.kiluyaqing.choice.service.mapper;

import org.kiluyaqing.choice.domain.*;
import org.kiluyaqing.choice.service.dto.ChoiceUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ChoiceUser and its DTO ChoiceUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChoiceUserMapper {

    ChoiceUserDTO choiceUserToChoiceUserDTO(ChoiceUser choiceUser);

    List<ChoiceUserDTO> choiceUsersToChoiceUserDTOs(List<ChoiceUser> choiceUsers);

    @Mapping(target = "investigations", ignore = true)
    ChoiceUser choiceUserDTOToChoiceUser(ChoiceUserDTO choiceUserDTO);

    List<ChoiceUser> choiceUserDTOsToChoiceUsers(List<ChoiceUserDTO> choiceUserDTOs);
}
