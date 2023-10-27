package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WorkWithFile {
    private static final String SUPPLY = "supply";
    private static final String BUY = "buy";
    private static final String RESULT = "result";

    public void getStatistic(String fromFileName, String toFileName) {
        String toFile = readFromFile(fromFileName);
        String report = createReport(toFile);
        writeToFile(toFileName, report);
    }

    private String readFromFile(String fileName) {
        File file = new File(fileName);
        try {
            return Files.readAllLines(file.toPath()).toString();
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + fileName, e);
        }
    }

    private String createReport(String strings) {
        String[] strings1 = strings.split("\\W+");
        int supply = 0;
        int buy = 0;
        int result;
        for (int i = 1; i < strings1.length; i++) {
            if (strings1[i].equals(SUPPLY)) {
                supply += Integer.parseInt(strings1[i + 1]);
            } else if (strings1[i].equals(BUY)) {
                buy += Integer.parseInt(strings1[i + 1]);
            }
        }
        result = supply - buy;
        StringBuilder builder = new StringBuilder();
        builder.append(SUPPLY).append(",").append(supply).append(System.lineSeparator())
                .append(BUY).append(",").append(buy).append(System.lineSeparator())
                .append(RESULT).append(",").append(result);
        return builder.toString();
    }

    private void writeToFile(String fileName, String report) {
        File file = new File(fileName);
        try {
            Files.write(file.toPath(), report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file: " + fileName, e);
        }
    }
}
