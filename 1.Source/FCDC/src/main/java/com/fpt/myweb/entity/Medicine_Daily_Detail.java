package com.fpt.myweb.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Medicine_Daily_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medicine_Daily_Detail extends BaseEntity {

    @Column(name = "medicine_id")
    private Long medicine;

    @Column(name = "daily_report_id")
    private Long daily_report;
}
