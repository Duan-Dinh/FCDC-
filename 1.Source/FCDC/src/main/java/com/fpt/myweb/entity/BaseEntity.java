package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @JsonProperty
    @Column(name = "createdBy")
    private String createdBy;

    @JsonProperty
    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "modifiedBy")
    private String modifiedBy;

    @Column(name = "modifiedDate")
    private Date modifiedDate;

}
