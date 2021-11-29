package com.backbase.data.repository;

import com.backbase.data.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {
    Optional<MovieEntity> findByTitle(String title);

    @Query(value = "SELECT * FROM MOVIES ORDER BY box_office DESC LIMIT 10", nativeQuery = true)
    List<MovieEntity> findTop10BasedOnBoxOffice();
}

