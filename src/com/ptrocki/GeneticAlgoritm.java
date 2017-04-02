package com.ptrocki;

import java.util.List;

public class GeneticAlgoritm {

    List<Job> jobs;

    public GeneticAlgoritm(List<Job> jobs) {
        this.jobs = jobs;
    }

    void compute(int numberOfIteration){
        compute(jobs, numberOfIteration);
    }

    void compute(List<Job> jobs, int numberOfIteration) {
        Population population = new Population();
        population.initPopulation(jobs);
        population.findBestParentsInPopulation();

        for(int i=0; i<numberOfIteration; i++) {
            //selection
            //crossover
            //mutation
        }


    }
}
