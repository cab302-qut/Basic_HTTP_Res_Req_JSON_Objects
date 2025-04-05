package com.ollamates;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String model = "llama3.2:latest";
        String prompt = "Why is the sky blue?";
        String fullResponse = "";
        String line;
        try {
            // Set up an HTTP POST request
            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create request JSON
            JSONObject requestJson = new JSONObject();
            requestJson.put("model", model);
            requestJson.put("prompt", prompt);
            requestJson.put("stream", false);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestJson.toString().getBytes());
            }

            // Get response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String responseLine = br.readLine();
                JSONObject responseJson = new JSONObject(responseLine);
                fullResponse = responseJson.getString("response");
            }

            System.out.println(fullResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

}