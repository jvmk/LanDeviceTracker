package com.varmarken.landevicetracker;

import com.google.common.net.InetAddresses;
import com.varmarken.landevicetracker.model.LanDevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Performs an ARP scan of the local network by invoking the {@code arp-scan} command line tool.
 *
 * @author Janus Varmarken {@literal <jvarmark@uci.edu>}
 */
public class ArpScanProcess {

    /**
     * Execute the {@code arp-scan} command to discover devices on the local network.
     * @return A list of discovered devices.
     * @throws IOException if an exception occurs when invoking the {@code arp-scan} command line tool or while reading
     *         its output.
     */
    public List<LanDevice> execute() throws IOException {
        List<LanDevice> devicesFound = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder();
        // TODO play around with retry count; there's a tradeoff between devices found and execution time.
        processBuilder.command("arp-scan", "--localnet", "--retry=10");
        Process arpscanProcess = processBuilder.start();
        // Read output of arp-scan...
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(arpscanProcess.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null) {
                LanDevice lanDevice = getLanDeviceFromOutput(line);
                if (lanDevice != null) {
                    devicesFound.add(lanDevice);
                    System.out.println("DISCOVERED: " + lanDevice.toString());
                }
            }
            System.out.println("arp-scan terminated gracefully");
        }
        return devicesFound;
    }

    /**
     * Parses an output line of {@code arp-scan}, extracting the discovered device.
     * @param arpScanOutputLine An output line from {@code arp-scan}.
     * @return A {@link LanDevice} modelling the discovered device, or {@code null} if the output line does not contain
     *         information about a discovered device.
     */
    private LanDevice getLanDeviceFromOutput(String arpScanOutputLine) {
        // Output format when a device is detected by arpscan is of format "IP\tMAC\tManufacturer" (i.e., tab separated)
        String[] items = arpScanOutputLine.split("\t");
        /*
         * arpscan also outputs other information that we choose to ignore here, so check the line to see if it
         * contains information about a device by checking if it is made up of three parts, the first of which is the
         * IP address of the device and the second of which is the MAC address of the device.
         */
        if (items.length == 3 && InetAddresses.isInetAddress(items[0]) && StringUtils.isMacAddress(items[1])) {
            return new LanDevice(items[0], items[1], items[2]);
        } else {
            return null;
        }
    }


}