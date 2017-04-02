package com.ptrocki;

public class Job {
    private Integer processingTime;
    private Integer weight;
    private Integer dueDate;
    private Integer tardiness;
    private Integer number;


    Job(Integer number, Integer processingTime, Integer weight, Integer dueDate) {
        this.number = number;
        this.processingTime = processingTime;
        this.weight = weight;
        this.dueDate = dueDate;
    }

    Integer getProcessingTime() {
        return processingTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    Integer getWeight() {
        return weight;
    }

    Integer getDueDate() {
        return dueDate;
    }

    Integer getTardiness() {
        return tardiness;
    }

    void setTardiness(Integer tardiness) { this.tardiness = tardiness; }
}
