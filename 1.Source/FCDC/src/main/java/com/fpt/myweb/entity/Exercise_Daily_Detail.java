package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Exercise_Daily_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exercise_Daily_Detail extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "daily_report_id")
    private Daily_Report daily_report;
}
