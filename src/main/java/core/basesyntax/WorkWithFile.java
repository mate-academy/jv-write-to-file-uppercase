package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    public static void getStatistic(String fromFileName, String toFileName) {
        String supplyData = readFromFile(fromFileName);
        String[] resultData = getTotalSupplyReport(supplyData).split(" ");
        File file = new File(toFileName);

        for (String data : resultData) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                bufferedWriter.write(data);
            } catch (IOException e) {
                throw new RuntimeException("Can't write data to file: " + toFileName, e);
            }
        }
    }

    private static String readFromFile(String fileName) {
        File file = new File(fileName);
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + fileName, e);
        }
        return stringBuilder.toString();
    }

    private static String getTotalSupplyReport(String fromFileName) {
        int countBuy = 0;
        int countSupply = 0;

        String[] lines = fromFileName.split(System.lineSeparator());
        for (String line : lines) {
            if (line.contains("buy")) {
                countBuy += Integer.parseInt(line.replaceAll("[\\D]", ""));
            }
            if (line.contains("supply")) {
                countSupply += Integer.parseInt(line.replaceAll("[\\D]", ""));
            }
        }
        int result = countSupply - countBuy;

        return "supply," + countSupply + System.lineSeparator()
                + "buy," + countBuy + System.lineSeparator()
                + "result," + result;
    }
}
