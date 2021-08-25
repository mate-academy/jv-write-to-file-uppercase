package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int SUPPLY_OR_BUY = 0;
    private static final int SUPPLY_OR_BUY_VALUE = 1;
    private static final String CSV_SEPARATOR = ",";

    public void getStatistic(String fromFileName, String toFileName) {
        String[] infoFromFile = readFile(fromFileName);
        String[] splitedLine;
        int supply = 0;
        int buyAmount = 0;
        for (String splitTextToLine : infoFromFile) {
            splitedLine = splitTextToLine.split(CSV_SEPARATOR);
            if ("supply".equals(splitedLine[SUPPLY_OR_BUY])) {
                supply += Integer.parseInt(splitedLine[SUPPLY_OR_BUY_VALUE]);
            } else {
                buyAmount += Integer.parseInt(splitedLine[SUPPLY_OR_BUY_VALUE]);
            }
        }
        int remain = supply - buyAmount;
        StringBuilder result = new StringBuilder();
        String statistic = result.append("supply,").append(supply).append(System.lineSeparator())
                .append("buy,").append(buyAmount).append(System.lineSeparator())
                .append("result,").append(remain).append(System.lineSeparator()).toString();
        writeInFile(toFileName, statistic);
    }

    public String[] readFile(String fromFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        File readFile = new File(fromFileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(readFile));
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value).append("-");
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file");
        }
        return stringBuilder.toString().split("-");
    }

    public void writeInFile(String toFileName, String statistic) {
        File writeFile = new File(toFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile))) {
            writeFile.createNewFile();
            writer.write(statistic);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file");
        }
    }
}
