package com.ptrocki;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    //TODO add for all instance
    List<Instance> readFrom(String filePath, int numberOfJobsInInstance, int numberOfInstance) throws IOException {
        Path path = Paths.get(filePath);
        List<Instance> instances = new ArrayList<>();
        Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
        ArrayList<String> lines = new ArrayList<>();
        stream.map(s -> new ArrayList<>(Arrays.asList(s.split("\\s+")))).forEach(lines::addAll);
        List<Integer> filteredLines = lines.stream().filter(s -> !s.equals("")).map(Integer::parseInt).collect(Collectors.toList());
        //HashMap<Integer, List<Integer>> matrix = new HashMap<>();
        instances.add(new Instance(convertToJobs(filteredLines.subList(0,3*numberOfJobsInInstance),numberOfJobsInInstance)));
        for (int i = 3; i < numberOfInstance;  i=i+3) {
            //matrix.put(i,filteredLines.subList(i*numberOfJobsInInstance,(i+1)*numberOfJobsInInstance));
            instances.add(new Instance(convertToJobs(filteredLines.subList(i*numberOfJobsInInstance,(i+3)*numberOfJobsInInstance),numberOfJobsInInstance)));
        }
        return instances;
    }

    List<Job> convertToJobs(List<Integer> filteredLines, int numberOfJobsInInstance) {
        List<Integer> processingTime = filteredLines.subList(0,numberOfJobsInInstance);
        List<Integer> weights = filteredLines.subList(numberOfJobsInInstance,2*numberOfJobsInInstance);
        List<Integer> dueDate = filteredLines.subList(2*numberOfJobsInInstance,3*numberOfJobsInInstance);
        ArrayList<Job> jobs = new ArrayList<>();
        for (int index = 0; index < numberOfJobsInInstance; index++) {
            jobs.add(new Job(index,processingTime.get(index),
                    weights.get(index),
                    dueDate.get(index)));
        }
        return jobs;
    }
}
