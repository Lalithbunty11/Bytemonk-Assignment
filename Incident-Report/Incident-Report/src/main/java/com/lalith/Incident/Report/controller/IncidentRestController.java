package com.lalith.Incident.Report.controller;


import com.lalith.Incident.Report.entity.IncidentReport;
import com.lalith.Incident.Report.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidentRestController {

    private final IncidentService incidentService;

    @Autowired
    public IncidentRestController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping("/incidents")
    public List<IncidentReport> getAllIncidentReport(){
        return incidentService.fetchIncidentReports();
    }

    @GetMapping("/incidents/{incidentId}")
    public IncidentReport getIncidentReportById(@PathVariable("incidentId") int id){
        return incidentService.findIncidentById(id);
    }

    @PostMapping("/incidents")
    public IncidentReport saveIncidentReport(@RequestBody IncidentReport incidentReport){
        return incidentService.saveIncidentReport(incidentReport);
    }

    @PutMapping("/incidents")
    public IncidentReport updateIncidentReport(@RequestBody IncidentReport incidentReport){
        return incidentService.updateIncidentReport(incidentReport);
    }

    @DeleteMapping("/incidents/{incidentId}")
    public String deleteIncidentReport(@PathVariable("incidentId") int id){
        incidentService.deleteIncidentReport(id);
        return "Successfully deleted incident report with id: %d".formatted(id);
    }

    @GetMapping("/incidents/filterBySecurity/{securityLevel}")
    public List<IncidentReport> getIncidentReportsBySecurityLevel(@PathVariable String securityLevel){
        return incidentService.findIncidentReportsBySecurityLevel(securityLevel);
    }

    @GetMapping("/incidents/getByDateRange")
    public List<IncidentReport> getIncidentReportsByDateRange(@RequestParam("startDate")LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        return incidentService.findIncidentReportsByDateRange(startDate, endDate);
    }

}
