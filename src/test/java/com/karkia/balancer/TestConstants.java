package com.karkia.balancer;

import com.karkia.balancer.entities.TransferEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.karkia.balancer.Constants.TRANSFER_DATE_FORMAT;

/**
 * @author Ashish Karki
 * Constants fields used in tests
 */
public final class TestConstants {
    /**
     * A sample Non-Starting (after beginning transfers) Transfer string
     */
    public static final String NON_STARTING_TRANSFER_STR = "112233, 223344, 11.11, 11/08/2055, 1448";
    /**
     * A sample TransferEntity representing one Transfer row/item
     */
    public static final TransferEntity SAMPLE_TRANSFER_ENTITY = TransferEntity.builder()
            .srcAccount("112233")
            .destAccount("223344")
            .amount(11.11)
            .transferDate(LocalDate.parse("11/08/2055", TRANSFER_DATE_FORMAT))
            .transferId(1448L)
            .build();

    /**
     * A sample TransferEntity representing one Transfer row/item
     */
    public static final TransferEntity SAMPLE_START_TRANSFER_ENTITY1 = TransferEntity.builder()
            .srcAccount("0")
            .destAccount("112233")
            .amount(60.00)
            .transferDate(LocalDate.parse("10/08/2055", TRANSFER_DATE_FORMAT))
            .transferId(1445L)
            .build();

    /**
     * A sample TransferEntity representing one Transfer row/item
     */
    public static final TransferEntity SAMPLE_START_TRANSFER_ENTITY2 = TransferEntity.builder()
            .srcAccount("0")
            .destAccount("223344")
            .amount(25.03)
            .transferDate(LocalDate.parse("10/08/2055", TRANSFER_DATE_FORMAT))
            .transferId(1446L)
            .build();
    /**
     * Sample list of Transfer entities
     */
    public static final List<TransferEntity> SAMPLE_TRANSFER_ENTITIES = List.of(
            SAMPLE_START_TRANSFER_ENTITY1,
            SAMPLE_START_TRANSFER_ENTITY2,
            SAMPLE_TRANSFER_ENTITY
    );

    /**
     * sample account to balance map
     */
    public static final Map<String, Double> SAMPLE_ACC_TO_BALANCE_MAP = Map.of("223344", 36.14, "112233", 48.89);

    /**
     * Sample account to freq map
     */
    public static final Map<String, Double> SAMPLE_ACC_TO_FREQ_MAP = Map.of("112233", 1.0);

    private TestConstants() {
    }

    /**
     * @return a sample final balance information string
     */
    public static final String buildSampleBalanceStr() {
        StringBuilder balancesBuilder = new StringBuilder();

        balancesBuilder.append("#Balances").append(Constants.NEWLINE_STRING);
        balancesBuilder.append("112233").append(" - ")
                .append(Constants.TWO_PLACE_DECIMAL.format(48.89))
                .append(Constants.NEWLINE_STRING);
        balancesBuilder.append("223344").append(" - ")
                .append(Constants.TWO_PLACE_DECIMAL.format(36.14))
                .append(Constants.NEWLINE_STRING);

        balancesBuilder.append("#Bank Account with highest balance").append(Constants.NEWLINE_STRING);
        balancesBuilder.append("112233").append(Constants.NEWLINE_STRING);

        balancesBuilder.append("#Frequently used source bank account").append(Constants.NEWLINE_STRING);
        balancesBuilder.append("112233");

        return balancesBuilder.toString();
    }
}
