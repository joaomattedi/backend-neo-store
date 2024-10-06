package org.desafio.neo.resource;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HealthHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response;

        if ("GET".equalsIgnoreCase(method)) {
            response = "Server working!";
            exchange.sendResponseHeaders(200, response.length());
        } else {
            response = "Method not supported";
            exchange.sendResponseHeaders(405, response.length()); // 405 Method Not Allowed
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
