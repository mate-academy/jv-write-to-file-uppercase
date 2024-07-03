package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int INDEX_SYPPLY = 0;
    private static final int INDEX_BUY = 1;
    private static final String SUPPLY = "supply,";
    private static final String BUY = "buy,";
    private static final String RESULT = "result,";
    private static final String COMMA = ",";
    private static final String NEW_LINE = System.lineSeparator();

    public void getStatistic(String fromFileName, String toFileName) {
        String[] resultArray = readFile(fromFileName);
        String report = generateReport(resultArray);
        writeFile(toFileName, report);
    }

    private String[] readFile(String fromFileName) {
        int supply = 0;
        int buy = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(COMMA);
                int value = Integer.parseInt(parts[1]);
                if (parts[0].equals("supply")) {
                    supply += value;
                } else {
                    buy += value;
                }
                line = reader.readLine();
            }
            return new String[]
                    {String.valueOf(supply), String.valueOf(buy)};
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    private String generateReport(String [] resultArray) {
        int supply = Integer.parseInt(resultArray[INDEX_SYPPLY]);
        int buy = Integer.parseInt(resultArray[INDEX_BUY]);
        int result = supply - buy;
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(SUPPLY)
                .append(supply)
                .append(NEW_LINE)
                .append(BUY)
                .append(buy)
                .append(NEW_LINE)
                .append(RESULT)
                .append(result);
        return reportBuilder.toString();
    }

    private void writeFile(String toFileName, String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write("");
            writer.write(report);

        } catch (IOException e) {
            throw new RuntimeException("Can't write file", e);
        }
    }
}
