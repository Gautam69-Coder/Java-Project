import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameListPanel extends JPanel {
    public GameListPanel(GamingStoreApp app) {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Available Games", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Game List
        List<Game> games = DatabaseHelper.getAllGames();

        // Grid Panel
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Game g : games) {
            gridPanel.add(new GameCard(app, g));
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("Back to Home");
        add(backBtn, BorderLayout.SOUTH);
        backBtn.addActionListener(e -> app.showPage("HOME"));
    }

    // Inner class for Game Box
    private class GameCard extends JPanel {
        public GameCard(GamingStoreApp app, Game game) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setBackground(Color.WHITE);

            // "Image" Placeholder
            JPanel imageBox = new JPanel();
            imageBox.setBackground(game.getColor());
            imageBox.setPreferredSize(new Dimension(150, 100));
            imageBox.add(new JLabel(game.getTitle())); // Title overlay on box

            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            infoPanel.setOpaque(false);
            
            JLabel nameLabel = new JLabel(game.getTitle());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            
            JLabel priceLabel = new JLabel("$" + game.getPrice());
            priceLabel.setForeground(new Color(0, 120, 0));
            
            JButton buyBtn = new JButton("View Details");
            buyBtn.addActionListener(e -> {
                app.setSelectedGame(game);
                app.showPage("GAME_DETAIL");
            });

            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(buyBtn);

            add(imageBox, BorderLayout.CENTER);
            add(infoPanel, BorderLayout.SOUTH);
        }
    }
}
