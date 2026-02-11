import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    public OrderPanel(GamingStoreApp app) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Orders", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Order ID", "Game", "Date", "Status"};
        java.util.List<String[]> orderList = DatabaseHelper.getOrders(app.getCurrentUser());
        Object[][] data = new Object[orderList.size()][4];
        for (int i = 0; i < orderList.size(); i++) {
            data[i] = orderList.get(i);
        }

        JTable table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton homeBtn = new JButton("Back to Home");
        add(homeBtn, BorderLayout.SOUTH);

        homeBtn.addActionListener(e -> app.showPage("HOME"));
    }
}
