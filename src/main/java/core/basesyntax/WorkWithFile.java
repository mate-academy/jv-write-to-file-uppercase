package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final int TRANSACTION_POSITION = 0;
    private static final int MONEY_AMOUNT_POSITION = 1;
    private static final String SUPPLY = "supply";
    private static final String BUY = "buy";

    public void getStatistic(String fromFileName, String toFileName) {
        int supplyAmount = 0;
        int buyAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] transactionType = line.split(",");
                switch (transactionType[TRANSACTION_POSITION]) {
                    case SUPPLY:
                        supplyAmount += Integer.parseInt(transactionType[MONEY_AMOUNT_POSITION]);
                        break;
                    case BUY:
                        buyAmount += Integer.parseInt(transactionType[MONEY_AMOUNT_POSITION]);
                        break;
                    default:
                        System.out.println("Invalid transaction type");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from " + fromFileName + e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName, true))) {
            writer.write(SUPPLY + "," + supplyAmount + System.lineSeparator()
                    + BUY + "," + buyAmount + System.lineSeparator()
                    + "result" + "," + (supplyAmount - buyAmount));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + toFileName + e);
        }
    }
}

