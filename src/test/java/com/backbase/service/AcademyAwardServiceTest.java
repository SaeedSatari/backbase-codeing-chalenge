package com.backbase.service;

import com.backbase.data.entity.AcademyAwardEntity;
import com.backbase.data.repository.AcademyAwardRepository;
import com.backbase.exception.CustomServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.backbase.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AcademyAwardServiceTest {

    @InjectMocks
    AcademyAwardService academyAwardService;

    @Mock
    AcademyAwardRepository academyAwardRepository;

    @Test
    @DisplayName("findBestPictureAcademyAward should returns AcademyAwardEntity, when given name is BestPicture winner")
    void findBestPictureAcademyAward_shouldReturnsAcademyAwardEntity_whenGivenNameIsBestPictureWinner() {
        AcademyAwardEntity mockedAcademyAwardEntity = mockedAcademyAwardEntity();
        when(academyAwardRepository.findByCategoryAndNomineeAndWon("Best Picture", "Crash", true)).thenReturn(Optional.of(mockedAcademyAwardEntity));
        AcademyAwardEntity actualAcademyAward = academyAwardService.findBestPictureAcademyAward("Crash");
        assertNotNull(actualAcademyAward);
    }

    @Test
    @DisplayName("findBestPictureAcademyAward should returns null, when given name is invalid")
    void findBestPictureAcademyAward_shouldReturnsNull_whenGivenNameIsInvalid() {
        when(academyAwardRepository.findByCategoryAndNomineeAndWon("Best Picture", "invalid-movie", true)).thenReturn(Optional.empty());
        AcademyAwardEntity actualAcademyAward = academyAwardService.findBestPictureAcademyAward("invalid-movie");
        assertNull(actualAcademyAward);
    }

    @Test
    @DisplayName("getAcademyAwards should throws new CustomException, when NothingFound")
    void getAcademyAwards_shouldThrowsNewCustomException_whenNothingFound() {
        Page<AcademyAwardEntity> pagedResponse = new PageImpl(new ArrayList<>());
        when(academyAwardRepository.findAll(mockedPageable())).thenReturn(pagedResponse);
        CustomServiceException thrown = assertThrows(
                CustomServiceException.class,
                () -> academyAwardService.getAcademyAwards(0, 10, "nominee"),
                "Nothing found!"
        );
        assertTrue(thrown.getMessage().contains("Nothing found!"));
    }

    @Test
    @DisplayName("getAcademyAwards should returns Paged AcademyAwardEntity, when records are existing")
    void getAcademyAwards_shouldReturnsPagedAcademyAwardEntity_whenRecordsAreExisting() {
        Page<AcademyAwardEntity> pagedResponse = mockedPagedAcademyAwardEntity();
        when(academyAwardRepository.findAll(mockedPageable())).thenReturn(pagedResponse);
        Page<AcademyAwardEntity> actualContent = academyAwardService.getAcademyAwards(0, 10, "nominee");
        assertNotNull(actualContent.getContent());
    }
}