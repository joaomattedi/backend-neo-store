package org.desafio.neo;

import com.sun.net.httpserver.HttpServer;
import org.desafio.neo.resource.FornecedorHandler;
import org.desafio.neo.resource.HealthHandler;
import org.desafio.neo.utils.db.HibernateUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    public static void main(String[] args) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Runtime.getRuntime().addShutdownHook(new Thread(session::close));

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/health-check", new HealthHandler());
        server.createContext("/api/fornecedores", new FornecedorHandler(session));
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080...");
    }
}