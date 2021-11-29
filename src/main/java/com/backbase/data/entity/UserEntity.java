package com.backbase.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private LocalDateTime tokenExpireDate;

    @OneToMany(mappedBy = "user")
    private List<RateEntity> rates;
}
