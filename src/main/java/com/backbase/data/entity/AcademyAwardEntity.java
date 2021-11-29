package com.backbase.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "academy_awards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class AcademyAwardEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String year;

    @Column
    private String category;

    @Column(columnDefinition = "text")
    private String nominee;

    @Column
    private String additionalInfo;

    @Column
    private boolean won;
}
