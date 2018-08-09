package com.varmarken.landevicetracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO add class documentation.
 *
 * @author Janus Varmarken
 */
@Controller
public class LanDeviceListController {

    @GetMapping("/devices")
    public String getDevices(@RequestParam(name="someParam", required = true) String someParam, Model model) {
        model.addAttribute("someParam", someParam);
        return "devices";
    }

}
