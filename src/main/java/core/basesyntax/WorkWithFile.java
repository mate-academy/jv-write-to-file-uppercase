package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMMA_SEPARATOR = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int OPERATION_AMOUNT_INDEX = 1;
    private static final String SUPPLY_NAME = "supply";
    private static final String BUY_NAME = "buy";
    private static final String RESULT_NAME = "result";
    private static final String COMMA_SYMBOL = ",";
    private static final String CAN_NOT_READ_FILE_INFO = "Can't read data from the file ";
    private static final String CAN_NOT_WRITE_FILE_INFO = "Can't write data from the file ";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void getStatistic(String fromFileName, String toFileName) {
        writeToFile(fromFileName, toFileName);
    }

    private String[] readFromFile(String fromFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            StringBuilder textBuilder = new StringBuilder();
            String text = reader.readLine();

            while (text != null) {
                textBuilder.append(text).append(SPACE_SEPARATOR);
                text = reader.readLine();
            }
            String[] textToStringArray
                    = textBuilder.toString().split(SPACE_SEPARATOR);
            return textToStringArray;
        } catch (IOException e) {
            throw new RuntimeException(CAN_NOT_READ_FILE_INFO + fromFileName, e);
        }
    }

    private String getData(String fromFileName) {
        int supply = 0;
        int buy = 0;

        for (int i = 0; i < readFromFile(fromFileName).length; i++) {
            String[] textSplitByComma = readFromFile(fromFileName)[i].split(COMMA_SEPARATOR);
            int amount = Integer.valueOf(textSplitByComma[OPERATION_AMOUNT_INDEX]);
            supply = textSplitByComma[OPERATION_TYPE_INDEX].equals(SUPPLY_NAME)
                    ? supply += amount : supply;
            buy = textSplitByComma[OPERATION_TYPE_INDEX].equals(BUY_NAME)
                    ? buy += amount : buy;
        }
        int result = supply - buy;
        StringBuilder dataBuilder = new StringBuilder()
                .append(SUPPLY_NAME).append(COMMA_SYMBOL).append(supply).append(LINE_SEPARATOR)
                .append(BUY_NAME).append(COMMA_SYMBOL).append(buy).append(LINE_SEPARATOR)
                .append(RESULT_NAME).append(COMMA_SYMBOL).append(result).append(LINE_SEPARATOR);
        return dataBuilder.toString();
    }

    private void writeToFile(String fromFileName, String toFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write(getData(fromFileName));
        } catch (IOException e) {
            throw new RuntimeException(CAN_NOT_WRITE_FILE_INFO + toFileName, e);
        }
    }
}
