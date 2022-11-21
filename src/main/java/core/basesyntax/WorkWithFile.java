package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String COMA_REGEX = ",";
    private static final String STRING_BUY = "buy";
    private static final String STRING_SUPPLY = "supply";
    private static final String STRING_RESULT = "result";
    private static final int BUY_SUPPLY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private int supplySum;
    private int buySum;

    public void getStatistic(String fromFileName, String toFileName) {
        readFromFile(fromFileName);
        String report = createReport();
        writeIntoNewFile(report, toFileName);
    }

    private void readFromFile(String fromFileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fromFileName));
            String value = reader.readLine();
            while (value != null) {
                fillUpdatedInfo(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file!", e);
        }
    }

    private void fillUpdatedInfo(String value) {
        String[] temporaryValue = value.split(COMA_REGEX);
        if (temporaryValue[BUY_SUPPLY_INDEX].equals(STRING_BUY)) {
            buySum += Integer.parseInt(temporaryValue[VALUE_INDEX]);
        } else {
            supplySum += Integer.parseInt(temporaryValue[VALUE_INDEX]);
        }
    }

    public String createReport() {
        return STRING_SUPPLY + COMA_REGEX + supplySum
                + System.lineSeparator() + STRING_BUY + COMA_REGEX + buySum
                + System.lineSeparator() + STRING_RESULT + COMA_REGEX + (supplySum - buySum);
    }

    private void writeIntoNewFile(String report, String toFileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName, true));
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file!", e);
        }
    }
}
