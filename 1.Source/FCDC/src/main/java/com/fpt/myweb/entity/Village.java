package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Village")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Village extends BaseEntity{

    @JsonProperty
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

//    @OneToMany(mappedBy = "village")
//    private List<Medical_Clinic> medical_clinics = new ArrayList<>();

    @OneToMany(mappedBy = "village")
    private List<User> users = new ArrayList<>();

}
