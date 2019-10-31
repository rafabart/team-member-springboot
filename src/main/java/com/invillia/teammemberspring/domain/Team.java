package com.invillia.teammemberspring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Team extends IdAbstract<Long> {

    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Member> members;
}
