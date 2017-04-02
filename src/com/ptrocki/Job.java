package com.ptrocki;

public class Job {
    private Integer processingTime;
    private Integer weight;
    private Integer dueDate;
    private Integer tardiness;


    Job(Integer processingTime, Integer weight, Integer dueDate) {
        this.processingTime = processingTime;
        this.weight = weight;
        this.dueDate = dueDate;
        this.tardiness = Math.max(0,processingTime-dueDate);
    }

    public Integer getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Integer processingTime) {
        this.processingTime = processingTime;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDueDate() {
        return dueDate;
    }

    public void setDueDate(Integer dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getTardiness() {
        return tardiness;
    }

    public void setTardiness(Integer tardiness) {
        this.tardiness = tardiness;
    }
}
