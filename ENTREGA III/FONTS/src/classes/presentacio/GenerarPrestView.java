package classes.presentacio;

import java.awt.*;
import javax.swing.*;

/**
 * Panell per a generar el prestatge en l'aplicació.
 * Aquest panell proporciona una interfície d'usuari en la que es veuen les dues opcions d'algorismes que proporcionem.
 * Es pot escollir quin algorisme utilitzar per generar el prestatge.
 */
public class GenerarPrestView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal; 

    /**
     * Crea una nova instancia de GenerarPrestView.
     *
     * @param vistaPrincipal La vista principal de l'aplicació.
     */
    public GenerarPrestView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        initComponents();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        // Mismo color de fondo que en la anterior
        setBackground(new Color(150, 110, 105));
        setLayout(new BorderLayout());

        // Panel superior con el botón de volver
        JPanel panelTornar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTornar.setBackground(new Color(150, 110, 105));

        // Botón Volver con la misma fuente que en la anterior (Segoe UI, PLAIN, 14)
        JButton bTornar = new JButton("Tornar");
        bTornar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bTornar.addActionListener(evt -> tornarActionPerformed(evt));
        panelTornar.add(bTornar);

        // Panel central con los dos botones, más pequeños y centrados
        JPanel panelBotons = new JPanel(new GridBagLayout());
        panelBotons.setBackground(new Color(150, 110, 105));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH; // No expandir los botones
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; 
        gbc.gridy = 0; 

        // Botones para los algoritmos, más pequeños
        JButton btAlgorisme1 = new JButton("Algorisme FB");
        JButton btAlgorisme2 = new JButton("Algorisme 2-Aprox");

        // Ajustar la fuente a algo más pequeño
        Font btFont = new Font("Segoe UI", Font.PLAIN, 14);
        btAlgorisme1.setFont(btFont);
        btAlgorisme2.setFont(btFont);

        Dimension botoDimensio = new Dimension(275, 125);
        btAlgorisme1.setPreferredSize(botoDimensio);
        btAlgorisme2.setPreferredSize(botoDimensio);

        btAlgorisme1.addActionListener(evt -> generarPrestatgeActionPerformed(evt, 1));
        btAlgorisme2.addActionListener(evt -> generarPrestatgeActionPerformed(evt, 2));

        // Añadimos el primer botón
        panelBotons.add(btAlgorisme1, gbc);

        // Añadimos el segundo 
        gbc.gridy = 1;
        panelBotons.add(btAlgorisme2, gbc);

        // Agregar los paneles al contenedor principal
        add(panelTornar, BorderLayout.NORTH);
        add(panelBotons, BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Mètode al que es crida quan l'usuari selecciona l'opció de Tornar enrere.
     * 
     */
    private void tornarActionPerformed(java.awt.event.ActionEvent evt) {
        vistaPrincipal.showPanel("MainView"); // Volver a la vista principal
    }

    /**
     * Mètode al que es crida quan l'usuari selecciona un dels dos algorismes per procedir a la generació del prestatge.
     * 
     * @param algorisme Integer que ens indica si l'usuari ha escollit un algorisme o l'altre.
     */
    private void generarPrestatgeActionPerformed(java.awt.event.ActionEvent evt, int algorisme) {
        try {
            // Llamamos al controlador de presentación para generar el prestatge
            vistaPrincipal.generarPrestatge(algorisme); // El algoritmo 1 o 2
            JOptionPane.showMessageDialog(this, "Prestatge generat amb èxit");
            vistaPrincipal.showPanel("PrestatgeView");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el prestatge: " + e.getMessage());
        }
    }
}
