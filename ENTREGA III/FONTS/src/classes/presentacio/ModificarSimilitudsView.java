package classes.presentacio;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Classe que permet a l'usuari modificar alguna de les similituds d'un producte.
 * Mostra les similituda del producte amb tots els altres productes de l'aplicació.
 */
public class ModificarSimilitudsView extends JFrame {
    private final String producteA;
    private final LinkedHashMap<String, Double> similituds; //Nombres reales y sus grados de similitud
    private final GestionarProdView gestionarProdView;

    /**
     * Crea una nova instancia de ModificarSimilitudsView.
     * 
     * @param producteA Nom del producte del que volem modificar alguna similitud.
     * @param similituds Graus de similitud del producte.
     * @param gestionarProdView Vista anterior en la que es crida a aquesta pantalla.
     */
    public ModificarSimilitudsView(String producteA, LinkedHashMap<String, Double> similituds, GestionarProdView gestionarProdView) {
        this.producteA = producteA;
        this.similituds = similituds;
        this.gestionarProdView = gestionarProdView;
        initUI();
    }

    /**
     * En aquest mètode s'inicialitzen els components de la vista.
     */
    private void initUI() {
        setTitle("Modificar Similituds de " + producteA);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear el modelo y lista para mostrar similitudes con nombres reales
        DefaultListModel<String> listModel = new DefaultListModel<>();
        similituds.forEach((producteB, grau) -> 
            listModel.addElement(producteB + " - " + grau)
        );

        JList<String> similitudsList = new JList<>(listModel);
        similitudsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(similitudsList);
        add(scrollPane, BorderLayout.CENTER);

        // Crear botón para modificar similitudes
        JButton modificarButton = new JButton("Modificar Similitud");
        modificarButton.addActionListener(e -> {
            int selectedIndex = similitudsList.getSelectedIndex();
            if (selectedIndex >= 0) {
                String selected = similitudsList.getSelectedValue();
                String[] parts = selected.split(" - ");
                String producteB = parts[0];
                double grauActual = Double.parseDouble(parts[1]);

                String novaSimilitudStr = JOptionPane.showInputDialog(this,
                        "Introdueix un nou grau de similitud (entre 0 i 1):",
                        grauActual);

                try {
                    double novaSimilitud = Double.parseDouble(novaSimilitudStr);
                    if (novaSimilitud >= 0 && novaSimilitud <= 1) {
                        // Actualizar la similitud en el controlador
                        gestionarProdView.getVistaPrincipal().actualitzaSimilituds(producteA, producteB, novaSimilitud);

                        // Actualizar la lista en la ventana
                        listModel.set(selectedIndex, producteB + " - " + novaSimilitud);
                        similituds.put(producteB, novaSimilitud);

                        JOptionPane.showMessageDialog(this, "Similitud actualitzada correctament.");
                    } else {
                        JOptionPane.showMessageDialog(this, "El valor ha de ser entre 0 i 1.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Introdueix un valor vàlid.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al modificar la similitud: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una similitud per modificar.");
            }
        });

        // Crear panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modificarButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
