package com.invillia.teammemberspring.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotBlank(message = "O time é obrigatório!")
    private Team team;
}
