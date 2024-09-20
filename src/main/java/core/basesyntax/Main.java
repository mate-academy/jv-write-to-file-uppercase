package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        String[] inputFiles = {"apple.csv", "banana.csv", "grape.csv", "orange.csv"};
        String[] outputFiles = {"newFile1.txt", "newFile2.txt", "newFile3.txt", "newFile4.txt"};
        WorkWithFile workWithFile = new WorkWithFile();
        String[] results = new String[inputFiles.length];

        for (int i = 0; i < inputFiles.length; i++) {
            String fromFile = inputFiles[i];
            String newFile = outputFiles[i];
            workWithFile.writeStatistic(fromFile, newFile);
            results[i] = workWithFile.getStatistic(fromFile, newFile);
            System.out.println(results[i]);
        }
    }
}
