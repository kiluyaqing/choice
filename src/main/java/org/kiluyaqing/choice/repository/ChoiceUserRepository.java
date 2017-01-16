package org.kiluyaqing.choice.repository;

import org.kiluyaqing.choice.domain.ChoiceUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChoiceUser entity.
 */
@SuppressWarnings("unused")
public interface ChoiceUserRepository extends JpaRepository<ChoiceUser,Long> {

}
