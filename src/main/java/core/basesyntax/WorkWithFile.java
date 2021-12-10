package core.basesyntax;

import java.io.*;

public class WorkWithFile {
    private int supplyResult;
    private int buyResult;
    private int result;

    public WorkWithFile(int supplyResult, int buyResult, int result) {
        this.supplyResult = supplyResult;
        this.buyResult = buyResult;
        this.result = result;
    }

    public WorkWithFile() {
    }

    public int getSupplyResult() {
        return supplyResult;
    }

    public int getBuyResult() {
        return buyResult;
    }

    public int getResult() {
        return result;
    }

    public StringBuilder readFile(String fromFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fromFileName));
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("can't read file", e);
        }
        return builder;
    }

    public WorkWithFile getResult(String fromFileName) {
        String[] resultArray = readFile(fromFileName).toString().split("\\W+");
        int supply = 0;
        int buy = 0;
        for (int i = 0; i < resultArray.length; i++) {
            if (resultArray[i].equals("supply")) {
                supply = supply + Integer.parseInt(resultArray[i + 1]);
            } else if (resultArray[i].equals("buy")) {
                buy = buy + Integer.parseInt(resultArray[i + 1]);
            }
        }
        return new WorkWithFile(supply, buy, supply - buy);
    }

    public void getStatistic(String fromFileName, String toFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName, true))) {
            writer.write("supply," +
                    String.valueOf(getResult(fromFileName).getSupplyResult()) +
                    System.lineSeparator());
            writer.write("buy," +
                    String.valueOf(getResult(fromFileName).getBuyResult()) +
                    System.lineSeparator());
            writer.write("result," +
                    String.valueOf(getResult(fromFileName).getResult()) +
                    System.lineSeparator() + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("can't write file", e);
        }
    }
}
