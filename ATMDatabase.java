import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ATMDatabase {
    private static final String DB_FILE = "atm_database.dat";
    private Map<String, Double> userBalances;
    private Map<String, String> userPins;

    public ATMDatabase() {
        this.userBalances = new HashMap<>();
        this.userPins = new HashMap<>();
        loadFromFile();
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        // Create default users if database is empty
        if (userBalances.isEmpty()) {
            userBalances.put("user1", 1000000.0);
            userPins.put("user1", "1234");

            userBalances.put("user2", 500000.0);
            userPins.put("user2", "5678");

            userBalances.put("user3", 750000.0);
            userPins.put("user3", "9999");

            saveToFile();
        }
    }

    public synchronized boolean authenticateUser(String userId, String pin) {
        String storedPin = userPins.get(userId);
        return storedPin != null && storedPin.equals(pin);
    }

    public synchronized double getBalance(String userId) {
        return userBalances.getOrDefault(userId, 0.0);
    }

    public synchronized boolean deposit(String userId, double amount) {
        if (amount <= 0) {
            return false;
        }

        if (userBalances.containsKey(userId)) {
            double currentBalance = userBalances.get(userId);
            userBalances.put(userId, currentBalance + amount);
            saveToFile();
            return true;
        }
        return false;
    }

    public synchronized boolean withdraw(String userId, double amount) {
        if (amount <= 0) {
            return false;
        }

        if (userBalances.containsKey(userId)) {
            double currentBalance = userBalances.get(userId);
            if (currentBalance >= amount) {
                userBalances.put(userId, currentBalance - amount);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean createUser(String userId, String pin, double initialBalance) {
        if (userBalances.containsKey(userId)) {
            return false; // User already exists
        }

        userBalances.put(userId, initialBalance);
        userPins.put(userId, pin);
        saveToFile();
        return true;
    }

    private synchronized void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            oos.writeObject(userBalances);
            oos.writeObject(userPins);
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
        }
    }

    private synchronized void loadFromFile() {
        File dbFile = new File(DB_FILE);
        if (dbFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DB_FILE))) {
                userBalances = (Map<String, Double>) ois.readObject();
                userPins = (Map<String, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading database: " + e.getMessage());
                userBalances = new HashMap<>();
                userPins = new HashMap<>();
            }
        }
    }

    public synchronized Map<String, Double> getAllBalances() {
        return new HashMap<>(userBalances);
    }
}
