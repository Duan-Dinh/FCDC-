package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

        @JsonProperty
    @Column(name = "username")
    private String username;

    @JsonProperty
    @Column(name = "result")
    private String result;

    @JsonProperty
    @Column(name = "fullname")
    private String fullname;

    @JsonProperty
    @Column(name = "gender")
    private String gender;


    @JsonProperty
    @Column(name = "password")
    private String password;

//    @JsonProperty
//    @Column(name = "firstname")
//    private String firstname;

//    @JsonProperty
//    @Column(name = "lastname")
//    private String lastname;

    @JsonProperty
    @Column(name = "email")
    private String email;

    @JsonProperty
    @Column(name = "phone")
    private String phone;

    @JsonProperty
    @Column(name = "address")
    private String address;

    @JsonProperty
    @Column(name = "imageUrl")
    private String imageUrl;

    @JsonProperty
    @Column(name = "is_active")
    private String is_active;

    @JsonProperty
    @Column(name = "currentStatus")
    private String currentStatus;
    @JsonProperty
    @Column(name = "typeTakeCare")
    private String typeTakeCare;

    @JsonProperty
    @Column(name = "birthOfdate")
    private Date birthOfdate;

    @JsonProperty
    @Column(name = "dateStart")
    private Date dateStart;

    // Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; // chỗ ni trung với mapby bên User
    // Dia chi (village)
    @ManyToOne
    @JoinColumn(name = "village_id")
    private Village village;
    // new
    @OneToMany(mappedBy = "user")
    private List<New> news = new ArrayList<>();
    // daily_report
    @OneToMany(mappedBy = "user")
    private List<Daily_Report> daily_reports = new ArrayList<>();
    // doctor
//    @OneToMany(mappedBy = "user")
//    private List<Medical_Doctor> medical_doctors = new ArrayList<>();
//    //staff
//    @OneToMany(mappedBy = "user")
//    private List<Medical_Staff> medical_staffs = new ArrayList<>();


}
