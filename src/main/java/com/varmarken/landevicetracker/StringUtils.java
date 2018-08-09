package com.varmarken.landevicetracker;

import java.util.regex.Pattern;

/**
 * A set of utility functions for analyzing strings.
 *
 * @author Janus Varmarken {@literal <jvarmark@uci.edu>}
 */
public class StringUtils {

    /**
     * Regex pattern for searching strings for MACs in IEEE 802 standard format.
     * Courtesy of <a href="https://stackoverflow.com/a/4260512/1214974">StackOverflow.com user 'netcoder'</a>.
     */
    private static final Pattern MAC_REGEX_PATTERN = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");

    /**
     * Verifies if {@code macString} is a valid MAC address (in IEEE 802 standard format).
     * @param macString A string expected to contain a MAC address in IEEE 802 standard format.
     * @return {@code true} if the the entire string, {@code macString}, is a valid IEE 802 standard format MAC address,
     *         {@code false} otherwise.
     */
    public static boolean isMacAddress(String macString) {
        return macString != null && MAC_REGEX_PATTERN.matcher(macString).matches();
    }

}
