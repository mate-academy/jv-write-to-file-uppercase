package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class WorkWithFile {
    private static final int NUMBER_OF_LINE = 3;

    public void getStatistic(String fromFileName, String toFileName) {
        String fileString = readData(fromFileName);
        String data = createReport(fileString);
        writeData(toFileName, data);
    }

    private String readData(String fromFileName) {
        File file = new File(fromFileName);
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getName()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file! " + fromFileName, e);
        }
    }

    private String createReport(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] arrayString = data.split(System.lineSeparator());
        int[] arrayNumber = calculateData(arrayString);
        stringBuilder.append("supply,").append(arrayNumber[0]).append(System.lineSeparator())
                        .append("buy,").append(arrayNumber[1]).append(System.lineSeparator())
                        .append("result,").append(arrayNumber[2]);
        return stringBuilder.toString();
    }

    private int[] calculateData(String[] arrayString) {
        int[] arrayResult = new int[NUMBER_OF_LINE];
        int sumSupply = 0;
        int sumBuy = 0;
        int result = 0;
        for (String array : arrayString) {
            String[] arrayStringLine = array.split(",");
            if (arrayStringLine[0].contains("supply")) {
                sumSupply += Integer.parseInt(arrayStringLine[1]);
            } else {
                sumBuy += Integer.parseInt(arrayStringLine[1]);
            }
        }
        result = sumSupply - sumBuy;
        arrayResult[0] = sumSupply;
        arrayResult[1] = sumBuy;
        arrayResult[2] = result;
        return arrayResult;
    }

    private void writeData(String toFileName, String data) {
        File file = new File(toFileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            if (data != "") {
                bufferedWriter.write(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file!", e);
        }
    }
}
