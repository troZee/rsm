package com.ptrocki;

public class TotalTardiness {

    private Chromoson chromoson;
    private int solution;

    public TotalTardiness(Chromoson chromoson) {
        this.chromoson = chromoson;
        this.solution = 0;
        solve();
    }

    private void solve() {
        for (int i = 0; i < chromoson.getJobs().size(); ++i) {
            solution += chromoson.getJobs().get(i).getWeight() * tardiness(i);
        }
    }

    private int tardiness(final int index) {
        return Math.max(completionTime(index) - chromoson.getJobs().get(index).getDueDate(), 0);
    }

    private int completionTime(final int index) {
        int completionTime = 0;

        for (int i = 0; i <= index; ++i) {
            completionTime += chromoson.getJobs().get(index).getProcessingTime();
        }
        return completionTime;
    }

    public int getSolution() {
        return solution;
    }
}
