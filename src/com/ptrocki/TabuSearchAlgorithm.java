package com.ptrocki;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/*
s ← s0
 2 sBest ← s
 3 tabuList ← []
 4 while (not stoppingCondition())
 5 	candidateList ← []
 6 	bestCandidate ← null
 7 	for (sCandidate in sNeighborhood)
 8 		if ( (not tabuList.contains(sCandidate)) and (fitness(sCandidate) > fitness(bestCandidate)) )
 9 			bestCandidate ← sCandidate
10 		end
11 	end
12 	s ← bestCandidate
13 	if (fitness(bestCandidate) > fitness(sBest))
14 		sBest ← bestCandidate
15 	end
16 	tabuList.push(bestCandidate);
17 	if (tabuList.size > maxTabuSize)
18 		tabuList.removeFirst()
19 	end
20 end
21 return sBest
 */
public class TabuSearchAlgorithm {

    private List<Job> jobs;
    private List<Job> bestJobs;
    private List<List<Job>> tabu;
    private Duration timeExecution;

    public TabuSearchAlgorithm(List<Job> jobs) {
        this.jobs = jobs;
        this.bestJobs = new ArrayList<>();
        this.tabu = new ArrayList<>();
    }

    public Duration getTimeExecution() {
        return timeExecution;
    }

    public void compute(int iterations, int numberOfNeneighbors, int tabuSize) {
        Instant start = Instant.now();
        this.bestJobs = computeBestCandidate(jobs);
        List<Job> bestJobs = this.bestJobs;
        for (int index = 0; index < iterations; index++) {
            List<List<Job>> neighborhoods = generateNeighborhood(numberOfNeneighbors, jobs);
            for (List<Job> neighborhood : neighborhoods) {
                if (!findIn(tabu, neighborhood) && new TotalTardiness(neighborhood).getSolution() < new TotalTardiness(bestJobs).getSolution()) {
                    bestJobs = neighborhood;
                }
            }
            if (new TotalTardiness(this.bestJobs).getSolution() < new TotalTardiness(bestJobs).getSolution()) {
                this.bestJobs = bestJobs;
            }
            tabu.add(bestJobs);
            if (tabu.size() > tabuSize) {
                tabu.remove(0);
            }
        }
        Instant end = Instant.now();
        timeExecution = Duration.between(start, end);
    }

    public boolean findIn(List<List<Job>> tabuList, List<Job> jobsList) {

        for (List<Job> tabu : tabuList) {
            for (int i = 0; i < jobsList.size(); i++) {
                if (Objects.equals(tabu.get(i).getNumber(), jobsList.get(i).getNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printSolution() {
        System.out.println("Best tardiness sum is " + new TotalTardiness(bestJobs).getSolution());
        System.out.println("Tasks order :");
        bestJobs.forEach(item ->
                System.out.print(item.getNumber() + " -> ")
        );
        System.out.println("");
        if (hasDuplicatedJobs()) {
            System.out.print("List has duplicates");
        } else {
            System.out.print("No duplicates");
        }
    }

    boolean hasDuplicatedJobs() {
        Set<Integer> encounteredNumbers = new HashSet<>();
        for (Job job : bestJobs) {
            if (encounteredNumbers.contains(job.getNumber())) {
                return true;
            }
            encounteredNumbers.add(job.getNumber());
        }
        return false;
    }

    private List<List<Job>> generateNeighborhood(int numberOfNeneighbors, List<Job> jobs) {
        List<List<Job>> temp = new ArrayList<>();
        for (int index = 0; index < numberOfNeneighbors; index++) {
            temp.add(swap(jobs));
        }
        return temp;
    }

    private List<Job> swap(List<Job> jobs) {
        int idx1 = new Random().nextInt(jobs.size());
        int idx2 = new Random().nextInt(jobs.size());
        Collections.swap(jobs, idx1, idx2);
        return jobs;
    }

    private List<Job> computeBestCandidate(List<Job> jobs) {
        List<List<Job>> candidates = new ArrayList<>();
        int bestTardiness = Integer.MAX_VALUE;
        List<Job> bestJobs = new ArrayList<>();
        candidates.addAll(Arrays.asList(edd(jobs), wspt(jobs), spt(jobs), bwf(jobs)));
        for (List<Job> candidate : candidates) {
            int temp = new TotalTardiness(candidate).getSolution();
            if (new TotalTardiness(candidate).getSolution() < bestTardiness) {
                bestJobs = candidate;
                bestTardiness = temp;
            }
        }
        return bestJobs;
    }

    List<Job> edd(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getDueDate() > rhs.getDueDate() ? 1 : (lhs.getDueDate() < rhs.getDueDate()) ? -1 : 0).collect(Collectors.toList());
    }


    List<Job> wspt(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> (lhs.getProcessingTime() / lhs.getWeight()) > (rhs.getProcessingTime() / rhs.getWeight()) ? 1 : (lhs.getProcessingTime() / lhs.getWeight()) > (rhs.getProcessingTime() / rhs.getWeight()) ? -1 : 0).collect(Collectors.toList());
    }


    List<Job> spt(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getProcessingTime() > rhs.getProcessingTime() ? 1 : (lhs.getProcessingTime() < rhs.getProcessingTime()) ? -1 : 0).collect(Collectors.toList());
    }

    List<Job> bwf(List<Job> jobs) {
        return jobs.stream().sorted((lhs, rhs) -> lhs.getWeight() > rhs.getWeight() ? -1 : (lhs.getWeight() < rhs.getWeight()) ? 1 : 0).collect(Collectors.toList());
    }
}
