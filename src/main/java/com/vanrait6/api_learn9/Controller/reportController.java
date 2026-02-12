package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_report;
import com.vanrait6.api_learn9.Model.Report;
import com.vanrait6.api_learn9.Services.reportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class reportController {
    @Autowired
    private reportService service;

    @PostMapping("/report")
    public ResponseEntity<?> postReport(@RequestBody DTO_report request) {
        try{
            Report report = new Report();
            report.setEmail(request.getEmail());
            report.setPostId(request.getReportId());

            service.addReport(report);
            return ResponseEntity.ok(Map.of("Message", "Report Successfully"));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }
}
