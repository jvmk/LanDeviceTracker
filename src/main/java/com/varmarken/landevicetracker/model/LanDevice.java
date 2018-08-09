package com.varmarken.landevicetracker.model;

import com.google.common.net.InetAddresses;
import com.varmarken.landevicetracker.StringUtils;

/**
 * Models a device connected to the local area network (LAN).<br/>
 * Immutable.
 *
 * @author Janus Varmarken {@literal <jvarmark@uci.edu>}
 */
public class LanDevice {

    /**
     * The IP address of this {@link LanDevice} at the point of initialization.
     */
    private final String mIp;
    /**
     * The MAC address of the NIC of this {@link LanDevice}.
     */
    private final String mMac;
    /**
     * The manufacturer of the NIC of this {@link LanDevice}.
     */
    private final String mManufacturer;

    /**
     * Initializes a new {@link LanDevice}.
     * @param ip The current IP address (of the NIC) of the device.
     * @param mac The MAC address (of the NIC) of the device.
     * @param manufacturer The manufacturer (of the NIC) of the device.
     */
    public LanDevice(String ip, String mac, String manufacturer) {
        if (ip == null || !InetAddresses.isInetAddress(ip)) {
            String excMsg = String.format("%s constructor: invalid IP address", getClass().getSimpleName());
            throw new IllegalArgumentException(excMsg);
        }
        if (!StringUtils.isMacAddress(mac)) {
            String excMsg = String.format("%s constructor: invalid MAC address", getClass().getSimpleName());
            throw new IllegalArgumentException(excMsg);
        }
        mIp = ip;
        mMac = mac;
        mManufacturer = manufacturer;
    }

    /**
     * Gets the IP address of this device in human readable format.
     * @return the IP address of this device in human readable format.
     */
    public String getIpAddress() {
        return mIp;
    }

    /**
     * Gets the MAC address (in IEEE 802 standard format) of the NIC of this device.
     * @return the MAC address (in IEEE 802 standard format) of the NIC of this device.
     */
    public String getMacAddress() {
        return mMac;
    }

    /**
     * Gets the name of the manufacturer of the NIC of this device.
     * @return the name of the manufacturer of the NIC of this device.
     */
    public String getNicManufacturer() {
        return mManufacturer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("{ %s with field values ip=%s; mac=%s; manufacturer='%s'; }",
                getClass().getSimpleName(), mIp, mMac, mManufacturer);
    }

    /**
     * Equality is solely determined based on the value returned by {@link #getMacAddress()} in order to allow
     * {@link LanDevice}s to be used as keys in {@link java.util.Map}s (or {@link java.util.Set}s) in spite of the
     * possibility of the device changing IPs in-between successive ARP scans.
     *
     * @param obj An object to compare for equality with this {@link LanDevice}.
     * @return {@code true} <em>iff</em> {@code obj} is a {@link LanDevice} whose MAC address is equal to the MAC
     *         address of this device.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LanDevice)) {
            return false;
        }
        LanDevice that = (LanDevice) obj;
        return this.getMacAddress().equals(that.getMacAddress());
    }

    /**
     * As equality is determined based on the value returned by {@link #getMacAddress()}, the hash code of this
     * {@link LanDevice} is accordingly defined as the hash code of the string returned by {@link #getMacAddress()}
     * @return The hash code of this device.
     */
    @Override
    public int hashCode() {
        return getMacAddress().hashCode();
    }
}
