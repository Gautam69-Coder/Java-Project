import javax.swing.*;
import java.awt.*;

public class GamingStoreApp extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private String currentUser;
    private Game selectedGame;

    public GamingStoreApp() {
        DatabaseHelper.initialize();
        setTitle("Simple Gaming Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        refreshPages(); // Initial page setup

        add(mainPanel);
        showPage("LOGIN");
    }

    public void refreshPages() {
        mainPanel.removeAll();
        mainPanel.add(new LoginPanel(this), "LOGIN");
        mainPanel.add(new SignupPanel(this), "SIGNUP");
        mainPanel.add(new HomePanel(this), "HOME");
        mainPanel.add(new GameListPanel(this), "GAME_LIST");
        mainPanel.add(new GameDetailPanel(this), "GAME_DETAIL");
        mainPanel.add(new PaymentPanel(this), "PAYMENT");
        mainPanel.add(new OrderPanel(this), "ORDERS");
        mainPanel.revalidate();
    }

    public void setCurrentUser(String user) { this.currentUser = user; }
    public String getCurrentUser() { return currentUser; }

    public void setSelectedGame(Game game) { this.selectedGame = game; }
    public Game getSelectedGame() { return selectedGame; }

    public void showPage(String pageName) {
        if (pageName.equals("ORDERS") || pageName.equals("GAME_LIST") || pageName.equals("GAME_DETAIL")) {
            refreshPages(); // Ensure data is fresh from DB or selection
        }
        cardLayout.show(mainPanel, pageName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GamingStoreApp().setVisible(true);
        });
    }
}
