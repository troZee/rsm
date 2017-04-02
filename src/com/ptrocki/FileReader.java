package com.ptrocki;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    //TODO add for all instance
    List<Job> readFrom(String filePath, int numberOfJobsInInstance) throws IOException {
        Path path = Paths.get(filePath);
        Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
        ArrayList<String> lines = new ArrayList<>();
        stream.map(s -> new ArrayList<>(Arrays.asList(s.split("\\s+")))).forEach(lines::addAll);
        List<Integer> filteredLines = lines.stream().filter(s -> !s.equals("")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> processingTime = filteredLines.subList(0,numberOfJobsInInstance);
        List<Integer> weights = filteredLines.subList(numberOfJobsInInstance,2*numberOfJobsInInstance);
        List<Integer> dueDate = filteredLines.subList(2*numberOfJobsInInstance,3*numberOfJobsInInstance);
        ArrayList<Job> jobs = new ArrayList<>();
        for (int index = 0; index < numberOfJobsInInstance; index++) {
            jobs.add(new Job(processingTime.get(index),
                                weights.get(index),
                                dueDate.get(index)));
        }
        return jobs;
    }
}
