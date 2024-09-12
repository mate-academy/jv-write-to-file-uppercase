package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int SUPPLY_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final String SUPPLY = "supply";
    private static final String BUY = "buy";
    private static final String COMMA = ",";
    private static final String RESULT = "result";

    public void getStatistic(String fromFileName, String toFileName) {
        int[] reportData = readFromFile(fromFileName);
        String report = calculateReport(reportData[SUPPLY_INDEX], reportData[BUY_INDEX]);
        writeIntoFile(toFileName, report);
    }

    private int[] readFromFile(String fromFileName) {
        int supplySum = 0;
        int buySum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(COMMA);
                String operationType = details[0];
                int amount = Integer.parseInt(details[1]);

                if (operationType.equals(SUPPLY)) {
                    supplySum += amount;
                } else if (operationType.equals(BUY)) {
                    buySum += amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + fromFileName, e);
        }

        return new int[]{supplySum, buySum};
    }

    private String calculateReport(int supplySum, int buySum) {
        int result = supplySum - buySum;

        return SUPPLY + COMMA + supplySum + System.lineSeparator()
                + BUY + COMMA + buySum + System.lineSeparator()
                + RESULT + COMMA + result;
    }

    private void writeIntoFile(String toFileName, String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing file " + toFileName, e);
        }
    }
}
