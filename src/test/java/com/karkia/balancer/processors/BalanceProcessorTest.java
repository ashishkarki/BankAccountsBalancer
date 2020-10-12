package com.karkia.balancer.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class BalanceProcessorTest {

    BalanceProcessor balanceProcessorTest;

    @BeforeEach
    void setup() {
        balanceProcessorTest = new BalanceProcessor();
    }

    @Test
    public final void someTest() {
        assertTrue(1 == 1);
    }
}