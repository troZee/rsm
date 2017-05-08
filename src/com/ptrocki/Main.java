package com.ptrocki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    List<Job> jobs;

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
            fileName = "wt40.txt";
            jobs = reader.readFrom(System.getProperty("user.dir")+ "/" + fileName, 40);
            System.out.print("Genetic Algo");
            GeneticAlgoritm algoritm = new GeneticAlgoritm(jobs);
            algoritm.compute(100000);
            algoritm.printSolution();
            System.out.println("");
            System.out.println("Time execution: " + convert(algoritm.getTimeExecution().toNanos()));
            //System.out.println("Time execution: " + algoritm.getTimeExecution().toNanos());
            System.out.println("Tabu Algo");
            TabuSearchAlgorithm tabuSearchAlgorithm = new TabuSearchAlgorithm(jobs);
            tabuSearchAlgorithm.compute(100000,100,10);
            tabuSearchAlgorithm.printSolution();
            System.out.println("");
            System.out.println("Time execution: " + convert(tabuSearchAlgorithm.getTimeExecution().toNanos()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String convert(Long nanosecond) {
        String temp = Long.toString(nanosecond);
        return (temp.substring(0,temp.length() - 9) + "." + temp.substring(temp.length() - 9)).substring(0,6);
    }
}
