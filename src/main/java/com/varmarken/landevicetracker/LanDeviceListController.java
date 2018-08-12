package com.varmarken.landevicetracker;

import com.varmarken.landevicetracker.model.LanDevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO add class documentation.
 *
 * @author Janus Varmarken
 */
@Controller
public class LanDeviceListController {

    @GetMapping("/landevices")
    public String getDevices(Model model) {
        List<LanDevice> lanDevices = null;
        try {
            lanDevices = new ArpScanProcess().execute();
        } catch (IOException e) {
            // TODO: figure out how to return a HTTP 500 error from here
            e.printStackTrace();
        }
        if (lanDevices == null) {
            // For now, just return empty list in case of error.
            lanDevices = new ArrayList<>();
        }
        // arp-scan can detect same device twice, so remove duplicates before presenting results to the user
        Set<LanDevice> lanDevicesSet = new HashSet<>();
        lanDevicesSet.addAll(lanDevices);

        model.addAttribute("lanDevices", lanDevicesSet);
        return "landevices";
    }

}
