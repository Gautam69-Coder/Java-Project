import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(GamingStoreApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login to Gaming Store", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        JTextField userField = new JTextField(15);
        gbc.gridx = 1; add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Password:"), gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1; add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginBtn, gbc);

        JButton signupBtn = new JButton("Don't have an account? Sign Up");
        gbc.gridy = 4;
        add(signupBtn, gbc);

        // Database Status Label
        JLabel statusLabel = new JLabel();
        if (DatabaseHelper.isOffline()) {
            statusLabel.setText("● Offline Mode (XAMPP/WAMP not connected)");
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setText("● Database Connected");
            statusLabel.setForeground(new Color(0, 150, 0));
        }
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 5;
        add(statusLabel, gbc);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (DatabaseHelper.loginUser(username, password)) {
                    app.setCurrentUser(username);
                    app.showPage("HOME");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signupBtn.addActionListener(e -> app.showPage("SIGNUP"));
    }
}
