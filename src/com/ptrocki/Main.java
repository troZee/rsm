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
            GeneticAlgoritm algoritm = new GeneticAlgoritm(jobs);
            algoritm.compute(40);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
