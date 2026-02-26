import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMServer {
    private static final int PORT = 8080;
    private static ATMDatabase database;
    private static Map<String, String> activeSessions; // Store current logged-in users

    public static void main(String[] args) throws IOException {
        database = new ATMDatabase();
        activeSessions = new HashMap<>();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

        // Register API endpoints
        server.createContext("/api/authenticate", new AuthenticateHandler());
        server.createContext("/api/balance", new BalanceHandler());
        server.createContext("/api/deposit", new DepositHandler());
        server.createContext("/api/withdraw", new WithdrawHandler());
        server.createContext("/api/logout", new LogoutHandler());
        server.createContext("/", new CORSHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("================================");
        System.out.println("ATM Server Started");
        System.out.println("================================");
        System.out.println("Server running on http://localhost:" + PORT);
        System.out.println("\nDefault Test Users:");
        System.out.println("  User ID: user1, PIN: 1234");
        System.out.println("  User ID: user2, PIN: 5678");
        System.out.println("  User ID: user3, PIN: 9999");
        System.out.println("\nPress Ctrl+C to stop the server");
    }

    // CORS Handler
    static class CORSHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            }
        }
    }

    // Authenticate Handler
    static class AuthenticateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = readRequestBody(exchange);
                Map<String, String> params = parseJson(body);

                String userId = params.get("userId");
                String pin = params.get("pin");

                if (userId != null && pin != null && database.authenticateUser(userId, pin)) {
                    // Create session
                    String sessionId = generateSessionId();
                    activeSessions.put(sessionId, userId);

                    String response = "{\"success\": true, \"sessionId\": \"" + sessionId + "\", \"userId\": \"" + userId + "\"}";
                    sendResponse(exchange, 200, response);
                } else {
                    String response = "{\"success\": false, \"error\": \"Invalid credentials\"}";
                    sendResponse(exchange, 401, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }
    }

    // Balance Handler
    static class BalanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = parseQuery(query);

                String sessionId = params.get("sessionId");
                String userId = activeSessions.get(sessionId);

                if (userId != null) {
                    double balance = database.getBalance(userId);
                    String response = "{\"success\": true, \"balance\": " + balance + ", \"userId\": \"" + userId + "\"}";
                    sendResponse(exchange, 200, response);
                } else {
                    String response = "{\"success\": false, \"error\": \"Invalid session\"}";
                    sendResponse(exchange, 401, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }
    }

    // Deposit Handler
    static class DepositHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = readRequestBody(exchange);
                Map<String, String> params = parseJson(body);

                String sessionId = params.get("sessionId");
                String amountStr = params.get("amount");

                String userId = activeSessions.get(sessionId);

                if (userId != null && amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);

                        if (database.deposit(userId, amount)) {
                            double newBalance = database.getBalance(userId);
                            String response = "{\"success\": true, \"message\": \"Deposit successful\", \"newBalance\": " + newBalance + "}";
                            sendResponse(exchange, 200, response);
                        } else {
                            String response = "{\"success\": false, \"error\": \"Invalid amount\"}";
                            sendResponse(exchange, 400, response);
                        }
                    } catch (NumberFormatException e) {
                        String response = "{\"success\": false, \"error\": \"Invalid amount format\"}";
                        sendResponse(exchange, 400, response);
                    }
                } else {
                    String response = "{\"success\": false, \"error\": \"Invalid session or amount\"}";
                    sendResponse(exchange, 401, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }
    }

    // Withdraw Handler
    static class WithdrawHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = readRequestBody(exchange);
                Map<String, String> params = parseJson(body);

                String sessionId = params.get("sessionId");
                String amountStr = params.get("amount");

                String userId = activeSessions.get(sessionId);

                if (userId != null && amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        double currentBalance = database.getBalance(userId);

                        if (amount > currentBalance) {
                            String response = "{\"success\": false, \"error\": \"Insufficient funds\", \"currentBalance\": " + currentBalance + "}";
                            sendResponse(exchange, 400, response);
                        } else if (database.withdraw(userId, amount)) {
                            double newBalance = database.getBalance(userId);
                            String response = "{\"success\": true, \"message\": \"Withdrawal successful\", \"newBalance\": " + newBalance + "}";
                            sendResponse(exchange, 200, response);
                        } else {
                            String response = "{\"success\": false, \"error\": \"Invalid amount\"}";
                            sendResponse(exchange, 400, response);
                        }
                    } catch (NumberFormatException e) {
                        String response = "{\"success\": false, \"error\": \"Invalid amount format\"}";
                        sendResponse(exchange, 400, response);
                    }
                } else {
                    String response = "{\"success\": false, \"error\": \"Invalid session or amount\"}";
                    sendResponse(exchange, 401, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }
    }

    // Logout Handler
    static class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = readRequestBody(exchange);
                Map<String, String> params = parseJson(body);

                String sessionId = params.get("sessionId");

                if (activeSessions.containsKey(sessionId)) {
                    activeSessions.remove(sessionId);
                    String response = "{\"success\": true, \"message\": \"Logged out successfully\"}";
                    sendResponse(exchange, 200, response);
                } else {
                    String response = "{\"success\": false, \"error\": \"Invalid session\"}";
                    sendResponse(exchange, 401, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }
    }

    // Utility Methods
    private static String readRequestBody(HttpExchange exchange) throws IOException {
        StringBuilder body = new StringBuilder();
        try (InputStream is = exchange.getRequestBody();
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                body.append(scanner.nextLine());
            }
        }
        return body.toString();
    }

    private static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.replaceAll("[{}\"\\s]", "");
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return map;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String generateSessionId() {
        return "session_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000);
    }
}
