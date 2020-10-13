package com.karkia.balancer.utils;

import com.karkia.balancer.entities.TransferEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.karkia.balancer.Constants.TRANSFER_DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
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
    @DisplayName("buildTransferEntity() is setup correctly and returns expected value")
    void buildTransferEntity_test1() {
        assertNotNull(TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR));
    }

    @Test
    @DisplayName("buildTransferEntity() should fail on empty argument string")
    void buildTransferEntity_test2() {
        Assertions.assertThrows(AssertionError.class, () -> {
            TransferEntityBuilder.buildTransferEntity("");
        });
    }

    @Test
    @DisplayName("buildTransferEntity() should work with valid argument string")
    void buildTransferEntity_test3() {
        final var actualEntity = TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR);
        assertEquals(SAMPLE_TRANSFER_ENTITY, actualEntity);
    }
}