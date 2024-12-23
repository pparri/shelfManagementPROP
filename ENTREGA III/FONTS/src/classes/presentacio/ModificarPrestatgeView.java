package classes.presentacio;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Panell que permet a l'usuari modificar la distribució del prestatge.
 * Es poden seleccionar dos productes i intercanviar-los la posició en el prestatge.
 */
public class ModificarPrestatgeView extends javax.swing.JPanel {

    private final PrincipalView vistaPrincipal; 
    private ArrayList<String> distPrestatge; 

    private ArrayList<ItemPanel> itemPanels;

    private String vistaAnterior;

    //Color per a la selecció i la no selecció
    private final Color baseColor = new Color(150,110,105);
    private final Color selectedColor = new Color(200,150,145);

    /**
     * Crea una nova instancia de ModificarPrestatgeView.
     * 
     * @param vistaPrincipal La vista principal de l'aplicació.
     * @param vistaAnterior La vista anterior de la qual es pot cridar a aquesta pantalla.
     */
    public ModificarPrestatgeView(PrincipalView vistaPrincipal, String vistaAnterior) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaAnterior = vistaAnterior;
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
        setLayout(new BorderLayout());
        setBackground(baseColor);
        
        // Panel central con GridBagLayout
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0;
        panelCentral.add(Box.createVerticalGlue(), gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 0.0; 
        JPanel itemsRow = new JPanel();
        itemsRow.setLayout(new BoxLayout(itemsRow, BoxLayout.X_AXIS));
        itemsRow.setBackground(baseColor);

        itemPanels = new ArrayList<>();
        for (String s : distPrestatge) {
            ItemPanel ip = new ItemPanel(s);
            itemPanels.add(ip);
            itemsRow.add(ip);
        }
        
        panelCentral.add(itemsRow, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 1.0; 
        panelCentral.add(Box.createVerticalGlue(), gbc);

        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getViewport().setBackground(baseColor);

        add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setBackground(baseColor);

        JButton btTornarEnrere = new JButton("Tornar");
        btTornarEnrere.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btTornarEnrere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.showPanel(vistaAnterior);
            }
        });

        JButton btModificar = new JButton("Modificar Distribució");
        btModificar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> seleccionados = new ArrayList<>();
                for (ItemPanel ip : itemPanels) {
                    if (ip.isSelected()) {
                        seleccionados.add(ip.getItemName());
                    }
                }
                
                if (seleccionados.size() == 2) {
                    // Pasamos los dos ítems seleccionados a la vistaPrincipal
                    vistaPrincipal.modificarDistribucio(seleccionados.get(0), seleccionados.get(1));

                    for (ItemPanel ip : itemPanels) {
                        if (ip.isSelected()) {
                            seleccionados.remove(ip);
                        }
                    }          
                    vistaPrincipal.showPanel("ModificarPrestatgeView");

                } else {
                    JOptionPane.showMessageDialog(
                        ModificarPrestatgeView.this,
                        "Has de seleccionar exactament dos productes per tal de modificar el prestatge.",
                        "Atenció",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        panelInferior.add(btTornarEnrere);
        panelInferior.add(btModificar);

        add(panelInferior, BorderLayout.SOUTH);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Aquesta classe serveix per representar cada producte.
     */
    private class ItemPanel extends JPanel {
        private String itemName;
        private boolean selected;

        public ItemPanel(String name) {
            this.itemName = name;
            this.selected = false;
            setBackground(baseColor);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel label = new JLabel(name);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(Color.WHITE);
            add(label);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggleSelection();
                }
            });
        }

        public String getItemName() {
            return itemName;
        }

        public boolean isSelected() {
            return selected;
        }

        private void toggleSelection() {
            // Contamos cuántos están seleccionados actualmente
            int currentlySelected = 0;
            for (ItemPanel ip : itemPanels) {
                if (ip.isSelected()) currentlySelected++;
            }

            if (selected) {
                // Si ya está seleccionado, lo deseleccionamos
                selected = false;
                setBackground(baseColor);
            } else {
                // Si no está seleccionado, comprobamos si ya hay 2 seleccionados
                if (currentlySelected < 2) {
                    selected = true;
                    setBackground(selectedColor);
                } else {
                    // Ya hay 2 seleccionados, no permitimos más
                    JOptionPane.showMessageDialog(
                        ModificarPrestatgeView.this,
                        "Només pots seleccionar dos productes.",
                        "Límit assolit",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
            repaint();
        }
    }
}
