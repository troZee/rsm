package com.ptrocki;

import java.util.List;

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
    private int bestTardinessSum = Integer.MAX_VALUE;
    private Chromoson bestChromoson;

    GeneticAlgoritm(List<Job> jobs) {
        this.jobs = jobs;
    }

    void compute(int numberOfIteration) {
        compute(jobs, numberOfIteration);
    }

    private void compute(List<Job> jobs, int numberOfIteration) {
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
    }

    void checkIfPopulationHasTheBestOne(List<Chromoson> chromosons) {
        for (Chromoson chromo : chromosons) {
            if (bestTardinessSum == -1) {
                bestTardinessSum = chromo.getTardinessSum();
                bestChromoson = chromo;
                continue;
            }
            if (chromo.getTardinessSum() < bestTardinessSum) {
                bestTardinessSum = chromo.getTardinessSum();
                bestChromoson = chromo;
            }
        }
    }

    void printSolution() {
        System.out.print("Best tardiness sum is " + bestTardinessSum);
    }
}
