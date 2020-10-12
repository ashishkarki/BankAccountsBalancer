package com.karkia.balancer.utils;

import com.karkia.balancer.entities.BalanceTransferEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.karkia.balancer.Constants.TRANSFER_DATE_FORMAT;

/**
 * @author Ashish Karki
 * <p>
 * Builds a TransferEntity POJO - abstracts away the logic here
 */
public final class TransferEntityBuilder {

    private static final Pattern COMPILE = Pattern.compile(", ");

    private TransferEntityBuilder() {
    }

    /**
     * @param transferStr the string version of one row of balances transfer info.
     * @return BalanceTransferEntity
     */
    public static BalanceTransferEntity buildTransferEntity(final String transferStr) {
        final List<String> splitTransferCols = List.of(COMPILE.split(transferStr))
                .stream().map(String::trim)
                .collect(Collectors.toList());

        return BalanceTransferEntity.builder()
                .sourceAccount(splitTransferCols.get(0))
                .destinationAccount(splitTransferCols.get(1))
                .amount(Double.parseDouble(splitTransferCols.get(2)))
                .transferDate(LocalDate.parse(splitTransferCols.get(3), TRANSFER_DATE_FORMAT))
                .transferId(Long.parseLong(splitTransferCols.get(4)))
                .build();
    }
}
