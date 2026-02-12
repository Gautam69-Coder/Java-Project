import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {
    public SignupPanel(GamingStoreApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Account", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        JTextField userField = new JTextField(15);
        gbc.gridx = 1; add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(15);
        gbc.gridx = 1; add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Password:"), gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1; add(passField, gbc);

        JButton signupBtn = new JButton("Sign Up");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(signupBtn, gbc);

        JButton loginBtn = new JButton("Already have an account? Login");
        gbc.gridy = 5;
        add(loginBtn, gbc);

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
        gbc.gridy = 6;
        add(statusLabel, gbc);

        signupBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int result = DatabaseHelper.registerUserStatus(username, email, password);
                if (result == 1) {
                    JOptionPane.showMessageDialog(this, "Signup Successful!");
                    app.showPage("LOGIN");
                } else if (result == 0) {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Database Error! Check your XAMPP/MySQL connection.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginBtn.addActionListener(e -> app.showPage("LOGIN"));
    }
}
