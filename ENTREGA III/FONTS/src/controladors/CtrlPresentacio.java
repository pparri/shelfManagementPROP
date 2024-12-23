package controladors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

import classes.presentacio.PrincipalView;

/**
 * Representa el controlador de presentació.
 * 
 * Proporciona mètodes per gestionar la interfície de l'aplicació, i connectar-se amb domini, i aquest amb persistència.
 */
public class CtrlPresentacio {

    private static CtrlPresentacio instancia;

    private static CtrlDomini ctrlDomini;
    private final PrincipalView vistaPrincipal;

    /**
     * Constructor de la classe CtrlPresentacio.
     */
    public CtrlPresentacio(boolean bool) {
        boolean first = bool;
        ctrlDomini = CtrlDomini.getInstancia();        
        ctrlDomini.setCtrlPresentacio(this);
        this.vistaPrincipal = new PrincipalView(this);
        if (first) vistaPrincipal.setVisible(true);
        else vistaPrincipal.setVisible(false);
    }

    /**
     * Retorna la instancia del controlador de presentació.
     *
     * @return CtrlPresentacio.
     */
    public static CtrlPresentacio getInstancia() {
        if (instancia == null) instancia = new CtrlPresentacio(true);
        return instancia;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CtrlPresentacio(true);
            }
        });
    }

    /**
     * Mètode per generar el prestatge mitjançant l'algorisme 2-Aprox.
     * @throws Exception
     */
    public static void generarPrestatgeDosAprox() throws Exception {
        ctrlDomini.generarPrestatgeDosAprox();
    }

    /**
     * Mètode per generar el prestatge mitjançant l'algorisme de força bruta.
     * @throws Exception
     */
    public static void generarPrestatgeForcaBruta() throws Exception {
        ctrlDomini.generarPrestatgeForcaBruta();
    }
    
    /**
     * Mètode per guardar la nova distribució del prestatge si aquesta és modificada des de l'aplicació.
     * @param prestatge Nova distribució del prestatge.
     * @throws Exception
     */
    public static void guardarDistPrest(ArrayList<String> prestatge) throws Exception{
        ctrlDomini.guardarPrestatge(prestatge);
    }
    

    /**
     * Mètode per obtenir les dades de la cistella.
     * 
     * @return LinkedHashMap<String, ArrayList<Double>>
     * @throws IOException
     */
    public static LinkedHashMap<String, ArrayList<Double>> llegirCistella() throws IOException {
        LinkedHashMap<String, ArrayList<Double>> mapaCistella = ctrlDomini.consultaCistella();
        return mapaCistella;
    } 

    /**
     * Mètode per afegir un nou producte a l'aplicació.
     * 
     * @param id Nom del producte nou.
     * @param similituds Similituds del nou producte.
     * @return Integer.
     * @throws Exception
     */
    public static void carregarProducte(String id, Double[] similituds) throws Exception {
        ctrlDomini.afegirProducteSingle(id, similituds);
    }

    /**
     * Mètode per eliminar un producte de l'aplicació.
     * 
     * @param id Nom del producte que es vol eliminar de l'aplicació.
     * @return Integer.
     * @throws Exception
     */
    public static void eliminarProducte(String id) throws Exception {
        ctrlDomini.eliminarProducte(id);
    }

    /**
     * Mètode per modificar el grau de similitud dels productes.
     * 
     * @param producteA Nom del primer producte del qual volem modificar un dels seus graus de similitud.
     * @param producteB Nom del segon producte del qual volem modificar un dels seus graus de similitud.
     * @param sim Similitud nova que modifica a la similituds entre el primer i el segon producte dels quals volem modificar la similitud.
     * @throws Exception
     */
    public static void modificarGrauSimil(String producteA, String producteB, double sim) throws Exception {
        ctrlDomini.modificarGrauSimil(producteA, producteB, sim);
    }

    /**
     * Aquest mètode serveix per modificar la distribució del prestatge des de l'aplicació.
     * 
     * @param producteA Nom del primer producte del qual es vol canviar la posició amb un altre.
     * @param producteB Nom del segon producte del qual es vol canviar la posició amb el primer.
     */
    public static void modificarDistPrestatge(String producteA, String producteB) {
        try {
            ctrlDomini.modificarPrestatge(producteA, producteB);
        } catch (IOException e) {
            System.err.println("Error al modificar el prestatge: " + e.getMessage());
        }
    }

    /**
     * Mètode que serveix per poder eliminar el prestatge des de l'aplicació.
     * @throws IOException
     */
    public static void eliminaPrestatge() throws IOException {
        ctrlDomini.eliminarDistPrestatge();
    }

    /**
     * Mètode per obtenir els productes guardats a la base de dades.
     * 
     * @return LinkedHashMap<String, ArrayList<Double>>
     * @throws Exception
     */
    public static LinkedHashMap<String, ArrayList<Double>> obtenerProductos() throws Exception
    {
        return ctrlDomini.consultaCistella();
    }

    /**
     * Aquest mètode serveix per obtenir les dades del prestatge.
     * 
     * @return ArrayList<String>
     */
    public static ArrayList<String> obtenirProdsPrestatge() {
        ArrayList<String> prestatge = ctrlDomini.consultarPrestatge();
        return prestatge;
    }

    /**
     * Mètode que serveix per carregar un arxiu d'un producte.
     * 
     * @param filePath path del nou fitxer.
     * @throws Exception
     */
    public static void carregaProducteArxiu(String filePath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Leer el número de productos
            int numProductos = Integer.parseInt(br.readLine().trim());

            // Inicializar arrays para nombres y similitudes
            String[] ids = new String[numProductos];
            Double[][] similituds = new Double[numProductos][numProductos];

            // Procesar el archivo línea por línea
            for (int i = 0; i < numProductos; i++) {
                // Leer el nombre del producto
                ids[i] = br.readLine().trim();

                // Leer la fila de similitudes
                String lineaSimilitudes = br.readLine().trim();
                String[] valores = lineaSimilitudes.split(" ");
                if (valores.length != numProductos) {
                    throw new Exception("El número de similitudes no coincide con el número de productos.");
                }

                // Convertir los valores a Double[]
                for (int j = 0; j < valores.length; j++) {
                    similituds[i][j] = Double.parseDouble(valores[j]);
                }
            }

            // Llamar a la función afegirProducteMultiple de CtrlDomini
            ctrlDomini.afegirProducteMultiple(ids, similituds, numProductos);
        } catch (IOException e) {
            throw new Exception("Error al leer el archivo: " + filePath, e);
        } catch (NumberFormatException e) {
            throw new Exception("Error de formato en el archivo. Asegúrate de que las similitudes son números.", e);
        }
    }

    /**
     * Surt de l'aplicacio.
     */
    public void sortir() {
        ctrlDomini.sortir();
    } 

}
