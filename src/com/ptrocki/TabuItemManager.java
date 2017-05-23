package com.ptrocki;

import java.util.*;

public class TabuItemManager {

    private List<ProhibitedMove> tabu;
    private int tabuSize;

    public void setTabuSize(int tabuSize) {
        this.tabuSize = tabuSize;
    }

    public TabuItemManager() {
        this.tabu = new ArrayList<>();
        tabuSize = 0;
    }

    public void add(ProhibitedMove prohibitedMove) {
        tabu.add(prohibitedMove);
        if (tabu.size() > tabuSize) tabu.remove(0);
    }

    public boolean find(ProhibitedMove prohibitedMove) {
        if (tabu.isEmpty()) return false;
        for (ProhibitedMove item : tabu) {
            if (item.getPosition() == prohibitedMove.getPosition() && item.getSecondPosition() == prohibitedMove.getSecondPosition()) {
                //System.out.println(String.valueOf(item.getPosition()) + " " + String.valueOf(prohibitedMove.getPosition()) );
                return true;
            } else if (item.getPosition() == prohibitedMove.getSecondPosition() && item.getSecondPosition() == prohibitedMove.getPosition()) {
                //System.out.println(String.valueOf(item.getPosition()) + " " + String.valueOf(prohibitedMove.getPosition()) );
                return true;
            }
        }
        return false;
    }


    public ProhibitedMove generatePohibitedMoves(int size) {
        return swap(size);
    }

    public List<Job> generateNeighborhood(List<Job> jobs, ProhibitedMove prohibitedMove) {
        Collections.swap(jobs,prohibitedMove.getPosition(),prohibitedMove.getSecondPosition());
        return jobs;
    }

    private ProhibitedMove swap(int size) {
        Random random = new Random();
        int idx1 = random.nextInt(size-1);
        int idx2 = random.nextInt(size-1);
        while (idx1 == idx2) {
            idx1 = random.nextInt(size-1);
            idx2 = random.nextInt(size-1);
        }
        if(idx1>idx2) {
            int temp = idx1;
            idx1 = idx2;
            idx2 = temp;
        }
        //System.out.println("x " + String.valueOf(idx1) + " " + "x " + String.valueOf(idx2));
        return new ProhibitedMove(idx1,idx2);
    }
}
