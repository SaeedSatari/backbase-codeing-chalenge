package com.backbase.data.repository;

import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface RateRepository extends JpaRepository<RateEntity, UUID> {
    Optional<RateEntity> findByUserAndMovie(UserEntity user, MovieEntity movie);
}
