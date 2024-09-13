package com.lalith.Incident.Report.repository;

import com.lalith.Incident.Report.entity.IncidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IncidentRepository extends JpaRepository<IncidentReport, Integer> {

    @Query("SELECT i FROM IncidentReport i WHERE i.securityLevel = :securityLevel")
    List<IncidentReport> findIncidentsBySecurityLevel(@Param("securityLevel") String securityLevel);

    @Query("SELECT i FROM IncidentReport i WHERE i.incidentDate BETWEEN :startDate AND :endDate")
    List<IncidentReport> findIncidentReportsByDateRange(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

}
