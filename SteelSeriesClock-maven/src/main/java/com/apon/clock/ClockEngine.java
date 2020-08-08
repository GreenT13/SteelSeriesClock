package com.apon.clock;

import com.apon.api.ApiMethod;
import com.apon.server.ServerOperator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClockEngine {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private final ServerOperator serverOperator;

    public ClockEngine(ServerOperator serverOperator) {
        this.serverOperator = serverOperator;
    }

    public void start() {
        // Register needed data to function (only relevant the first time).
        serverOperator.post(ApiMethod.GAME_META_DATA);
        serverOperator.post(ApiMethod.BIND_GAME_EVENT);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::updateTime, 0,1,  TimeUnit.SECONDS);
    }

    private void updateTime() {
        serverOperator.post(ApiMethod.GAME_EVENT, formatTimeAsString());
    }

    private String formatTimeAsString() {
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}

