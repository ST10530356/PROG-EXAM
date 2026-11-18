/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammy
 */
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

public class ProductForm extends JFrame {

    private JTextField txtCustomerName = new JTextField();
    private JComboBox<String> cmbProduct = new JComboBox<>(new String[]{
        "Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"});
    private JTextField txtPrice = new JTextField();
    private JButton btnProcess = new JButton("PROCESS");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu mnuFile = new JMenu("File");
    private JMenu mnuTools = new JMenu("Tools");
    private JMenuItem mnuExit = new JMenuItem("Exit");
    private JMenuItem mnuProcess = new JMenuItem("Process");
    private JMenuItem mnuClear = new JMenuItem("Clear");

    public ProductForm() {
        setTitle("Product App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 250);
        setLocationRelativeTo(null);

        // Menu system (Q2.4)
        mnuFile.add(mnuExit);
        mnuTools.add(mnuProcess);
        mnuTools.add(mnuClear);
        menuBar.add(mnuFile);
        menuBar.add(mnuTools);
        setJMenuBar(menuBar);

        // Form layout (Q2.1 / Q2.2)
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Customer Name:"));
        panel.add(txtCustomerName);
        panel.add(new JLabel("Select Product:"));
        panel.add(cmbProduct);
        panel.add(new JLabel("Enter Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel(""));
        panel.add(btnProcess);
        add(panel);

        // Events
        btnProcess.addActionListener(e -> processSale());
        mnuProcess.addActionListener(e -> processSale());
        mnuClear.addActionListener(e -> clearForm());
        mnuExit.addActionListener(e -> System.exit(0));
    }

    // Q2.3: same logic used by the button and the Process menu item
    private void processSale() {
        try {
            String customer = txtCustomerName.getText().trim();
            String product = cmbProduct.getSelectedItem().toString();

            if (customer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the customer name.");
                return;
            }

            double price = Double.parseDouble(txtPrice.getText().trim());

            IProductCalculations calc = new ProductCalculations(price);
            double vat = calc.GetVat();
            double commission = calc.GetCommission();
            double total = calc.GetTotal();

            JOptionPane.showMessageDialog(this,
                    "Customer Name: " + customer + "\n"
                    + "Product: " + product + "\n"
                    + "VAT: R " + vat + "\n"
                    + "COMMISSION: R " + commission + "\n"
                    + "TOTAL: R " + total);

            try (PrintWriter out = new PrintWriter(new FileWriter("data.txt", true))) {
                out.println("DATA LOG");
                out.println("**********************************");
                out.println("Customer Name: " + customer);
                out.println("Product: " + product);
                out.println("VAT: R " + vat);
                out.println("COMMISSION: R " + commission);
                out.println("TOTAL: R " + total);
                out.println("**********************************");
                out.println();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Could not save to data.txt");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.");
        }
    }

    // Q2.4: clear text boxes and reset combo box to index 0
    private void clearForm() {
        txtCustomerName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductForm().setVisible(true));
    }
}
