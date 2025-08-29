package com.girishma22bce8271.bfhjava.dto;

public class SubmitQueryRequest {
    private String finalQuery;

    public SubmitQueryRequest() {}
    public SubmitQueryRequest(String finalQuery) { this.finalQuery = finalQuery; }
    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
}
