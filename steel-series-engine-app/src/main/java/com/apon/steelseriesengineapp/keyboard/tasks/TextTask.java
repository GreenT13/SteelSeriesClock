package com.apon.steelseriesengineapp.keyboard.tasks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TextTask implements IKeyboardTask {

    private final String textToShow;

    public TextTask(String textToShow) {
        this.textToShow = textToShow;
    }

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
                        "\"event\": \"TEXT\"," +
                        "\"data\": {\"value\": \"%s\"}" +
                        "}", textToShow)));
    }

    @Override
    public List<Action> prepare() {
        return Collections.singletonList(new Action(
                "bind_game_event",
                "{" +
                        "\"game\": \"SPRING\"," +
                        "\"event\": \"TEXT\"," +
                        "\"handlers\": [{" +
                        "  \"device-type\": \"screened\"," +
                        "  \"mode\": \"screen\"," +
                        "  \"zone\": \"one\"," +
                        "  \"datas\": [{ \"has-text\": true, \"arg\": \"(custom-text: (context-frame: self))}\" }]" +
                        "}]" +
                        "}"));
    }
}
