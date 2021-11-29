package com.backbase.data;

import com.backbase.data.entity.AcademyAwardEntity;
import com.backbase.data.repository.AcademyAwardRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DBInit {

    private final AcademyAwardRepository academyAwardRepository;

    public void init() {
        AcademyAwardEntity firstAcademyAward = academyAwardRepository.findFirstByOrderByIdAsc();
        if (Objects.nonNull(firstAcademyAward))
            return;
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("/var/files/academy_awards.csv"))
                .withSkipLines(1)
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                academyAwardRepository.save(
                        AcademyAwardEntity
                                .builder()
                                .year(values[0])
                                .category(values[1])
                                .nominee(values[2])
                                .additionalInfo(values[3])
                                .won(values[4].equals("YES"))
                                .build());
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}

