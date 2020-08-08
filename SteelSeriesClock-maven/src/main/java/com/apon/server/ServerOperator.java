package com.apon.server;

import com.apon.api.ApiMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Sends requests to the server of the keyboard..
 */
public class ServerOperator {
    private final int port;

    public ServerOperator(int port) {
        this.port = port;
    }

    public void post(ApiMethod apiMethod, String... args) {
        try {
            URL urlForGetRequest = new URL("http://127.0.0.1:" + port + "/" + apiMethod.getUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlForGetRequest.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                String message = apiMethod.getMessage(args);
                byte[] input = message.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("JSON String Result " + getResponseAsString(httpURLConnection));
            } else {
                throw new ServerOperatorException("Request could not be processed.");
            }
        } catch (IOException e) {
            throw new ServerOperatorException(e);
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
