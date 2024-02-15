package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private final String[] headers = {"supply", "buy", "result"};

    public void getStatistic(String fromFileName, String toFileName) {
        int[] result = formatData(readFromFile(fromFileName));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder.append(headers[i])
                    .append(",")
                    .append(result[i])
                    .append(System.lineSeparator());
        }
        File file = new File(toFileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can`t create file " + toFileName, e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(builder.toString().trim());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Can`t write file " + toFileName, e);
        }
    }

    private String readFromFile(String fromFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fromFileName));
            int value = reader.read();
            while (value != -1) {
                builder.append((char) value);
                value = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file " + fromFileName, e);
        }
        return builder.toString();
    }

    private int[] formatData(String text) {
        int[] result = {0, 0, 0};
        String[] data = text.split("\\W+");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(headers[0])) {
                result[0] += Integer.parseInt(data[i + 1]);
            } else if (data[i].equals(headers[1])) {
                result[1] += Integer.parseInt(data[i + 1]);
            }
        }
        result[2] = result[0] - result[1];
        return result;
    }
}
