package com.apon.steelseriesengineapp.keyboard;

import com.apon.steelseriesengineapp.keyboard.tasks.IKeyboardTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KeyboardServer {
    private final static Pattern PORT_PATTERN = Pattern.compile(".*\"address\":\".*?:(\\d+)\".*");
    private static final Logger log = LoggerFactory.getLogger(KeyboardServer.class);
    private final int port;

    public KeyboardServer() {
        port = readPort();
        log.info("Using port: " + port);
    }

    /**
     * Determines the port from the SteelSeries Engine to which we need to send requests.
     * @return The port number.
     */
    private int readPort() {
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

    public void post(IKeyboardTask.Action action) {
        try {
            URL urlForGetRequest = new URL("http://127.0.0.1:" + port + "/" + action.getUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlForGetRequest.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                byte[] input = action.getMessage().getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                log.info("JSON String Result " + getResponseAsString(httpURLConnection));
            } else {
                throw new KeyboardServerException("Request could not be processed.");
            }
        } catch (IOException e) {
            throw new KeyboardServerException(e);
        }
    }

    private String getResponseAsString(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String readLine;
        while ((readLine = in.readLine()) != null) {
            response.append(readLine);
        }
        in.close();

        return response.toString();
    }
}
