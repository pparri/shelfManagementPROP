package classes.presentacio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Panell que mostra la distribució del prestatge, un cop recent generat el prestatge.
 * Aquest panell proporciona a l'usuari una interfície en la que pot escollir entre Guardar el prestatge
 *  o Modificar el prestatge, no pot fer res més. Si vol sortir d'aquesta finestra, primer ha de guardar el prestatge.
 */
public class PrestatgeView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal; 
    private ArrayList<String> distPrestatge; 

    /**
     * Crea una nova instancia de PrestatgeView.
     *
     * @param vistaPrincipal La vista principal de l'aplicació.
     */
    public PrestatgeView(PrincipalView vistaPrincipal) {
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
        gbc.weightx = 1.0; // Para que se expandan horizontalmente si es necesario
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
            label.setForeground(Color.WHITE); // Texto blanco para contraste
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

        JButton btGuardar = new JButton("Guardar Prestatge");
        btGuardar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                 vistaPrincipal.guardarDistribucio(distPrestatge);
                }
                catch (Exception xe) 
                {
                    System.out.println("Error: " + xe.getMessage());
                }
                vistaPrincipal.showPanel("MainView");
            }
        });

        JButton btModificar = new JButton("Modificar Prestatge");
        btModificar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.showPanel("ModificarPrestatgeView");
            }
        });

        panelInferior.add(btGuardar);
        panelInferior.add(btModificar);

        // Añadimos el panel inferior a la parte inferior del layout
        add(panelInferior, BorderLayout.SOUTH);
    } // </editor-fold>//GEN-END:initComponents

}