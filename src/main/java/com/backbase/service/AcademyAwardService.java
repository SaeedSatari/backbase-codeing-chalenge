package com.backbase.service;

import com.backbase.data.entity.AcademyAwardEntity;
import com.backbase.data.repository.AcademyAwardRepository;
import com.backbase.exception.CustomServiceException;
import com.backbase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcademyAwardService {

    private final AcademyAwardRepository academyAwardRepository;

    public AcademyAwardEntity findBestPictureAcademyAward(String name) {
        Optional<AcademyAwardEntity> optionalAcademyAwardDetail = academyAwardRepository.findByCategoryAndNomineeAndWon("Best Picture", name, true);
        return optionalAcademyAwardDetail.orElse(null);
    }

    public Page<AcademyAwardEntity> getAcademyAwards(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AcademyAwardEntity> academyAwards = academyAwardRepository.findAll(paging);
        if (academyAwards.hasContent()) {
            return academyAwards;
        } else {
            throw new CustomServiceException("Nothing found!", ErrorCode.BOX_OFFICE_NOT_FOUND, HttpStatus.NO_CONTENT);
        }
    }
}
