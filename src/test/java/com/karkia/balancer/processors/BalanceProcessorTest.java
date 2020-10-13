package com.karkia.balancer.processors;

import com.karkia.balancer.TestConstants;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
class BalanceProcessorTest {

    // the System Under Test
    BalanceProcessor sut = null;


    @BeforeEach
    void setup() {
        sut = new BalanceProcessor();
    }

    @Test
    @DisplayName("processTransfers() should work as expected")
    void processTransfers_test1() {
        final Pair<Map<String, Double>, Map<String, Double>> actualTransferMaps = sut.
                processTransfers(TestConstants.SAMPLE_TRANSFER_ENTITIES);

        Assertions.assertNotNull(actualTransferMaps);
    }

    @Test
    @DisplayName("processTransfers() should return correct result")
    void processTransfers_test2() {
        final Pair<Map<String, Double>, Map<String, Double>> expectedTransferMaps = Pair.with(
                TestConstants.SAMPLE_ACC_TO_BALANCE_MAP,
                TestConstants.SAMPLE_ACC_TO_FREQ_MAP
        );

        final Pair<Map<String, Double>, Map<String, Double>> actualTransferMaps = sut.
                processTransfers(TestConstants.SAMPLE_TRANSFER_ENTITIES);

        Assertions.assertEquals(expectedTransferMaps, actualTransferMaps);
    }

    @Test
    @DisplayName("balancesPrinter()")
    void balancesPrinter() {
        final String expectedBalanceStr = TestConstants.buildSampleBalanceStr();
        final String actualBalanceStr = sut.balancesPrinter(
                TestConstants.SAMPLE_ACC_TO_BALANCE_MAP,
                TestConstants.SAMPLE_ACC_TO_FREQ_MAP
        );

        Assertions.assertEquals(expectedBalanceStr, actualBalanceStr);
    }
}