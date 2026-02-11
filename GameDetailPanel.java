import javax.swing.*;
import java.awt.*;

public class GameDetailPanel extends JPanel {
    public GameDetailPanel(GamingStoreApp app) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Game Details
        Game game = app.getSelectedGame();
        String titleText = (game != null) ? game.getTitle() : "Select a Game";
        String priceText = (game != null) ? "$ " + game.getPrice() : "$ --.--";
        String descText = (game != null) ? game.getDescription() : "No description available.";
        Color gameColor = (game != null) ? game.getColor() : Color.LIGHT_GRAY;

        // Title
        JLabel title = new JLabel("Game Details", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1));
        detailsPanel.add(new JLabel("Title: " + titleText));
        detailsPanel.add(new JLabel("Price: " + priceText));
        detailsPanel.add(new JLabel("Rating: ★★★★☆"));
        
        // Image Placeholder (Box)
        JPanel imageBox = new JPanel();
        imageBox.setBackground(gameColor);
        imageBox.setPreferredSize(new Dimension(200, 150));
        imageBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Description
        JTextArea desc = new JTextArea(descText);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(getBackground());

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imageBox, BorderLayout.NORTH);
        centerPanel.add(detailsPanel, BorderLayout.CENTER);
        centerPanel.add(new JScrollPane(desc), BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel btnPanel = new JPanel();
        JButton buyBtn = new JButton("Buy Now");
        JButton backBtn = new JButton("Back");
        btnPanel.add(backBtn);
        btnPanel.add(buyBtn);
        add(btnPanel, BorderLayout.SOUTH);

        buyBtn.addActionListener(e -> {
            if (game != null) {
                app.showPage("PAYMENT");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game first!");
            }
        });
        backBtn.addActionListener(e -> app.showPage("GAME_LIST"));
    }
}
