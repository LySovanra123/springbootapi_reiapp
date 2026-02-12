package com.vanrait6.api_learn9.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_report {
    @JsonProperty("repostId_DTO")
    private int reportId;
    @JsonProperty("emailRepost_DTO")
    private String email;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
