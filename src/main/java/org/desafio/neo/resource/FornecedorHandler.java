package org.desafio.neo.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.desafio.neo.model.Fornecedor;
import org.desafio.neo.service.FornecedorService;
import org.desafio.neo.utils.RequestUtils;
import org.hibernate.Session;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FornecedorHandler implements HttpHandler {
    private FornecedorService fornecedorService;

    public FornecedorHandler(Session session) {
        this.fornecedorService = new FornecedorService(session);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String response = "Metódo HTTP não encontrado";
        switch (exchange.getRequestMethod()) {
            case "GET":
                // get list, get single
                response = handlerGETMethod(exchange);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                break;
            case "POST":
                // post fornecedores (sempre receber array de objetos) - JSON
                response = handlerPOSTMethod(exchange.getRequestURI().getPath(), exchange.getRequestBody());
                break;
            case "PUT":
                // put fornecedores - JSON
                response = handlerPUTMethod(exchange.getRequestURI().getPath(), exchange.getRequestBody());
                break;
            case "DELETE":
                // delete fornecedor - JSON
                response = handlerDELETEMethod(exchange.getRequestURI().getPath());
                break;
        }

        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        os.close();
    }

    private String handlerGETMethod(HttpExchange exchange) {
        Gson gson = new GsonBuilder().create();
        String apiPath = exchange.getRequestURI().getPath();

        if (apiPath.equals("/api/fornecedores/list")) {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> queryParams = RequestUtils.parseQueryParams(query);

            int page = queryParams.containsKey("page") ? Integer.parseInt(queryParams.get("page")) : 1;
            int size = queryParams.containsKey("size") ? Integer.parseInt(queryParams.get("size")) : 5;

            return gson.toJson(fornecedorService.getAllPaginated(page, size));
        } else if (apiPath.matches("/api/fornecedores/\\d+")) {
            String idString = apiPath.substring(apiPath.lastIndexOf("/") + 1);
            return gson.toJson(fornecedorService.getFornecedorById(Long.parseLong(idString)));
        }

        return "Endpoint não encontrado";
    }

    private String handlerPOSTMethod(String apiPath, InputStream requestInput) {
        Gson gson = new GsonBuilder().create();

        if (apiPath.equals("/api/fornecedores")) {
            InputStreamReader isr = new InputStreamReader(requestInput, StandardCharsets.UTF_8);
            JsonArray jsonArray = gson.fromJson(isr, JsonArray.class);
            JsonObject responseJson = fornecedorService.createFornecedores(jsonArray);

            return gson.toJson(responseJson);
        }

        return "Endpoint não encontrado";
    }

    private String handlerPUTMethod(String apiPath, InputStream requestInput) {
        Gson gson = new GsonBuilder().create();

        if (apiPath.equals("/api/fornecedores")) {
            try {

                InputStreamReader isr = new InputStreamReader(requestInput, StandardCharsets.UTF_8);
                JsonObject jsonBody = new Gson().fromJson(isr, JsonObject.class);

                Fornecedor fornecedorAtualizado = gson.fromJson(jsonBody, Fornecedor.class);
                Fornecedor fornecedorAtual = fornecedorService.updateFornecedor(fornecedorAtualizado);

                return gson.toJson(fornecedorAtual);

            } catch (Exception e) {
                e.printStackTrace();
                return  "Erro ao atualizar o fornecedor: " + e.getMessage();
            }
        }

        return "Endpoint não encontrado";
    }

    private String handlerDELETEMethod(String apiPath) {
        if (apiPath.matches("/api/fornecedores/\\d+")) {
            try {
                String idString = apiPath.substring(apiPath.lastIndexOf("/") + 1);
                Long fornecedorId = Long.parseLong(idString);

                fornecedorService.deleteFornecedor(fornecedorId);

                return "Fornecedor com ID " + fornecedorId + " deletado com sucesso!";
            } catch (Exception e) {
                e.printStackTrace();
                return  "Erro ao atualizar o fornecedor: " + e.getMessage();
            }
        }

        return "Endpoint não encontrado";
    }
}
