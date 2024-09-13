package com.lalith.Incident.Report.service;

import com.lalith.Incident.Report.entity.IncidentReport;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentService {

    IncidentReport saveIncidentReport(IncidentReport incidentReport);

    IncidentReport updateIncidentReport(IncidentReport incidentReport);

    List<IncidentReport> fetchIncidentReports();

    void deleteIncidentReport(int id);

    IncidentReport findIncidentById(int id);

    List<IncidentReport> findIncidentReportsBySecurityLevel(String securityLevel);

    List<IncidentReport> findIncidentReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
