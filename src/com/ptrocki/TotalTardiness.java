package com.ptrocki;

import java.util.List;

public class TotalTardiness {

    private Chromoson chromoson;
    private int solution;
    private boolean deadlineExceed;

    public TotalTardiness(Chromoson chromoson) {
        this.chromoson = chromoson;
        this.solution = 0;
        this.deadlineExceed = false;
        solve(chromoson.getJobs());
    }

    public TotalTardiness(List<Job> jobs) {
        this.solution = 0;
        solve(jobs);
    }

    private void solve(List<Job> jobs) {
        for (int i = 0; i < jobs.size(); ++i) {
            solution += jobs.get(i).getWeight() * tardiness(i,jobs);
        }
    }

    private int tardiness(final int index, List<Job> jobs) {
        return Math.max(completionTime(index,jobs) - jobs.get(index).getDueDate(), 0);
    }

    private int completionTime(final int index, List<Job> jobs) {
        int completionTime = 0;

        for (int i = 0; i <= index; ++i) {
            completionTime += jobs.get(index).getProcessingTime();
        }

        return completionTime;
    }


    public int getSolution() {
        return solution;
    }
}
