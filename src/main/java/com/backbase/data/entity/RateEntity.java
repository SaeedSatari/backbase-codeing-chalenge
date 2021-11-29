package com.backbase.data.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RateEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
