package com.invillia.teammemberspring.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Team extends IdAbstract<Long> {

    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members;
}
