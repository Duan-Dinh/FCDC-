package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "Exercise")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exercise  extends BaseEntity{
    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "description")
    private String description;

    @ManyToMany(mappedBy = "exercises", fetch = FetchType.LAZY)
    Collection<Daily_Report> dailyReports = new HashSet<>();
}
