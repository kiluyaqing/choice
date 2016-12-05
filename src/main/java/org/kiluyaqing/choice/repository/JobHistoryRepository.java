package org.kiluyaqing.choice.repository;

import org.kiluyaqing.choice.domain.JobHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
public interface JobHistoryRepository extends JpaRepository<JobHistory,Long> {

}
