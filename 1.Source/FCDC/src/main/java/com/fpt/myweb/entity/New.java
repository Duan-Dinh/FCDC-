package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Table(name = "New")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class New extends BaseEntity{
    @JsonProperty
    @Column(name = "title", length = 100000)
    private String title;

    @JsonProperty
    @Column(name = "decription", length = 100000)
    private String decription;

    @JsonProperty
    @Column(name = "imageUrl")
    private String imageUrl;

    @JsonProperty
    @Column(name = "isDelete", columnDefinition = "boolean default false")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

}
