package com.varmarken.landevicetracker;

import com.varmarken.landevicetracker.model.LanDevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO add class documentation.
 *
 * @author Janus Varmarken
 */
@Controller
public class LanDeviceListController {

    @GetMapping("/devices")
    public String getDevices(@RequestParam(name="someParam", required = true) String someParam, Model model) {
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
        model.addAttribute("lanDevices", lanDevices);
        return "devices";
    }

}
