package com.karkia.balancer;

import com.karkia.balancer.entities.BalanceTransferEntity;
import com.karkia.balancer.processors.BalanceProcessor;
import com.karkia.balancer.utils.TransferEntityBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author Ashish Karki
 * <p>
 * A Command-line application to parse and process a transfers file and provide the business requirements, namely:
 * 	<ul>
 * 	    <li>1. Print the final balances on all bank accounts</li>
 * 	    <li>2. Print the bank account with the highest balance</li>
 * 	    <li>3. Print the most frequently used source bank account</li>
 * 	</ul>
 * </p>
 */
@SpringBootApplication
@Log4j2
public class TransfersApplication implements CommandLineRunner {

    /**
     * @param args arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(TransfersApplication.class, args);
    }

    @Override
    public void run(final String... args) throws URISyntaxException, IOException {
        // Below is some sample code to get you started. Good luck :)
        final URL file = getClass().getClassLoader().getResource("transfers.txt");

        // read into Transfer POJOs
        assert null != file;
        final ArrayList<BalanceTransferEntity> transferEntities = new ArrayList<>();
        Files.readAllLines(Path.of(file.toURI()))
                .stream()
                .skip(Constants.FILE_ROW_SKIP_COUNT)
                .forEach(transferStr -> transferEntities.add(TransferEntityBuilder.buildTransferEntity(transferStr)));

        // DEBUG STATEMENT
        transferEntities.forEach(log::info);

        final BalanceProcessor balanceProcessor = new BalanceProcessor();
        final var mapPair = balanceProcessor.processorTransfers(transferEntities);
        final var balancesStr = balanceProcessor.balancesPrinter(mapPair.getValue0(), mapPair.getValue1());

        // FINAL PRINTING OF RESULTS
        System.out.print(balancesStr);
    }
}
