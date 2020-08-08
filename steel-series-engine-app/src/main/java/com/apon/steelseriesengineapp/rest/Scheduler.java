package com.apon.steelseriesengineapp.rest;

import com.apon.steelseriesengineapp.keyboard.KeyboardTaskScheduler;
import com.apon.steelseriesengineapp.keyboard.tasks.ClockTask;
import com.apon.steelseriesengineapp.keyboard.tasks.TextTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Scheduler {

    private final KeyboardTaskScheduler keyboardTaskScheduler;

    public Scheduler(KeyboardTaskScheduler keyboardTaskScheduler) {
        this.keyboardTaskScheduler = keyboardTaskScheduler;
    }

    @GetMapping("/clock")
    public void clock() {
        ClockTask clockTask = new ClockTask();
        keyboardTaskScheduler.schedule(clockTask);
    }

    @GetMapping("/text")
    public void text() {
        TextTask textTask = new TextTask("Dit werkt!");
        keyboardTaskScheduler.schedule(textTask);
    }
}
