package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "Medicine")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medicine extends BaseEntity{

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "description")
    private String description;

    @ManyToMany(mappedBy = "medicines", fetch = FetchType.LAZY)
    Collection<Daily_Report> dailyReports = new HashSet<>();

}
