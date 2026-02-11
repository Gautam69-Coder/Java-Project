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

        browseBtn.addActionListener(e -> app.showPage("GAME_LIST"));
        ordersBtn.addActionListener(e -> app.showPage("ORDERS"));
        logoutBtn.addActionListener(e -> app.showPage("LOGIN"));
    }
}
