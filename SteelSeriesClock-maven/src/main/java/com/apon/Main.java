package com.apon;

import com.apon.clock.ClockEngine;
import com.apon.server.ServerOperator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private final static Pattern PORT_PATTERN = Pattern.compile(".*\"address\":\".*?:(\\d+)\".*");

    public static void main(String... args) {
        ClockEngine clockEngine = new ClockEngine(new ServerOperator(readPort()));
        clockEngine.start();
    }

    /**
     * Returns the port from the SteelSeries Engine to which we need to send requests.
     * @return The port number.
     */
    private static int readPort() {
        try {
            String content = Files.readString(Paths.get(System.getenv("PROGRAMDATA")+"/SteelSeries/SteelSeries Engine 3/coreProps.json"), StandardCharsets.UTF_8);

            Matcher matcher = PORT_PATTERN.matcher(content);
            if (matcher.matches()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                throw new RuntimeException("Could not read port from coreProps.json.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
