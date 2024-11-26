import javax.swing.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        JTextField idText = new JTextField(20);
        idText.setBounds(100, 20, 165, 25);
        panel.add(idText);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 50, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 50, 165, 25);
        panel.add(nameText);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(10, 80, 80, 25);
        panel.add(descLabel);

        JTextField descText = new JTextField(20);
        descText.setBounds(100, 80, 165, 25);
        panel.add(descText);

        JLabel costLabel = new JLabel("Cost:");
        costLabel.setBounds(10, 110, 80, 25);
        panel.add(costLabel);

        JTextField costText = new JTextField(20);
        costText.setBounds(100, 110, 165, 25);
        panel.add(costText);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 140, 80, 25);
        panel.add(addButton);

        JTextField recordCount = new JTextField("0");
        recordCount.setBounds(100, 140, 165, 25);
        recordCount.setEditable(false);
        panel.add(recordCount);

        addButton.addActionListener(e -> {
            try (RandomAccessFile raf = new RandomAccessFile("ProductData.dat", "rw")) {
                raf.seek(raf.length()); // Move to the end of the file

                String id = Product.padString(idText.getText(), 6);
                String name = Product.padString(nameText.getText(), 35);
                String description = Product.padString(descText.getText(), 75);
                double cost = Double.parseDouble(costText.getText());

                raf.writeBytes(id);
                raf.writeBytes(name);
                raf.writeBytes(description);
                raf.writeDouble(cost);

                int count = Integer.parseInt(recordCount.getText());
                recordCount.setText(String.valueOf(count + 1));

                idText.setText("");
                nameText.setText("");
                descText.setText("");
                costText.setText("");
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error saving product: " + ex.getMessage());
            }
        });
    }
}
