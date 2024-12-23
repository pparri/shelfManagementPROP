package classes.presentacio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Panell per a mostrar el prestatge de l'aplicació.
 * Mostra la distribució dels productes en el prestatge.
 * Permet accedir a les funcionalitats de modificar i eliminar el prestatge.
 */
public class MostrarPrestatgeView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal; 
    private ArrayList<String> distPrestatge; 

    /**
     * Crea una nova instancia de MostrarPrestatgeView.
     *
     * @param vistaPrincipal La vista principal de l'aplicació.
     */
    public MostrarPrestatgeView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        distPrestatge = vistaPrincipal.obtenirPrestatge();
        initComponents();
    }

    /**
     * Mètode que actualitza els components de la vista.
     */
    public void actualitzarVista() 
    {
        distPrestatge = vistaPrincipal.obtenirPrestatge();

        this.removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Color de fondo base
        Color baseColor = new Color(150,110,105);

        // Establecemos el layout principal y el fondo
        setLayout(new BorderLayout());
        setBackground(baseColor);
        
        // Creamos el panel central con GridBagLayout
        JPanel panelCentral = new JPanel(new GridBagLayout());
        //panelCentral.setBackground(baseColor);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Fila superior (relleno vertical)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Para expandir horizontalmente si es necesario
        gbc.weighty = 1.0; // Espacio vertical arriba
        panelCentral.add(Box.createVerticalGlue(), gbc);
        
        // Fila de los items (centro)
        gbc.gridy = 1;
        gbc.weighty = 0.0; 
        // Aquí ponemos los items en horizontal
        JPanel itemsRow = new JPanel();
        itemsRow.setLayout(new BoxLayout(itemsRow, BoxLayout.X_AXIS));
        itemsRow.setBackground(baseColor);
        
        for (String s : distPrestatge) {
            JPanel itemPanel = new JPanel();
            itemPanel.setBackground(baseColor);
            itemPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel label = new JLabel(s);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(Color.WHITE); 
            itemPanel.add(label);

            itemsRow.add(itemPanel);
        }
        
        panelCentral.add(itemsRow, gbc);
        
        // Fila inferior (relleno vertical)
        gbc.gridy = 2;
        gbc.weighty = 1.0; // Espacio vertical abajo
        panelCentral.add(Box.createVerticalGlue(), gbc);

        // Creamos un JScrollPane con scroll horizontal.
        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Fondo del viewport
        scroll.getViewport().setBackground(baseColor);

        // Añadimos el scroll al centro
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con los botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setBackground(baseColor);

        JButton btTornarEnrere = new JButton("Tornar");
        btTornarEnrere.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btTornarEnrere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                vistaPrincipal.showPanel("MainView");
            }
        });

        JButton btModificarPrestatge = new JButton("Modificar Prestatge");
        btModificarPrestatge.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btModificarPrestatge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                vistaPrincipal.showPanel("ModificarPrestatgeView");
            }
        });

        JButton btEliminarPrestatge = new JButton("Eliminar Prestatge");
        btEliminarPrestatge.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btEliminarPrestatge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");

                int resposta = JOptionPane.showConfirmDialog(
                    MostrarPrestatgeView.this, 
                    "Estàs segur d'eliminar el prestatge?", 
                    "Confirmació", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (resposta == JOptionPane.YES_OPTION) {

                    try {
                        vistaPrincipal.eliminarPrestatge();
                        vistaPrincipal.showPanel("EliminarPrestatgeView");
                    } catch (IOException ex) {
                        /* 
                        JOptionPane.showMessageDialog(
                            MostrarPrestatgeView.this,
                            "Error al eliminar el prestatge: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );*/
                    }
                    vistaPrincipal.showPanel("MainView");
                } else {

                    vistaPrincipal.showPanel("MostrarPrestatgeView");
                }
            }
        });

        panelInferior.add(btTornarEnrere);
        panelInferior.add(btModificarPrestatge);
        panelInferior.add(btEliminarPrestatge);

        // Añadimos el panel inferior a la parte inferior del layout
        add(panelInferior, BorderLayout.SOUTH);
    } // </editor-fold>//GEN-END:initComponents

}