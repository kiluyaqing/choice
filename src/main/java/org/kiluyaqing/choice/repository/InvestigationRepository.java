package org.kiluyaqing.choice.repository;

import org.kiluyaqing.choice.domain.Investigation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Investigation entity.
 */
@SuppressWarnings("unused")
public interface InvestigationRepository extends JpaRepository<Investigation,Long> {

}
