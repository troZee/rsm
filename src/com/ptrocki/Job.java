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
    }

    Integer getProcessingTime() {
        return processingTime;
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
