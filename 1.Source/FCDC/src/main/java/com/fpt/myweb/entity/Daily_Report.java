package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@Entity
@Table(name = "Daily_Report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Daily_Report extends BaseEntity{

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "bodyTemperature")
    private Double bodyTemperature;

    @Column(name = "oxygenConcentration")
    private Double oxygenConcentration;

    @Column(name = "dateTime")
    private String dateTime;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sysptom_daily_detail",
            joinColumns = @JoinColumn(name = "daily_report_id"),
            inverseJoinColumns = @JoinColumn(name = "sysptom_id"))
    public Collection<Sysptom> sysptoms = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "medicine_daily_detail",
            joinColumns = @JoinColumn(name = "daily_report_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    public Collection<Medicine> medicines = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "exercise_daily_detail",
            joinColumns = @JoinColumn(name = "daily_report_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    public Collection<Exercise> exercises = new HashSet<>();
}
