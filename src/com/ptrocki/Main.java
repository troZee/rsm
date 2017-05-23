package com.ptrocki;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Main {

    private List<Instance> instances;

    public static void main(String[] args) {
        new Main().start();
        //new Main().test();
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
            algoritm.compute(100000, 50, 50);
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

    void test() {

        FileReader reader = new FileReader();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("FileName: ");
        String fileName;
        final int noOfInstance;
        int instanceCount;
        int instanceTestNumer;
        int loops;
        int numberOfIteration;
        int pForCrossover = -1;
        int pForMutation = -1;
        int tabuSize = -1;
        try {
            System.out.println("Which one do you want to test ?: 0 gene, 1 tabu");
            Integer choice = Integer.valueOf(br.readLine());
            System.out.println("filename");
            fileName = br.readLine();
            noOfInstance = 125;
            System.out.println("instanceCount: ");
            instanceCount = Integer.valueOf(br.readLine());
            System.out.println("instanceTestNumer: ");
            instanceTestNumer = Integer.valueOf(br.readLine());
            System.out.println("How many times you want to measure : ");
            loops = Integer.valueOf(br.readLine());
            System.out.println("Number of iteration: ");
            numberOfIteration = Integer.valueOf(br.readLine());
            if (choice == 0) {
                System.out.println("p for crossover ");
                pForCrossover = Integer.valueOf(br.readLine());
                System.out.println("p for mutation ");
                pForMutation = Integer.valueOf(br.readLine());
            } else {
                System.out.println("Tabu size");
                tabuSize = Integer.valueOf(br.readLine());
            }

            instances = reader.readFrom(System.getProperty("user.dir") + "/" + fileName, instanceCount, noOfInstance);
            List<Job> jobs = instances.get(instanceTestNumer).getJobs();

            List<Long> times = new ArrayList<>();
            if (choice == 0) {
                times.clear();
                for (int i = 0; i < loops; i++) {
                    GeneticAlgoritm algoritm = new GeneticAlgoritm(jobs);
                    algoritm.compute(numberOfIteration, pForCrossover, pForMutation);
                    System.out.println("Time execution for Genetic: " + algoritm.getTimeExecution().toNanos());
                    times.add(algoritm.getTimeExecution().toNanos());
                }
                save(times, "instanceTestNumer" + String.valueOf(instanceTestNumer), "iteration" + String.valueOf(numberOfIteration), "pCrossover" + String.valueOf(pForCrossover), "pMutation" + String.valueOf(pForMutation));

            } else {
                times.clear();
                for (int i = 0; i < loops; i++) {
                    TabuSearchAlgorithm tabuSearchAlgorithm = new TabuSearchAlgorithm(jobs);
                    tabuSearchAlgorithm.compute(numberOfIteration, tabuSize);
                    System.out.println("Time execution for Tabu: " + tabuSearchAlgorithm.getTimeExecution().toNanos());
                    times.add(tabuSearchAlgorithm.getTimeExecution().toNanos());
                }
                save(times, "instanceTestNumer" + String.valueOf(instanceTestNumer), "iteration" + String.valueOf(numberOfIteration), "tabuSize" + String.valueOf(tabuSize));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void save(List<Long> results, String... fileNameParams) {
        String filename = "";
        for (String param : fileNameParams) {
            filename += param + "_";
        }
        filename += ".csv";
        try {
            FileWriter writer = new FileWriter(filename, false);
            for (Long result : results) {
                writer.write(String.valueOf(result) + "\n");
            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
