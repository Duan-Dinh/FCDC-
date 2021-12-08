package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Province")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Province extends BaseEntity{

    @JsonProperty
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "province")
    private List<District> districts = new ArrayList<>();

    public Province(String name) {
    }
}
