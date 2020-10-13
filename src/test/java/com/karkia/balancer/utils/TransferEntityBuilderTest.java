package com.karkia.balancer.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.karkia.balancer.TestConstants.NON_STARTING_TRANSFER_STR;
import static com.karkia.balancer.TestConstants.SAMPLE_TRANSFER_ENTITY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class TransferEntityBuilderTest {

    @Test
    @DisplayName("buildTransferEntity() is setup correctly and returns expected value")
    void buildTransferEntity_test1() {
        assertNotNull(TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR));
    }

    @Test
    @DisplayName("buildTransferEntity() should fail on empty argument string")
    void buildTransferEntity_test2() {
        Assertions.assertThrows(AssertionError.class, () -> TransferEntityBuilder.buildTransferEntity(""));
    }

    @Test
    @DisplayName("buildTransferEntity() should work with valid argument string")
    void buildTransferEntity_test3() {
        final var actualEntity = TransferEntityBuilder.buildTransferEntity(NON_STARTING_TRANSFER_STR);
        Assertions.assertEquals(SAMPLE_TRANSFER_ENTITY, actualEntity);
    }
}