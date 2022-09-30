package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String RESULT = "result";
    private static final String SUPPLY_OPERATOR = "supply";
    private static final String BUY_OPERATOR = "buy";
    private static final String COMMA = ",";
    private static final int INDEX_OF_OPERATOR = 0;
    private static final int INDEX_OF_NUMBER = 1;

    public void getStatistic(String fromFileName, String toFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        int sumSupply = 0;
        int sumBuy = 0;
        int result;
        String[] dataFromFile = readFromFile(fromFileName);
        for (String lineInArr : dataFromFile) {
            String[] arrWithOperatorAndNumber = lineInArr.split(COMMA);
            switch (arrWithOperatorAndNumber[INDEX_OF_OPERATOR]) {
                case SUPPLY_OPERATOR:
                    sumSupply += Integer.parseInt(arrWithOperatorAndNumber[INDEX_OF_NUMBER]);
                    break;
                case BUY_OPERATOR:
                    sumBuy += Integer.parseInt(arrWithOperatorAndNumber[INDEX_OF_NUMBER]);
                    break;
                default:
                    break;
            }
        }
        result = sumSupply - sumBuy;
        stringBuilder.append(SUPPLY_OPERATOR).append(COMMA)
                .append(sumSupply).append(System.lineSeparator())
                .append(BUY_OPERATOR).append(COMMA)
                .append(sumBuy).append(System.lineSeparator())
                .append(RESULT).append(COMMA).append(result);
        writeToFile(toFileName, stringBuilder);
    }

    private String[] readFromFile(String fileName) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line).append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString().split(System.lineSeparator());
    }

    private void writeToFile(String fileName, StringBuilder stringBuilder) {
        String[] arrWithResult = stringBuilder.toString()
                .split(System.lineSeparator());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : arrWithResult) {
                bufferedWriter.write(line);
                bufferedWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
