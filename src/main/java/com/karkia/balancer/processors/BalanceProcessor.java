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
 * Processes balances in following manner:
 * <li>1. Print the final balances on all bank accounts</li>
 * <li>2. Print the bank account with the highest balance</li>
 * <li>3. Print the most frequently used source bank account</li>
 */
@Log4j2
public class BalanceProcessor {

    /**
     * @param transferEntities List of BalanceTransfer entities
     * @return a Pair with accToBalanceMap and accToFreqMap
     */
    public Pair<Map<String, Double>, Map<String, Double>> processTransfers(
            final List<BalanceTransferEntity> transferEntities
    ) {
        // exception: A source Bank account with ID 0 does not have a balance
        // 0, 112233, 60.00, 10/08/2055, 1445
        // 112233, 223344, 11.11, 11/08/2055, 1448

        final Map<Boolean, List<BalanceTransferEntity>> startingNonStartingBalances =
                getStartingNonStartingMaps(transferEntities);

        // Determine the opening balances on all available accounts
        // These are the ones where sourceAccount == 0 i.e. the starting balance transfers
        final Map<String, Double> accToBalanceMap = new HashMap<>(getStartingBalances(startingNonStartingBalances));

        // DEBUG STATEMENT
        // accToBalanceMap.forEach((acc, bal) -> log.info("acc: {}, Starting balance: {}", acc, bal));

        // Go through the motion of calculating final balances
        final Map<String, Double> accToFreqMap = startingNonStartingBalances.get(false)
                .stream().map(entity -> buildAccountMaps(accToBalanceMap, entity))
                .collect(Collectors.toList())
                .get(0); // TODO find better way to collect/terminate value from buildAccountMaps

        // Reference: https://www.baeldung.com/java-tuples
        return Pair.with(accToBalanceMap, accToFreqMap);
    }

    private Map<String, Double> getStartingBalances(
            final Map<Boolean, List<BalanceTransferEntity>> startingNonStartingBalances) {
        final Map<String, Double> accToBalanceMap = new HashMap<>();
        startingNonStartingBalances.get(true)
                .forEach(entity -> accToBalanceMap.put(
                        entity.getDestAccount(),
                        entity.getAmount()));

        return accToBalanceMap;
    }

    /**
     * @param accToBalanceMap AccNumber to its Balance Map
     * @param accToFreqMap    AccNumber to its Freq. as a Source Map
     * @return string representing formatting output
     */
    public String balancesPrinter(final Map<String, Double> accToBalanceMap, final Map<String, Double> accToFreqMap) {
        log.info("*************** RESULTANT PRINTS BELOW *****************");

        final var balancesBuilder = new StringBuilder();
        balancesBuilder.append("#Balances").append(Constants.NEWLINE_STRING);

        accToBalanceMap.forEach((acc, balance) ->
                balancesBuilder.append(acc).append(" - ")
                        .append(Constants.TWO_PLACE_DECIMAL.format(balance))
                        .append(Constants.NEWLINE_STRING));

        // Find account with highest balance after all the transfers
        // reference: https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map/11256352
        final String accWithHighestBalance = getKeyForMaxValue(accToBalanceMap);
        balancesBuilder.append("#Bank Account with highest balance").append(Constants.NEWLINE_STRING);
        balancesBuilder.append(accWithHighestBalance).append(Constants.NEWLINE_STRING);

        final String accWithMaxSrcFreq = getKeyForMaxValue(accToFreqMap);
        balancesBuilder.append("#Frequently used source bank account").append(Constants.NEWLINE_STRING);
        balancesBuilder.append(accWithMaxSrcFreq);

        return balancesBuilder.toString();
    }

    private Map<String, Double> buildAccountMaps(
            final Map<String, Double> accToBalanceMap,
            final BalanceTransferEntity entity) {
        assert accToBalanceMap.containsKey(entity.getSrcAccount());
        assert accToBalanceMap.containsKey(entity.getDestAccount());

        // Deduct this amount from source account
        final double newSrcAmount = accToBalanceMap.get(entity.getSrcAccount()) - entity.getAmount();
        accToBalanceMap.replace(
                entity.getSrcAccount(),
                newSrcAmount);

        // if source account doesn't exist, put 1 as value, otherwise add 1 to the current value
        final Map<String, Double> localAccToFreqMap = new HashMap<>();
        localAccToFreqMap.merge(entity.getSrcAccount(), 1.0, Double::sum);

        // Add this amount to the destination account
        final double newDestAmount = accToBalanceMap.get(entity.getDestAccount()) + entity.getAmount();
        accToBalanceMap.replace(
                entity.getDestAccount(),
                newDestAmount);

        return localAccToFreqMap;
    }

    private Map<Boolean, List<BalanceTransferEntity>> getStartingNonStartingMaps(final List<BalanceTransferEntity> transferEntities) {
        // Reference: https://www.baeldung.com/java-list-split
        return transferEntities.stream().collect(
                Collectors.partitioningBy(transferEntity ->
                        transferEntity.getSrcAccount().equalsIgnoreCase("0")));
    }

    private String getKeyForMaxValue(final Map<String, Double> accToSomeValueMap) {
        return Collections.max(
                accToSomeValueMap.entrySet(),
                Map.Entry.comparingByValue())
                .getKey();
    }
}
