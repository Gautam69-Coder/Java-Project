import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/gaming_store";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    
    // Offline Mode Storage
    private static boolean isOfflineMode = false;
    private static List<OfflineUser> offlineUsers = new ArrayList<>();
    private static List<Game> offlineGames = new ArrayList<>();
    private static List<OfflineOrder> offlineOrders = new ArrayList<>();

    private static class OfflineUser {
        String username, email, password;
        OfflineUser(String u, String e, String p) { username = u; email = e; password = p; }
    }

    private static class OfflineOrder {
        int id;
        String username, gameTitle, date, status;
        OfflineOrder(int id, String u, String g, String d, String s) {
            this.id = id; this.username = u; this.gameTitle = g; this.date = d; this.status = s;
        }
    }

    public static void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("Database Connected Successfully!");
                isOfflineMode = false;
                Statement stmt = conn.createStatement();
                
                // Users Table
                stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                        "username VARCHAR(255) PRIMARY KEY, " +
                        "email VARCHAR(255), " +
                        "password VARCHAR(255))");

                // Games Table
                stmt.execute("CREATE TABLE IF NOT EXISTS games (" +
                        "id VARCHAR(255) PRIMARY KEY, " +
                        "title VARCHAR(255), " +
                        "price REAL, " +
                        "description VARCHAR(255), " +
                        "color_rgb INTEGER)");

                // Orders Table
                stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "username VARCHAR(255), " +
                        "game_title VARCHAR(255), " +
                        "order_date VARCHAR(255), " +
                        "status VARCHAR(255))");

                // Seed Games if empty
                ResultSet rs = stmt.executeQuery("SELECT count(*) FROM games");
                if (rs.next() && rs.getInt(1) == 0) {
                    seedGames(conn);
                }
            }
        } catch (Exception e) {
            System.err.println("DB Connection Failed! Entering Offline Mode.");
            isOfflineMode = true;
            initializeOfflineData();
        }
    }

    private static void initializeOfflineData() {
        offlineGames.clear();
        offlineGames.add(new Game("1", "Cyberpunk 2077 (Offline)", 59.99, "Open-world action RPG.", new Color(255, 230, 0)));
        offlineGames.add(new Game("2", "Elden Ring (Offline)", 59.99, "Fantasy action-RPG.", new Color(200, 160, 40)));
        offlineGames.add(new Game("3", "Minecraft (Offline)", 26.95, "Infinite worlds.", new Color(50, 180, 50)));
        offlineGames.add(new Game("4", "RDR 2 (Offline)", 39.99, "Western epic.", new Color(180, 50, 50)));
        
        // Add a default test user for offline mode
        offlineUsers.add(new OfflineUser("test", "test@example.com", "test"));
    }

    public static boolean isOffline() {
        return isOfflineMode;
    }

    private static void seedGames(Connection conn) throws SQLException {
        String sql = "INSERT INTO games (id, title, price, description, color_rgb) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Object[][] defaultGames = {
                {"1", "Cyberpunk 2077", 59.99, "Open-world action RPG.", new Color(255, 230, 0).getRGB()},
                {"2", "Elden Ring", 59.99, "Fantasy action-RPG.", new Color(200, 160, 40).getRGB()},
                {"3", "Minecraft", 26.95, "Infinite worlds.", new Color(50, 180, 50).getRGB()},
                {"4", "RDR 2", 39.99, "Western epic.", new Color(180, 50, 50).getRGB()}
            };
            for (Object[] g : defaultGames) {
                pstmt.setString(1, (String)g[0]);
                pstmt.setString(2, (String)g[1]);
                pstmt.setDouble(3, (Double)g[2]);
                pstmt.setString(4, (String)g[3]);
                pstmt.setInt(5, (Integer)g[4]);
                pstmt.executeUpdate();
            }
        }
    }

    public static boolean checkUserExists(String username) {
        if (isOfflineMode) {
            return offlineUsers.stream().anyMatch(u -> u.username.equals(username));
        }
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static int registerUserStatus(String username, String email, String password) {
        if (isOfflineMode) {
            if (checkUserExists(username)) return 0;
            offlineUsers.add(new OfflineUser(username, email, password));
            return 1;
        }
        try {
            if (checkUserExists(username)) return 0;
        } catch (Exception e) { return -1; }

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public static boolean loginUser(String username, String password) {
        if (isOfflineMode) {
            return offlineUsers.stream().anyMatch(u -> u.username.equals(username) && u.password.equals(password));
        }
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Game> getAllGames() {
        if (isOfflineMode) return new ArrayList<>(offlineGames);
        
        List<Game> games = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM games")) {
            while (rs.next()) {
                games.add(new Game(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    new Color(rs.getInt("color_rgb"))
                ));
            }
        } catch (SQLException e) {
            return offlineGames; // Fallback if query fails
        }
        return games;
    }

    public static void placeOrder(String username, String gameTitle) {
        if (isOfflineMode) {
            offlineOrders.add(new OfflineOrder(offlineOrders.size() + 1, username, gameTitle, "2024-02-13", "Completed"));
            return;
        }
        String sql = "INSERT INTO orders (username, game_title, order_date, status) VALUES (?, ?, CURDATE(), 'Completed')";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, gameTitle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getOrders(String username) {
        List<String[]> orders = new ArrayList<>();
        if (isOfflineMode) {
            for (OfflineOrder o : offlineOrders) {
                if (o.username.equals(username)) {
                    orders.add(new String[]{"ORD-" + o.id, o.gameTitle, o.date, o.status});
                }
            }
            return orders;
        }
        String sql = "SELECT id, game_title, order_date, status FROM orders WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(new String[]{
                    "ORD-" + rs.getInt("id"),
                    rs.getString("game_title"),
                    rs.getString("order_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}

