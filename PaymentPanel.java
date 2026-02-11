import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {
    public PaymentPanel(GamingStoreApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Payment Information", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Payment Method:"), gbc);
        String[] types = {"Credit/Debit Card", "UPI"};
        JComboBox<String> paymentType = new JComboBox<>(types);
        gbc.gridx = 1; add(paymentType, gbc);

        // Card Panel
        JPanel cardPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        cardPanel.add(new JLabel("Card Number:"));
        cardPanel.add(new JTextField(16));
        cardPanel.add(new JLabel("Expiry (MM/YY):"));
        cardPanel.add(new JTextField(5));
        cardPanel.add(new JLabel("CVV:"));
        cardPanel.add(new JPasswordField(3));

        // UPI Panel
        JPanel upiPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        upiPanel.add(new JLabel("UPI ID (e.g., user@bank):"));
        upiPanel.add(new JTextField(15));
        upiPanel.add(new JLabel(" ")); // Placeholder for grid alignment
        upiPanel.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(cardPanel, gbc);
        add(upiPanel, gbc);

        paymentType.addActionListener(e -> {
            boolean isCard = paymentType.getSelectedIndex() == 0;
            cardPanel.setVisible(isCard);
            upiPanel.setVisible(!isCard);
            revalidate();
            repaint();
        });

        JButton payBtn = new JButton("Confirm Payment");
        gbc.gridy = 4;
        add(payBtn, gbc);

        JButton backBtn = new JButton("Cancel");
        gbc.gridy = 5;
        add(backBtn, gbc);

        payBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment Successful!");
            Game game = app.getSelectedGame();
            String gameTitle = (game != null) ? game.getTitle() : "Unknown Game";
            DatabaseHelper.placeOrder(app.getCurrentUser(), gameTitle);
            app.showPage("ORDERS");
        });
        backBtn.addActionListener(e -> app.showPage("GAME_DETAIL"));
    }
}
