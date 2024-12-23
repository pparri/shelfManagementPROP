package classes.presentacio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panell per a gestionar els productes de l'aplicació.
 * Aquest panell proporciona una interfície d'usuari en la que es poden veure tots els productes.
 * Es poden eliminar, afegir o modificar productes.
 */
public class GestionarProdView extends javax.swing.JPanel {

    private static PrincipalView vistaPrincipal;
    private DefaultListModel<String> productosModel;  //Model de llista per a mostrar els productes.

    /**
     * Constructor de la vista per a gestionar productes.
     *
     * @param vistaPrincipal La vista principal de l'aplicació.
     */
    public GestionarProdView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        initComponents();
        mostrarProductes();
    }

    /**
     * Mètode que actualitza els components de la vista.
     * 
     * @throws IOException
     */
    public void actualizarVista() throws IOException
    {
        this.removeAll();
        initComponents();
        mostrarProductes();
        revalidate();
        repaint();
    }

    /**
     * Mètode que retorna una instancia de la vista Principal.
     * 
     * @return PrincipalView
     */
    public PrincipalView getVistaPrincipal() {

        return vistaPrincipal;
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Establecer el layout
        setLayout(new BorderLayout());
        setBackground(new Color(150, 110, 105));

        // Crear los botones
        JButton btnAñadir1Producto = new JButton("Afegir 1 Producte");
        JButton btnAñadirArchivo = new JButton("Afegir Arxiu Prod");
        JButton btnVolver = new JButton("Tornar");

        // Configurar los botones
        Dimension buttonSize = new Dimension(150, 30);
        btnAñadir1Producto.setPreferredSize(buttonSize);
        btnAñadirArchivo.setPreferredSize(buttonSize);
        btnVolver.setPreferredSize(buttonSize);

        // Crear un panel para los botones (alineados en la parte inferior y centrados)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alineación centrada
        panelBotones.setBackground(new Color(150, 110, 105));  // Fondo del panel con el mismo color que el fondo principal
        panelBotones.add(btnVolver);
        panelBotones.add(btnAñadir1Producto);
        panelBotones.add(btnAñadirArchivo);

        // Agregar el panel de botones a la parte inferior
        add(panelBotones, BorderLayout.SOUTH);

        // Crear un panel para mostrar la lista de productos
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BorderLayout());

        // Lista para mostrar los productos
        productosModel = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(productosModel);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Selección de un solo producto
        listaProductos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String productoSeleccionado = listaProductos.getSelectedValue();
                    if (productoSeleccionado != null) {
                        manejarProductoSeleccionado(productoSeleccionado);  // Llamar al método para manejar la selección
                    }
                }
            }
        });

        // Añadir scroll vertical a la lista de productos
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Siempre mostrar barra de desplazamiento vertical
        panelProductos.add(scrollPane, BorderLayout.CENTER);
        add(panelProductos, BorderLayout.CENTER);

        // Manejo de eventos de los botones
        btnAñadir1Producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AfegirProdView afegirProdView = new AfegirProdView(vistaPrincipal);
                afegirProdView.setVisible(true);
            }
        });

        btnAñadirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarArchivoProductos();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.showPanel("MainView"); //Volver a la vista principal
            }
        });
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Aquest mètode mostra els productes que tenim guardats en l'aplicació.
     */
    private void mostrarProductes() {
        try {
            LinkedHashMap<String, ArrayList<Double>> mapaCistella = vistaPrincipal.obtenirCistella();
            for (Map.Entry<String, ArrayList<Double>> entry : mapaCistella.entrySet()) {
                productosModel.addElement(entry.getKey());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al obtenir la cistella: " + e.getMessage());
        }

    }

    /**
     * Aquest mètode realitza la interacció amb el producte, ja sigui modificar, eliminar, tornar...
     * 
     * @param productoSeleccionado Nom del producte seleccionat en el moment que volem dur a terme algo amb ell.
     */
    private void manejarProductoSeleccionado(String productoSeleccionado) {
        String[] opciones = {"Modificar", "Eliminar", "Tornar"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "Què desitges fer amb el producte: " + productoSeleccionado + "?",
                "Opcions de Producte",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == 0) {
            modificarProducto(productoSeleccionado);
        } else if (seleccion == 1) {
            eliminarProducto(productoSeleccionado);
        }
    }

    /**
     * Aquest mètode afegeix un o més productes a l'aplicació, per mitjà d'un fitxer que està guardat a l'ordinador.
     */
    private void agregarArchivoProductos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar arxiu de productes");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arxius de text (*.txt)", "txt"));

        int result = fileChooser.showOpenDialog(this);  // Abrir el dialogo

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Llamar al controlador para cargar productos desde el archivo
                vistaPrincipal.carregaArxiu(filePath);
                JOptionPane.showMessageDialog(this, "Arxiu seleccionat: " + filePath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al carregar productes des de l'arxiu: " + ex.getMessage());
            }
        }
    }

    /**
     * Aquest mètode modifica el producte que seleccionem en l'aplicació.
     * 
     * @param productoSeleccionado Nom del producte que volem modificar.
     */
    private void modificarProducto(String productoSeleccionado) {
        try {
            // Obtener el mapa de productos desde la cistella
            LinkedHashMap<String, ArrayList<Double>> mapaCistella = vistaPrincipal.obtenirCistella();
    
            // Obtener las similitudes del producto seleccionado
            ArrayList<Double> similitudsList = mapaCistella.get(productoSeleccionado);
    
            if (similitudsList == null || similitudsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hi ha similituds disponibles per al producte seleccionat.");
                return;
            }
    
        // Obtener todos los productos en orden
        List<String> productes = new ArrayList<>(mapaCistella.keySet());
        // Índice real
        int selectedIndex = productes.indexOf(productoSeleccionado);

        LinkedHashMap<String, Double> similituds = new LinkedHashMap<>();
        for (String producteB : productes) {
            if (!producteB.equals(productoSeleccionado)) {
                int indexB = productes.indexOf(producteB);     
                similituds.put(producteB, similitudsList.get(indexB));
            }
        }
        //Es mostren correctament
            // Abrir la ventana de modificación
            new ModificarSimilitudsView(productoSeleccionado, similituds, this);
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al carregar les similituds del producte: " + ex.getMessage());
        }
    }


    /**
     * Aquest mètode elimina el producte que seleccionem de l'aplicació.
     * 
     * @param productoSeleccionado Nom del producte que volem eliminar.
     */
    private void eliminarProducto(String productoSeleccionado) {

        UIManager.put("OptionPane.yesButtonText", "Si");
        UIManager.put("OptionPane.noButtonText", "No");

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "Estàs segur que desitges eliminar el producte: " + productoSeleccionado + "?",
                "Confirmar eliminació",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                vistaPrincipal.eliminarProducto(productoSeleccionado); // Llamar al controlador para eliminar el producto
                productosModel.removeElement(productoSeleccionado);  // Eliminar de la lista visual
                JOptionPane.showMessageDialog(this, "Producte eliminat amb èxit");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No s'ha pogut eliminar el producte: " + ex.getMessage());
            }
        }
    }
}
