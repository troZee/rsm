package com.ptrocki;

import java.util.List;
import java.util.stream.Collectors;

public class Chromoson {

    private List<Job> jobs;
    private int tardinessSum = 0;

    Chromoson(List<Job> jobs) {
        this.jobs = jobs;
        updateTardinesFor(jobs);
    }

    List<Job> getJobs() {
        return jobs;
    }


    int getTardinessSum() {
        return tardinessSum;
    }

    boolean updateTardinesFor(List<Job> jobs) {
        this.jobs = countTardinessSumForEachTask(jobs);
        this.tardinessSum = countTardinessSum(jobs);
        return checkIfPermutationIsCorrect(jobs);
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

    private boolean checkIfPermutationIsCorrect(List<Job> jobs) {
        int completionTime = 0;
        for (Job job : jobs) {
            completionTime += job.getProcessingTime();
            if (job.getDueDate() < completionTime && job.getDueDate() > 0) {
                //tardinessSum = Integer.MAX_VALUE - 100;
                return false;
            }
        }
        return true;
    }

    private int countTardinessSum(List<Job> jobs){
        int sum = 0;
        for (Job job : jobs) {
            sum += job.getTardiness() * job.getWeight();
        }
        return sum;
    }

    private List<Job> countTardinessSumForEachTask(List<Job> jobs) {
        int tempCompetionTime = 0;
        for (Job job : jobs) {
            tempCompetionTime += job.getProcessingTime();
            job.setTardiness(Integer.max(tempCompetionTime + job.getProcessingTime() - job.getDueDate(), 0));
        }
        return jobs;

    }
}
