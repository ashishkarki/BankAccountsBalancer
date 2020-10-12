package com.karkia.balancer.processors;

import com.karkia.balancer.entities.BalanceTransferEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ashish Karki
 * Processes balances...
 * <li>1. Print the final balances on all bank accounts</li>
 * * 	    <li>2. Print the bank account with the highest balance</li>
 * * 	    <li>3. Print the most frequently used source bank account</li>
 */
public class BalanceProcessor {
    List<Double> finalBalances = new ArrayList<>();
    String accWithHighestBalance;
    String mostFrequentSrcAcc;

    public void processorTransfers(List<BalanceTransferEntity> transferInfoEntities) {
        // exception: A source Bank account with ID 0 does not have a balance
        // 112233, 223344, 11.11, 11/08/2055, 1448

        Map<String, Double> accToBalanceMap = new HashMap<>();

        // determine the opening balances on all available accounts
//        transferInfoEntities.stream()
//                .filter(transferInfoEntity ->
//                        transferInfoEntity.getSourceAccount().equalsIgnoreCase("0")
//                ).forEach(transferInfoEntity -> {
//            accToBalanceMap
//        });
    }
}
