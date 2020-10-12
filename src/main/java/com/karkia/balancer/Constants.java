package com.karkia.balancer;

import java.time.format.DateTimeFormatter;

/**
 * @author Ashish Karki
 * Add always constant declarations here
 */
public final class Constants {
    /**
     * Our transfer date format is dd/MM/yyyy
     */
    public final static DateTimeFormatter TRANSFER_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * How many rows of text we want to skip while reading balances
     */
    public static final long FILE_ROW_SKIP_COUNT = 3L;
}
