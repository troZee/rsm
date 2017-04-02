package com.ptrocki;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Population {

    List<Chromoson> chromosons;
    List<Chromoson> bestChromos;

    public List<Chromoson> getChromosons() {
        return chromosons;
    }

    public void setChromosons(Chromoson... chromosons) {
        this.chromosons = Arrays.asList(chromosons);
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
        chromosons.add(eddChromo);
        Chromoson wspt = new Chromoson(jobs);
        wspt.wspt();
        chromosons.add(wspt);
        Chromoson spt = new Chromoson(jobs);
        spt.spt();
        chromosons.add(spt);
        Chromoson bwf = new Chromoson(jobs);
        bwf.bwf();
        chromosons.add(bwf);
    }
}
