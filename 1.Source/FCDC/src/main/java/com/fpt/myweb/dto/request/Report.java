package com.fpt.myweb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Report {

    private Long id;

    private Long userId;

    private String comment;

    private Double bodyTemperature;

    private Double breathingRate;

    private Double oxygenConcentration;

    private String listSysptomId;

    private String listMedicineId;

    private String listExerciseId;

    private List<Long> listSysptom;

    private List<Long> listMedicine;

    private List<Long> listExercise;

    private String dateReport;
    private String status;

    private String feedback;




}
