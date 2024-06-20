package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class WorkWithFile {
    private static final int OPERATION_PART = 0;
    private static final int VALUE_PART = 1;
    private static final int FIRST_VALUE = 0;

    public void getStatistic(String fromFileName, String toFileName) {
        File file = new File(fromFileName);
        int supply = FIRST_VALUE;
        int buy = FIRST_VALUE;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            List<String> strings = Files.readAllLines(file.toPath());

            for (String part : strings) {
                String[] parts = part.split(",");
                if (parts[OPERATION_PART].equals("supply")) {
                    supply += Integer.parseInt(parts[VALUE_PART]);
                } else {
                    buy += Integer.parseInt(parts[VALUE_PART]);
                }
            }
            int result = supply - buy;
            String report = "supply," + supply + System.lineSeparator()
                    + "buy," + buy + System.lineSeparator()
                    + "result," + result;
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + e);
        }
    }
}
