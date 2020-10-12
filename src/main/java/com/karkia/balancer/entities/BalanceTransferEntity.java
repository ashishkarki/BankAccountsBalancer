package com.karkia.balancer.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Ashish Karki
 * Represents a single item of Transfer Entity
 */
@Data
@Builder
public class BalanceTransferEntity {
    /**
     * account from which money is being sent
     */
    private String sourceAccount;

    private String destinationAccount;

    private double amount;

    private LocalDate transferDate;

    private long transferId;
}
