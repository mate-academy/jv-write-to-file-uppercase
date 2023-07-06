package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFile {
    private static final String SUPPLYING_DATA_FIELD = "supply";
    private static final String BUYING_DATA_FIELD = "buy";
    private static final int INDEX_OF_DATA_FIELD = 0;
    private static final int INDEX_OF_AMOUNT_FIELD = 1;

    public void getStatistic(String fromFileName, String toFileName) {
        File dataFile = new File(fromFileName);
        List<String> dataList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                dataList.add(line);
                line = bufferedReader.readLine();
            }
            String report = createReport(dataList.stream().toArray(String[]::new));
            writeReportToFile(report, toFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find a file!", e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file!", e);
        }
    }

    private String createReport(String[] dataFields) {
        StringBuilder report = new StringBuilder();
        int amountOfSupplying = 0;
        int amountOfBuying = 0;
        for (String dataField : dataFields) {
            String[] info = dataField.split(",");;
            if (info[INDEX_OF_DATA_FIELD].equals(SUPPLYING_DATA_FIELD)) {
                amountOfSupplying += Integer.parseInt(info[INDEX_OF_AMOUNT_FIELD]);
            } else {
                amountOfBuying += Integer.parseInt(info[INDEX_OF_AMOUNT_FIELD]);
            }
        }
        report.append(SUPPLYING_DATA_FIELD).append(",")
                                            .append(amountOfSupplying)
                                            .append(System.lineSeparator());
        report.append(BUYING_DATA_FIELD).append(",")
                                            .append(amountOfBuying)
                                            .append(System.lineSeparator());
        report.append("result").append(",")
                                .append(amountOfSupplying - amountOfBuying)
                                .append(System.lineSeparator());
        return report.toString();
    }

    private void writeReportToFile(String report, String toFileName) {
        File destinationFile = new File(toFileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destinationFile))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to a file!", e);
        }
    }
}
