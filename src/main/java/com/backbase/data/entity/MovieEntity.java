package com.backbase.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class MovieEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String title;

    @Column
    private Long boxOffice;

    @OneToMany(mappedBy = "movie")
    private List<RateEntity> rates;
}
