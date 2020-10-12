package com.karkia.balancer.processors;

import com.karkia.balancer.Constants;
import com.karkia.balancer.entities.BalanceTransferEntity;
import lombok.extern.log4j.Log4j2;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ashish Karki
 * Processes balances...
 * <li>1. Print the final balances on all bank accounts</li>
 * * 	    <li>2. Print the bank account with the highest balance</li>
 * * 	    <li>3. Print the most frequently used source bank account</li>
 */
@Log4j2
public class BalanceProcessor {

    /**
     * @param transferEntities List of BalanceTransfer entities
     * @return a Pair with accToBalanceMap and accToFreqMap
     */
    public Pair<Map<String, Double>, Map<String, Integer>> processorTransfers(
            final List<BalanceTransferEntity> transferEntities
    ) {
        // exception: A source Bank account with ID 0 does not have a balance
        // 0, 112233, 60.00, 10/08/2055, 1445
        // 112233, 223344, 11.11, 11/08/2055, 1448

        final Map<String, Double> accToBalanceMap = new HashMap<>();

        // Reference: https://www.baeldung.com/java-list-split
        final Map<Boolean, List<BalanceTransferEntity>> startingNonStartingBalances =
                transferEntities.stream().collect(
                        Collectors.partitioningBy(transferEntity ->
                                transferEntity.getSourceAccount().equalsIgnoreCase("0")));

        // Determine the opening balances on all available accounts
        // These are the ones where sourceAccount == 0 i.e. the starting balance transfers
        startingNonStartingBalances.get(true)
                .forEach(entity -> accToBalanceMap.put(
                        entity.getDestinationAccount(),
                        entity.getAmount()));

        // DEBUG STATEMENT
        accToBalanceMap.forEach((acc, bal) ->
                log.info("acc: {}, Starting balance: {}", acc, bal));

        // Go through the motion of calculating final balances
        final Map<String, Integer> accToFreqMap = new HashMap<>();
        startingNonStartingBalances.get(false)
                .forEach(entity -> {
                    assert accToBalanceMap.containsKey(entity.getSourceAccount());
                    assert accToBalanceMap.containsKey(entity.getDestinationAccount());

                    // Deduct this amount from source account
                    final double newSrcAmount = accToBalanceMap.get(entity.getSourceAccount()) - entity.getAmount();
                    accToBalanceMap.replace(
                            entity.getSourceAccount(),
                            newSrcAmount);

                    // if source account doesn't exist, put 1 as value, otherwise add 1 to the current value
                    accToFreqMap.merge(entity.getSourceAccount(), 1, Integer::sum);

                    // Add this amount to the destination account
                    final double newDestAmount = accToBalanceMap.get(entity.getDestinationAccount()) + entity.getAmount();
                    accToBalanceMap.replace(
                            entity.getDestinationAccount(),
                            newDestAmount);
                });

        // Reference: https://www.baeldung.com/java-tuples
        Pair<Map<String, Double>, Map<String, Integer>> mapPair = Pair.with(accToBalanceMap, accToFreqMap);

        return mapPair;
    }

    public String balancesPrinter(final Map<String, Double> accToBalanceMap, final Map<String, Integer> accToFreqMap) {
        // PRINT STATEMENTS
        System.out.println("*************** RESULTANT PRINTS BELOW *****************");

        final var balancesBuilder = new StringBuilder();
        // System.out.println("#Balances");
        balancesBuilder.append("#Balances\n");

        accToBalanceMap.forEach((acc, balance) ->
                balancesBuilder.append(acc + " - " + Constants.TWO_PLACE_DECIMAL.format(balance) + "\n"));

        // Find account with highest balance after all the transfers
        // reference: https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map/11256352
        final String accWithHighestBalance = Collections.max(
                accToBalanceMap.entrySet(),
                Map.Entry.comparingByValue())
                .getKey();
//        System.out.printf("#Bank Account with highest balance \n%s", accWithHighestBalance);
//        System.out.println();
        balancesBuilder.append("#Bank Account with highest balance \n");
        balancesBuilder.append(accWithHighestBalance + "\n");

        final String accWithMaxSrcFreq = Collections.max(
                accToFreqMap.entrySet(),
                Map.Entry.comparingByValue())
                .getKey();
//        System.out.printf("#Frequently used source bank account \n%s", accWithMaxSrcFreq);
//        System.out.println();
        balancesBuilder.append("#Frequently used source bank account \n");
        balancesBuilder.append(accWithMaxSrcFreq + "\n");

        return balancesBuilder.toString();
    }
}
