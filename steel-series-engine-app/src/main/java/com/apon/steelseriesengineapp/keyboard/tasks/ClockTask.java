package com.apon.steelseriesengineapp.keyboard.tasks;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Task that shows the time.
 */
public class ClockTask implements IKeyboardTask {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public int getPeriod() {
        return 1000;
    }

    @Override
    public Optional<Action> run() {
        return Optional.of(new Action(
                "game_event",
                String.format("{" +
                        "\"game\": \"SPRING\"," +
                        "\"event\": \"TIME\"," +
                        "\"data\": {\"value\": \"%s\"}" +
                        "}", simpleDateFormat.format(new Date()))));
    }

    @Override
    public List<Action> prepare() {
        return Collections.singletonList(new Action(
                "bind_game_event",
                "{" +
                        "\"game\": \"SPRING\"," +
                        "\"event\": \"TIME\"," +
                        "\"handlers\": [{" +
                        "  \"device-type\": \"screened\"," +
                        "  \"mode\": \"screen\"," +
                        "  \"zone\": \"one\"," +
                        "  \"datas\": [{ \"has-text\": true, \"arg\": \"(custom-text: (context-frame: self))}\" }]" +
                        "}]" +
                        "}"));
    }
}
