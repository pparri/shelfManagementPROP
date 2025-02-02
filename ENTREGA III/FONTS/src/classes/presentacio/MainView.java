package classes.presentacio;

import java.awt.*;
import javax.swing.*;

/**
 * Classe que representa la vista principal de l'aplicacio.
 *
 */
public class MainView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal; 

    /**
     * Crea una nova instancia de MainView i inicialitza els components.
     *
     * @param vistaPrincipal La vista principal de l'aplicacio.
     */
    public MainView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        // Establecer el fondo del MainView
        setBackground(new Color(150, 110, 105)); // Fondo general de la vista

        // Usamos un BorderLayout para el panel principal
        setLayout(new BorderLayout());

        // Panel superior para el botón de cerrar
        JPanel panelTancar = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alineación a la izquierda
        panelTancar.setBackground(new Color(150, 110, 105)); // Fondo del panel superior para que coincida con el fondo general

        // Botón de cerrar (rojo clarito)
        JButton bTancar = new JButton("Tancar");
        bTancar.setBackground(new Color(255, 102, 102)); // Rojo clarito
        bTancar.setForeground(Color.WHITE);
        bTancar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bTancar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btancarActionPerformed(evt);
            }
        });
        panelTancar.add(bTancar);

        // Crear el panel con GridBagLayout
        // Panel central para los botones
        JPanel panelBotons = new JPanel(new GridBagLayout());
        panelBotons.setBackground(new Color(150, 110, 105)); // Fondo del panel central

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espaciado entre los botones
        gbc.fill = GridBagConstraints.BOTH; // No expandir los botones
        gbc.anchor = GridBagConstraints.CENTER;

        // Crear los botones principales
        JButton btGestionarProductes = new JButton("Gestionar Productes");
        JButton btGenerarPrestatge = new JButton("Generar Prestatge");
        JButton btMostrarCistella = new JButton("Mostrar Cistella");
        JButton btMostrarPrestatge = new JButton("Mostrar Prestatge");

        // Configurar la fuente y tamaño de los botones
        Font btFont = new Font("Segoe UI", Font.PLAIN, 14);
        btGestionarProductes.setFont(btFont);
        btGenerarPrestatge.setFont(btFont);
        btMostrarCistella.setFont(btFont);
        btMostrarPrestatge.setFont(btFont);

        Dimension botonDimension = new Dimension(275, 125);
        btGestionarProductes.setPreferredSize(botonDimension);
        btGenerarPrestatge.setPreferredSize(botonDimension);
        btMostrarCistella.setPreferredSize(botonDimension);
        btMostrarPrestatge.setPreferredSize(botonDimension);

        btGestionarProductes.setMinimumSize(botonDimension);
        btGenerarPrestatge.setMinimumSize(botonDimension);
        btMostrarCistella.setMinimumSize(botonDimension);
        btMostrarPrestatge.setMinimumSize(botonDimension);

        // Añadir botones a la primera fila
        gbc.gridx = 0; // Primera columna
        gbc.gridy = 0; // Primera fila
        panelBotons.add(btGestionarProductes, gbc);

        gbc.gridx = 1; // Segunda columna
        gbc.gridy = 0; // Primera fila
        panelBotons.add(btGenerarPrestatge, gbc);

        // Añadir botones a la segunda fila
        gbc.gridx = 0; // Primera columna
        gbc.gridy = 1; // Segunda fila
        panelBotons.add(btMostrarCistella, gbc);

        gbc.gridx = 1; // Segunda columna
        gbc.gridy = 1; // Segunda fila
        panelBotons.add(btMostrarPrestatge, gbc);

        // Agregar el panel de botones al MainView
        add(panelBotons, BorderLayout.CENTER);


        // Agregar los botones al panel
        btGestionarProductes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btgestionarProductesActionPerformed(evt);
            }
        });

        btGenerarPrestatge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btgenerarPrestatgeActionPerformed(evt);
            }
        });

        btMostrarCistella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmostrarCistellaActionPerformed(evt);
            }
        });

        btMostrarPrestatge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmostrarPrestatgeActionPerformed(evt);
            }
        });

        //Agregar los paneles al MainView
        add(panelTancar, BorderLayout.NORTH);   
        add(panelBotons, BorderLayout.CENTER); 
    } // </editor-fold>//GEN-END:initComponents


    private void btancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btancarActionPerformed
        vistaPrincipal.dispose();
        System.exit(0);
    }//GEN-LAST:event_btancarActionPerformed

    private void btgestionarProductesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btgestionarProductesActionPerformed
        vistaPrincipal.showPanel("GestionarProdView");
    }//GEN-LAST:event_btgestionarProductesActionPerformed

    private void btgenerarPrestatgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btgenerarPrestatgeActionPerformed
        vistaPrincipal.showPanel("GenerarPrestView");
    }//GEN-LAST:event_btgenerarPrestatgeActionPerformed

    private void btmostrarCistellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmostrarCistellaActionPerformed
        vistaPrincipal.showPanel("MostrarCistellaView");
    }//GEN-LAST:event_btmostrarCistellaActionPerformed

    private void btmostrarPrestatgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmostrarPrestatgeActionPerformed
        vistaPrincipal.showPanel("MostrarPrestatgeView");
    }//GEN-LAST:event_btmostrarPrestatgeActionPerformed
}
