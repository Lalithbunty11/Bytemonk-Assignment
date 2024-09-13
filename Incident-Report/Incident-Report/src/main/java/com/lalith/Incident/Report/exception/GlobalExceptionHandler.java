package com.lalith.Incident.Report.exception;

import com.lalith.Incident.Report.entity.IncidentReportError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncidentReportError> incidentReportException(InvalidIncidentReportException exception){
        IncidentReportError error = new IncidentReportError();
        error.setMessage(exception.getMessage());
        error.setTime(System.currentTimeMillis());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
