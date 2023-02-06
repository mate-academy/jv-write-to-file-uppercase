package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMMA_SEPARATOR = ",";
    private static final int OPERATION_TYPE_NAME = 0;
    private static final int OPERATION_AMOUNT = 1;

    public void getStatistic(String fromFileName, String toFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write(getData(fromFileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't write data from the file " + toFileName, e);
        }
    }

    private String[] readFromFile(String fromFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            StringBuilder builder = new StringBuilder();
            String text = reader.readLine();

            while (text != null) {
                builder.append(text).append(SPACE_SEPARATOR);
                text = reader.readLine();
            }
            String[] textToStringArray
                    = builder.toString().split(SPACE_SEPARATOR);
            return textToStringArray;
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + fromFileName, e);
        }
    }

    private String getData(String fromFileName) {
        int supply = 0;
        int buy = 0;

        for (int i = 0; i < readFromFile(fromFileName).length; i++) {
            String[] textSplitByComma = readFromFile(fromFileName)[i].split(COMMA_SEPARATOR);
            int amount = Integer.valueOf(textSplitByComma[OPERATION_AMOUNT]);
            supply = textSplitByComma[OPERATION_TYPE_NAME].equals("supply")
                    ? supply += amount : supply;
            buy = textSplitByComma[OPERATION_TYPE_NAME].equals("buy")
                    ? buy += amount : buy;
        }
        int result = supply - buy;
        StringBuilder builder = new StringBuilder()
                .append("supply,").append(supply).append(System.lineSeparator())
                .append("buy,").append(buy).append(System.lineSeparator())
                .append("result,").append(result).append(System.lineSeparator());
        return builder.toString();
    }
}
