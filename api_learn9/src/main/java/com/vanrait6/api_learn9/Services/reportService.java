package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.Model.Report;
import com.vanrait6.api_learn9.Repo.reportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class reportService {
    @Autowired
    private reportRepo repo;

    public void addReport(Report report) {
        repo.save(report);
    }
}
