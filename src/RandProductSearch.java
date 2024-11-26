import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel searchLabel = new JLabel("Search Name:");
        searchLabel.setBounds(10, 20, 100, 25);
        panel.add(searchLabel);

        JTextField searchText = new JTextField(20);
        searchText.setBounds(120, 20, 165, 25);
        panel.add(searchText);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(10, 60, 560, 280);
        resultArea.setEditable(false);
        panel.add(resultArea);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(10, 350, 100, 25);
        panel.add(searchButton);

        searchButton.addActionListener(e -> {
            String query = searchText.getText().trim().toLowerCase();
            resultArea.setText("");

            try (RandomAccessFile raf = new RandomAccessFile("ProductData.txt", "r")) {
                ArrayList<String> results = new ArrayList<>();
                while (raf.getFilePointer() < raf.length()) {
                    String id = Product.readFixedString(raf, 6);
                    String name = Product.readFixedString(raf, 35);
                    String description = Product.readFixedString(raf, 75);
                    double cost = raf.readDouble();

                    if (name.toLowerCase().contains(query)) {
                        results.add(String.format("ID: %s, Name: %s, Desc: %s, Cost: %.2f", id, name, description, cost));
                    }
                }

                if (results.isEmpty()) {
                    resultArea.setText("No matches found.");
                } else {
                    for (String result : results) {
                        resultArea.append(result + "\n");
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error searching products: " + ex.getMessage());
            }
        });
    }
}
