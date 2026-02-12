import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(GamingStoreApp app) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to Gaming Store!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JButton browseBtn = new JButton("Browse Games");
        JButton ordersBtn = new JButton("View My Orders");
        JButton logoutBtn = new JButton("Logout");

        menuPanel.add(browseBtn);
        menuPanel.add(ordersBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Status Panel
        JPanel statusPanel = new JPanel();
        JLabel statusLabel = new JLabel();
        if (DatabaseHelper.isOffline()) {
            statusLabel.setText("Running in Offline Mode (No Database)");
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setText("Database Connected Successfully");
            statusLabel.setForeground(new Color(0, 150, 0));
        }
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);

        browseBtn.addActionListener(e -> app.showPage("GAME_LIST"));
        ordersBtn.addActionListener(e -> app.showPage("ORDERS"));
        logoutBtn.addActionListener(e -> app.showPage("LOGIN"));
    }
}
