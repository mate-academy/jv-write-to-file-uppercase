package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String COMMA = ",";

    public void getStatistic(String fromFileName, String toFileName) {
        String report = readFile(fromFileName);
        String result = getResult(report);
        writeToFile(result, toFileName);
    }

    private String readFile(String fromFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file:" + fromFileName, e);
        }
        return stringBuilder.toString();
    }

    private String getResult(String report) {
        int countSupply = 0;
        int countBuy = 0;

        String[] lines = report.split("\n");
        for (String line : lines) {
            String[] parts = line.split(COMMA);
            if (parts.length == 2) {
                String operation = parts[0].trim();
                int amount = Integer.parseInt(parts[1].trim());
                if (operation.equals("supply")) {
                    countSupply += amount;
                }
                if (operation.equals("buy")) {
                    countBuy += amount;
                }
            }
        }

        int result = countSupply - countBuy;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" supply").append(COMMA).append(countSupply)
                .append(System.lineSeparator())
                .append("buy").append(COMMA).append(countBuy).append(System.lineSeparator())
                .append("result").append(COMMA).append(result).append(System.lineSeparator());
        return stringBuilder.toString();
    }

    private void writeToFile(String result, String toFileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(result);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file:" + toFileName, e);
        }
    }
}
