package com.karkia.balancer.utils;

import com.karkia.balancer.entities.TransferEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.karkia.balancer.Constants.TRANSFER_DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransferEntityBuilderTest {
    final String NON_STARTING_TRANSFER_STR = "112233, 223344, 11.11, 11/08/2055, 1448";
    final TransferEntity SAMPLE_TRANSFER_ENTITY = TransferEntity.builder()
            .srcAccount("112233")
            .destAccount("223344")
            .amount(11.11)
            .transferDate(LocalDate.parse("11/08/2055", TRANSFER_DATE_FORMAT))
            .transferId(1448L)
            .build();

    @Test
    void buildTransferEntity_is_setup_correctly() {
        assertNotNull(TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR));
    }

    @Test
    void buildTransferEntity_should_fail_on_empty_transferString() {
        Assertions.assertThrows(AssertionError.class, () -> {
            TransferEntityBuilder.buildTransferEntity("");
        });
    }

    @Test
    void buildTransferEntity_should_work_with_proper_transferString() {
        final var actualEntity = TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR);
        assertEquals(SAMPLE_TRANSFER_ENTITY, actualEntity);
    }
}