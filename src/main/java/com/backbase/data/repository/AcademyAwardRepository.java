package com.backbase.data.repository;

import com.backbase.data.entity.AcademyAwardEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademyAwardRepository extends PagingAndSortingRepository<AcademyAwardEntity, UUID> {

    Optional<AcademyAwardEntity> findByCategoryAndNomineeAndWon(String category, String nominee, boolean won);

    AcademyAwardEntity findFirstByOrderByIdAsc();
}