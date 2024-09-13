package com.lalith.Incident.Report.service;

import com.lalith.Incident.Report.entity.IncidentReport;
import com.lalith.Incident.Report.exception.InvalidIncidentReportException;
import com.lalith.Incident.Report.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class IncidentServiceImpl implements IncidentService{

    private List<String> securityLevels = new ArrayList<>(
            List.of("LOW", "MEDIUM", "HIGH")
    );

    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public IncidentReport saveIncidentReport(IncidentReport incidentReport) {
        validateIncidentReport(incidentReport);
        incidentReport.setId(0);
        return incidentRepository.save(incidentReport);
    }

    @Override
    public IncidentReport updateIncidentReport(IncidentReport incidentReport) {
        Optional<IncidentReport> optional = incidentRepository.findById(incidentReport.getId());
        if(optional.isEmpty()){
            throw new InvalidIncidentReportException("No incident report exists with the provided id");
        }
        return incidentRepository.save(incidentReport);
    }

    @Override
    public List<IncidentReport> fetchIncidentReports() {
        return incidentRepository.findAll();
    }

    @Override
    public void deleteIncidentReport(int id) {
        incidentRepository.deleteById(id);
    }

    @Override
    public IncidentReport findIncidentById(int id) {
        Optional<IncidentReport> optional = incidentRepository.findById(id);
        if(optional.isEmpty()){
            throw new InvalidIncidentReportException("No Incident report is present with the given id");
        }
        return optional.get();
    }

    @Override
    public List<IncidentReport> findIncidentReportsBySecurityLevel(String securityLevel) {
        securityLevel = securityLevel.toUpperCase();
        if(!securityLevels.contains(securityLevel)){
            throw new InvalidIncidentReportException("Invalid security level");
        }
        return incidentRepository.findIncidentsBySecurityLevel(securityLevel);
    }

    @Override
    public List<IncidentReport> findIncidentReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return incidentRepository.findIncidentReportsByDateRange(startDate, endDate);
    }

    private void validateIncidentReport(IncidentReport incidentReport){
        String message = null;

        if(incidentReport.getTitle().length() < 10){
            message = "Title length must be at least 10 characters";
        } else if(incidentReport.getDescription().isBlank()){
            message = "Description cannot be blank";
        } else if(!securityLevels.contains(incidentReport.getSecurityLevel().toUpperCase())){
            // validate security level
            message = "Invalid security level";
        } else{
            // security level is valid. so convert it to upper case
            incidentReport.setSecurityLevel(incidentReport.getSecurityLevel().toUpperCase());

            // validate date. Should not belong to future or more than 30 days in past
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime past30Days = now.minusDays(30);

            // check if the date belongs to future or more than 30 days past
            if(now.isBefore(incidentReport.getIncidentDate())){
                message = "Invalid date. Future dates cannot be provided";
            } else if(past30Days.isAfter(incidentReport.getIncidentDate())){
                message = "Invalid date. Incident must belong to only past 30 days";
            }
        }

        if(message != null){
            throw new InvalidIncidentReportException(message);
        }

    }


}
