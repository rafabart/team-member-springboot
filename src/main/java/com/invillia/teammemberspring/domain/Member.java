package com.invillia.teammemberspring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends IdAbstract<Long> {

    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false)
    private Team team;
}
