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

    List<Chromoson> crossOver(){
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
