package com.ptrocki;

import java.util.*;
import java.util.stream.Collectors;

public class Population {

    List<Chromoson> chromosons;
    List<Chromoson> bestChromos;
    Random generator;

    public Population() {
        chromosons = new ArrayList<>();
        bestChromos = new ArrayList<>();
        generator = new Random();
    }

    public void findBestParentsInPopulation() {
        bestChromos = getParentsFrom(chromosons);
    }

    private List<Chromoson> getParentsFrom(List<Chromoson> chromosons) {
        return chromosons.stream().sorted((lhs, rhs) -> lhs.getTardinessSum() > rhs.getTardinessSum()  ? 1 : (lhs.getTardinessSum() < rhs.getTardinessSum() ) ? -1 : 0).limit(2).collect(Collectors.toList());
    }

    void initPopulation(List<Job> jobs) {
        Chromoson eddChromo = new Chromoson(jobs);
        eddChromo.edd();
        eddChromo.updateTardinesFor(eddChromo.getJobs());
        chromosons.add(eddChromo);
        Chromoson wspt = new Chromoson(jobs);
        wspt.wspt();
        wspt.updateTardinesFor(wspt.getJobs());
        chromosons.add(wspt);
        Chromoson spt = new Chromoson(jobs);
        spt.spt();
        spt.updateTardinesFor(spt.getJobs());
        chromosons.add(spt);
        Chromoson bwf = new Chromoson(jobs);
        bwf.bwf();
        bwf.updateTardinesFor(bwf.getJobs());
        chromosons.add(bwf);
    }

    List<Chromoson> crossOver2(){
        int indexOfCrossOver = generator.nextInt(bestChromos.get(0).getJobs().size() - 4 ) + 3;
        List<Job> offspring1 = new ArrayList<>(bestChromos.get(0).getJobs().subList(0, indexOfCrossOver));
        offspring1.addAll(bestChromos.get(1).getJobs().subList(indexOfCrossOver, bestChromos.get(1).getJobs().size()));
        List<Job> offspring2 = new ArrayList<>(bestChromos.get(1).getJobs().subList(0, indexOfCrossOver));
        offspring2.addAll(bestChromos.get(0).getJobs().subList(indexOfCrossOver, bestChromos.get(0).getJobs().size()));
        Chromoson chromo = new Chromoson(offspring1);
        chromo.updateTardinesFor(chromo.getJobs());
        Chromoson chromo1 = new Chromoson(offspring2);
        chromo1.updateTardinesFor(chromo1.getJobs());
        return Arrays.asList(chromo, chromo1);
    }

    List<Chromoson> crossOver(){
        Random crosPosition = new Random();
        int startPoint = 0;
        int endPoint = 0;
        int size = bestChromos.get(0).getJobs().size();
        while (startPoint == endPoint || startPoint < 0 || endPoint < 0){
            startPoint = Math.abs((crosPosition.nextInt()) % size);
            endPoint = Math.abs((crosPosition.nextInt()) % size);
        }
        int temp;
        if (startPoint > endPoint){
            temp = startPoint;
            startPoint = endPoint;
            endPoint = temp;
        }
        Chromoson offspring1 = pmxcrossOver(bestChromos.get(0).getJobs(),bestChromos.get(1).getJobs(),startPoint,endPoint);
        Chromoson offspring2 = pmxcrossOver(bestChromos.get(1).getJobs(),bestChromos.get(0).getJobs(),startPoint,endPoint);
        return Arrays.asList(offspring1,offspring2);
    }

    private Chromoson pmxcrossOver(List<Job> parent1, List<Job> parent2, int startPoint, int endPoint) {
        List<Job> offspring = new ArrayList<>(bestChromos.get(1).getJobs());
        int size = bestChromos.get(1).getJobs().size();
        Job temp;
        for (int i = startPoint; i <= endPoint; i++){
            for (int j = 0; j < size; j++){
                if (parent1.get(i) == parent2.get(j)){
                    temp = offspring.get(j);
                    offspring.set(j, offspring.get(i));
                    offspring.set(i, temp);
                    break;
                }
            }
        }
        return new Chromoson(offspring);
    }

    List<Chromoson> mutation(List<Chromoson> chromosons) {
        List<Chromoson> temp = new ArrayList<>();
        for (Chromoson chromo : chromosons) {
            int indexFrom = generator.nextInt(bestChromos.get(0).getJobs().size() - 5 ) + 3;
            int indexTo = generator.nextInt(bestChromos.get(0).getJobs().size() - 5 );
            if (indexFrom > indexTo) {
                int tempIdx = indexTo;
                indexTo = indexFrom;
                indexFrom = tempIdx;
            }
            Collections.shuffle(chromo.getJobs().subList(indexFrom,indexTo));
            chromo.updateTardinesFor(chromo.getJobs());
            temp.add(chromo);
        }
        return temp;
    }
}
