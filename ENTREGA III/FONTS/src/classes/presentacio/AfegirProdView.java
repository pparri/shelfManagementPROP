package classes.presentacio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe AfegirProdView que representa una vista per afegir un producte en l'aplicació.
 * Aquesta classe permet a l'usuari escriure el nom i les similituds del producte per després afegir-lo a l'aplicació.
 */
public class AfegirProdView extends JDialog {

    private JTextField productNameField;
    private JTextField similitudesField;
    private JButton saveButton, cancelButton;

    /**
     * En aquest mètode s'inicialitzen els components de la vista.
     * @param parent pantalla en la que ha de carregar la vista.
     */
    public AfegirProdView(JFrame parent) {
        super(parent, "Afegir Producte", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Configuración de layout
        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Nom del Producte:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Similituds (separades coma):"));
        similitudesField = new JTextField();
        inputPanel.add(similitudesField);

        add(inputPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Acciones de botones
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameField.getText();
                String[] similitudesArray = similitudesField.getText().split(",");
                try {
                    Double[] similitudes = new Double[similitudesArray.length];
                    for (int i = 0; i < similitudesArray.length; i++) {
                        similitudes[i] = Double.parseDouble(similitudesArray[i].trim());
                    }
                    ((PrincipalView) parent).carregaProducte(productName, similitudes);
                    JOptionPane.showMessageDialog(AfegirProdView.this, "Producte afegit amb èxit!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AfegirProdView.this, "Error en el format de similituds.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AfegirProdView.this, "Error al guardar el producte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
