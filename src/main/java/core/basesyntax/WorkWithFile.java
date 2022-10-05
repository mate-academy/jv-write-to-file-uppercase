package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int AMMOUNT_INDEX = 1;
    private static final int BUY_INDEX = 0;
    private static final int SUPPLY_INDEX = 1;
    private static final String SEPARATOR = ",";

    public void getStatistic(String fromFileName, String toFileName) {
        writeToFile(toFileName, createReport(readFromFile(fromFileName).split(SEPARATOR)));
    }

    private String readFromFile(String fromFileName) {
        int buy = 0;
        int supply = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFileName))) {
            String value = bufferedReader.readLine();

            while (value != null) {
                String[] lineValue = value.split(SEPARATOR);
                switch (lineValue[OPERATION_TYPE_INDEX]) {
                    case "buy":
                        buy += Integer.parseInt(lineValue[AMMOUNT_INDEX]);
                        break;
                    case "supply":
                        supply += Integer.parseInt(lineValue[AMMOUNT_INDEX]);
                        break;
                    default:
                        break;
                }
                value = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't create a report for file: " + fromFileName, e);
        }

        return buy + SEPARATOR + supply;
    }

    private void writeToFile(String toFileName, String message) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + toFileName, e);
        }
    }

    private String createReport(String[] value) {
        return "supply," + value[SUPPLY_INDEX] + System.lineSeparator()
                + "buy," + value[BUY_INDEX] + System.lineSeparator()
                + "result,"
                + (Integer.parseInt(value[SUPPLY_INDEX]) - Integer.parseInt(value[BUY_INDEX]));
    }
}
