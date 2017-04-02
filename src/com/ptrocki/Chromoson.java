package com.ptrocki;

import java.util.List;
import java.util.stream.Collectors;

public class Chromoson {

    private List<Job> jobs;
    private int tardinessSum = 0;


    public Chromoson(List<Job> jobs) {
        this.jobs = jobs;
        tardinessSum = countTardinessSum(jobs);
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public int getTardinessSum() {
        return tardinessSum;
    }

    public void setTardinessSum(int tardinessSum) {
        this.tardinessSum = tardinessSum;
    }

    int countTardinessSum() {
       return countTardinessSum(jobs);
    }

    int countTardinessSum(List<Job> jobs){
        return jobs.stream().mapToInt(Job::getTardiness).sum();
    }

    void edd() {
        jobs = edd(jobs);
    }
    List<Job> edd(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getDueDate() > rhs.getDueDate()  ? 1 : (lhs.getDueDate() < rhs.getDueDate() ) ? -1 : 0).collect(Collectors.toList());
    }

    void wspt() {
        jobs = wspt(jobs);
    }

    List<Job> wspt(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> (lhs.getProcessingTime()/lhs.getWeight()) > (rhs.getProcessingTime()/rhs.getWeight())  ? 1 : (lhs.getProcessingTime()/lhs.getWeight()) > (rhs.getProcessingTime()/rhs.getWeight()) ? -1 : 0).collect(Collectors.toList());
    }

    void spt() {
        jobs = spt(jobs);
    }

    List<Job> spt(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getProcessingTime() > rhs.getProcessingTime()  ? 1 : (lhs.getProcessingTime() < rhs.getProcessingTime() ) ? -1 : 0).collect(Collectors.toList());
    }

    void bwf() {
        jobs = bwf(jobs);
    }

    List<Job> bwf(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getWeight() > rhs.getWeight()  ? -1 : (lhs.getWeight() < rhs.getWeight() ) ? 1 : 0).collect(Collectors.toList());
    }
}
