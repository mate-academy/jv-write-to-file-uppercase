package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorkWithFile {
    public void getStatistic(String fromFileName, String toFileName) {
        File fileFromFileName = new File(fromFileName);
        File fileToFileName = new File(toFileName);
        Map<String, Integer> result = new HashMap<>();

        if (!fileFromFileName.exists()) {
            return;
        }

        if (!fileToFileName.exists()) {
            File newFile = new File("result.txt");
            try {
                newFile.createNewFile();
                toFileName = newFile.getName();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        readFromFileAndPutDataToMap(fromFileName, result);

        writeToFileDataFromMap(toFileName, result);
    }

    private void writeToFileDataFromMap(String toFileName, Map<String, Integer> result) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                bufferedWriter.write(stringBuilder
                        .append(entry.getKey())
                        .append(",")
                        .append(entry.getValue())
                        .append(System.lineSeparator()).toString());
                bufferedWriter.newLine();
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Can't write data to the file " + toFileName, ioException);
        }
    }

    private void readFromFileAndPutDataToMap(String fromFileName, Map<String, Integer> result) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFileName))) {
            String lineData;

            while ((lineData = bufferedReader.readLine()) != null) {
                String[] wordsAndNumber = lineData.split(",");
                if (wordsAndNumber.length == 2) {
                    String word = wordsAndNumber[0];
                    int number = Integer.parseInt(wordsAndNumber[1]);
                    if (result.containsKey(word)) {
                        int numberInMap = result.get(word);
                        result.put(word, numberInMap + number);
                    } else {
                        result.put(word, number);
                    }
                }
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Can't read data from the file "
                    + fromFileName, ioException);
        }
    }
}
