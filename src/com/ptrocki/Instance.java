package com.ptrocki;

import java.util.List;

public class Instance {
    List<Job> jobs;

    public Instance(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
