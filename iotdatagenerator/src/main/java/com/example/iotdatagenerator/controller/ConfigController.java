package com.example.iotdatagenerator.controller;

import com.example.iotdatagenerator.service.DataSendingSchedulerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final DataSendingSchedulerService scheduler;

    public ConfigController(DataSendingSchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    // configure the number of devices and messages per device at runtime.
    @PostMapping("/setSchedule")
    public String setSchedule(@RequestParam int devices, @RequestParam int messages) {
        scheduler.setConfig(devices, messages);
        return "Schedule config updated.";
    }

}
