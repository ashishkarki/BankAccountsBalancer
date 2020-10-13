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
public class TransferEntity {
    /**
     * account FROM which money is being sent
     */
    private String srcAccount;

    /**
     * account TO which money is being sent
     */
    private String destAccount;

    /**
     * Amount of transfer
     */
    private double amount;

    /**
     * Date of transfer
     */
    private LocalDate transferDate;

    /**
     * ID assigned to this transfer
     */
    private long transferId;
}
