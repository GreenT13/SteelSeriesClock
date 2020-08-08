package com.apon.api;

public enum ApiMethod {
    GAME_META_DATA("game_metadata", "{ " +
            "\"game\": \"CLOCK\"," +
            "\"game_display_name\": \"Shows the time\"," +
            "\"developer\": \"Rico Apon\"" +
            "}"),
    BIND_GAME_EVENT("bind_game_event", "{" +
            "\"game\": \"CLOCK\"," +
            "\"event\": \"TIME\"," +
            "\"handlers\": [{" +
            "  \"device-type\": \"screened\"," +
            "  \"mode\": \"screen\"," +
            "  \"zone\": \"one\"," +
            "  \"datas\": [{ \"has-text\": true, \"arg\": \"(custom-text: (context-frame: self))}\" }]" +
            "}]" +
            "}"),
    GAME_EVENT("game_event", "{" +
            "\"game\": \"CLOCK\"," +
            "\"event\": \"TIME\"," +
            "\"data\": {\"value\": \"%s\"}" +
            "}");

    private final String url;
    private final String messageTemplate;

    ApiMethod(String url, String messageTemplate) {
        this.url = url;
        this.messageTemplate = messageTemplate;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage(String... args) {
        //noinspection ConfusingArgumentToVarargsMethod
        return String.format(messageTemplate, args);
    }
}
