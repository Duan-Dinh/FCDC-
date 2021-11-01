package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Sysptom_Daily_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sysptom_Daily_Detail extends BaseEntity {

    @Column(name = "sysptom_id")
    private Long sysptom;

    @Column(name = "daily_report_id")
    private Long daily_report;
}
