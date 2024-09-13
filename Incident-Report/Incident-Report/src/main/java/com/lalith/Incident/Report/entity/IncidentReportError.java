package com.lalith.Incident.Report.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentReportError {

    private int statusCode;

    private String message;

    private long time;

}
