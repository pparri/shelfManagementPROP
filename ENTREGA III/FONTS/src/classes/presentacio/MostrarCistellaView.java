package classes.presentacio;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.*;

/**
 * Panell per a mostrar els productes que estan guardats en l'aplicació.
 * Mostra tots els productes que hi ha a la cistella.
 */
public class MostrarCistellaView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal;
    private LinkedHashMap<String, ArrayList<Double>> dadesCistella;

    /**
     * Crea una nova instancia de MostrarCistellaView.
     * 
     * @param vistaPrincipal La vista principal de l'aplicació.
     */
    public MostrarCistellaView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        try {
            dadesCistella = vistaPrincipal.obtenirCistella();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al obtenir la cistella: " + e.getMessage());
        }

        initComponents();
    }

    /**
     * Mètode que actualitza els components de la vista.
     * 
     * @throws IOException
     */
    public void actualizarVista() throws IOException
    {
        dadesCistella = vistaPrincipal.obtenirCistella();
        this.removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(150, 110, 105));

        // Panel superior con el botón Tornar a la izquierda
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(150, 110, 105));

        JButton bTornar = new JButton("Tornar");
        bTornar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.showPanel("MainView");
            }
        });

        panelSuperior.add(bTornar);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para mostrar los datos
        // Primero obtenemos la lista de keys
        java.util.List<String> keys = new ArrayList<>(dadesCistella.keySet());

        int numKeys = keys.size();
        int columns = 4;
        int rows = (int) Math.ceil(numKeys / (double) columns);
        if (rows == 0) rows = 1; // En caso de que no haya keys

        JPanel panelCentral = new JPanel(new GridLayout(rows, columns, 10, 10));
        panelCentral.setBackground(new Color(150, 110, 105));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Crear labels sólo con las keys
        for (String key : keys) {
            JLabel label = new JLabel(key);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panelCentral.add(label);
        }

        // Colocamos el panel central en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelCentral);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
}
