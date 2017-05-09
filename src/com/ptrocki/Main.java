package com.ptrocki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    private List<Instance> instances;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        FileReader reader = new FileReader();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("FileName: ");
        String fileName;
        try {
            //fileName = br.readLine();

            final int noOfInstance = 125; // don;t change it
            int instanceCount = 40;
            int instanceTestNumer = 0; //

            fileName = "wt" + String.valueOf(instanceCount) + ".txt";
            instances = reader.readFrom(System.getProperty("user.dir") + "/" + fileName, instanceCount, noOfInstance);
            List<Job> jobs = instances.get(instanceTestNumer).getJobs();
            System.out.print("Genetic Algo");
            GeneticAlgoritm algoritm = new GeneticAlgoritm(jobs);
            algoritm.compute(100000);
            algoritm.printSolution();
            System.out.println("");
            System.out.println("Time execution: " + convert(algoritm.getTimeExecution().toNanos()));
            //System.out.println("Time execution: " + algoritm.getTimeExecution().toNanos());
            System.out.println("Tabu Algo");
            TabuSearchAlgorithm tabuSearchAlgorithm = new TabuSearchAlgorithm(jobs);
            tabuSearchAlgorithm.compute(1000000, 5);
            tabuSearchAlgorithm.printSolution();
            System.out.println("");
            System.out.println("Time execution: " + convert(tabuSearchAlgorithm.getTimeExecution().toNanos()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String convert(Long nanosecond) {
        String temp = Long.toString(nanosecond);
        return (temp.substring(0, temp.length() - 9) + "." + temp.substring(temp.length() - 9)).substring(0, 6);
    }
}
