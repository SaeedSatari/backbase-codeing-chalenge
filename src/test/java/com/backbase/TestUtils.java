package com.backbase;

import com.backbase.data.entity.AcademyAwardEntity;
import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static AcademyAwardEntity mockedAcademyAwardEntity() {
        return AcademyAwardEntity
                .builder()
                .nominee("Crash")
                .won(true)
                .additionalInfo("mocked information")
                .id(UUID.randomUUID())
                .year("2004")
                .category("Best Picture")
                .build();
    }

    public static Pageable mockedPageable() {
        return PageRequest.of(0, 10, Sort.by("nominee"));
    }

    public static Page<AcademyAwardEntity> mockedPagedAcademyAwardEntity() {
        List<AcademyAwardEntity> academyAwardEntities = Collections.singletonList(mockedAcademyAwardEntity());
        return (Page<AcademyAwardEntity>) new PageImpl(academyAwardEntities);
    }

    public static UserEntity mockedUserEntity() {
        return UserEntity
                .builder()
                .username("username")
                .password("1234")
                .id(UUID.randomUUID())
                .rates(Collections.singletonList(RateEntity.builder().build()))
                .build();
    }

    public static MovieEntity mockedMovieEntity() {
        return MovieEntity
                .builder()
                .boxOffice(124500L)
                .title("Crash")
                .rates(Collections.singletonList(RateEntity.builder().build()))
                .id(UUID.randomUUID())
                .build();
    }

    public static List<MovieEntity> mockedListMovieEntity() {
        return Collections.singletonList(mockedMovieEntity());
    }

    public static RateEntity mockedRateEntity() {
        return RateEntity
                .builder()
                .rate(BigDecimal.valueOf(8.5))
                .movie(mockedMovieEntity())
                .user(mockedUserEntity())
                .id(UUID.randomUUID())
                .build();
    }
}
