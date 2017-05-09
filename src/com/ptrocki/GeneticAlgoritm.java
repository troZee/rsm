package com.ptrocki;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
[Start] Generate random population of n chromosomes (suitable solutions for the problem)
[Fitness] Evaluate the fitness f(x) of each chromosome x in the population
[New population] Create a new population by repeating following steps until the new population is complete
    [Selection] Select two parent chromosomes from a population according to their fitness (the better fitness, the bigger chance to be selected)
    [Crossover] With a crossover probability cross over the parents to form a new offspring (children). If no crossover was performed, offspring is an exact copy of parents.
    [Mutation] With a mutation probability mutate new offspring at each locus (position in chromosome).
    [Accepting] Place new offspring in a new population
[Replace] Use new generated population for a further run of algorithm
[Test] If the end condition is satisfied, stop, and return the best solution in current population
[Loop] Go to step 2
 */
public class GeneticAlgoritm {

    private List<Job> jobs;
    private int bestTardinessSum = -Integer.MAX_VALUE;
    private Chromoson bestChromoson;
    private Duration timeExecution;

    GeneticAlgoritm(List<Job> jobs) {
        this.jobs = jobs;
    }

    void compute(int numberOfIteration) {
        compute(jobs, numberOfIteration);
    }

    public Duration getTimeExecution() {
        return timeExecution;
    }

    private void compute(List<Job> jobs, int numberOfIteration) {
        Instant start = Instant.now();

        Population population = new Population();
        population.initPopulation(jobs);
        checkIfPopulationHasTheBestOne(population.chromosons);
        for (int i = 0; i < numberOfIteration; i++) {
            population.findBestParentsInPopulation();
            List<Chromoson> crossOvers = population.crossOver();
            checkIfPopulationHasTheBestOne(crossOvers);
            List<Chromoson> mutations = population.mutation(crossOvers);
            checkIfPopulationHasTheBestOne(mutations);
            population.chromosons.clear();
            population.chromosons.addAll(population.bestChromos);
            population.chromosons.addAll(mutations);
        }
        Instant end = Instant.now();
        timeExecution = Duration.between(start, end);
    }

    void checkIfPopulationHasTheBestOne(List<Chromoson> chromosons) {

        for (Chromoson chromo : chromosons) {
            TotalTardiness totalTardiness = new TotalTardiness(chromo);
            if (totalTardiness.getSolution() < bestTardinessSum || bestTardinessSum == -Integer.MAX_VALUE) {
                bestTardinessSum = totalTardiness.getSolution();
                bestChromoson = chromo;
            }
        }
    }

    void printSolution() {
        System.out.println("Best tardiness sum is " + bestTardinessSum);
        System.out.println("Tasks order :");
        bestChromoson.getJobs().forEach( item ->
            System.out.print(item.getNumber() + " -> " )
        );
        System.out.println("");
        if (hasDuplicatedJobs()) {
            System.out.println("List has duplicates");}
            else {
            System.out.println("No duplicates");
        }
        System.out.println("Number of tasks"+String.valueOf(bestChromoson.getJobs().size()));
    }

    boolean hasDuplicatedJobs() {
        Set<Integer> encounteredNumbers = new HashSet<>();
        for (Job job : bestChromoson.getJobs()) {
            if (encounteredNumbers.contains(job.getNumber())) {
                return true;
            }
            encounteredNumbers.add(job.getNumber());
        }
        return false;
    }
}
