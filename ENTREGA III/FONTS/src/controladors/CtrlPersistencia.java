package controladors;

import classes.domini.Prestatge;
import classes.domini.Producte;
import classes.persistencia.PrestatgeIO;
import classes.persistencia.ProducteIO;
import java.io.IOException;

/**
 * Controlador de persistència.
 * 
 * Proporciona mètodes per gestionar l'emmagatzematge i la recuperació de dades 
 *      relacionades amb els productes i prestatges.
 */
public class CtrlPersistencia {

    /**
     * Afegeix un producte a l'emmagatzematge persistent.
     * 
     * @param id Identificador del producte.
     * @param similituds Vector de similituds del producte amb altres productes.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void afegirProducte(String id, Double[] similituds) throws IOException {
        ProducteIO.afegirProducte(id, similituds);
    }

    /**
     * Carrega un producte de l'emmagatzematge persistent.
     * 
     * @param id Identificador del producte.
     * @return El producte carregat.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static Producte carregarProducte(String id) throws IOException {
        return ProducteIO.loadProducte(id);
    }

    /**
     * Desa un producte a l'emmagatzematge persistent.
     * 
     * @param producte El producte a desar.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void guardarProducte(Producte producte) throws IOException {
        ProducteIO.saveProducte(producte);
    }

    /**
     * Elimina un producte de l'emmagatzematge persistent.
     * 
     * @param id Identificador del producte que es vol eliminar.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void eliminarProducte(String id) throws IOException {
        ProducteIO.eliminarProducte(id);
    }

    /**
     * Modifica el grau de similitud entre dos productes en l'emmagatzematge persistent.
     * 
     * @param producteA Identificador del primer producte.
     * @param producteB Identificador del segon producte.
     * @param sim Nou grau de similitud.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void modificarGrauSimil(String producteA, String producteB, double sim) throws IOException {
        ProducteIO.modificarGrauSimil(producteA, producteB, sim);
    }

    /**
     * Desa un prestatge a l'emmagatzematge persistent.
     * 
     * @param prestatge El prestatge a desar.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void guardarPrestatge(Prestatge prestatge) throws IOException {
        PrestatgeIO.escriure(prestatge);
    }

    /**
     * Carrega un prestatge de l'emmagatzematge persistent.
     * 
     * @return El prestatge carregat.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static Prestatge carregarPrestatge() throws IOException {
        return PrestatgeIO.llegir();
    }

    /**
     * Modifica un prestatge en l'emmagatzematge persistent.
     * 
     * @param producteA Identificador del primer producte.
     * @param producteB Identificador del segon producte.
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void modificarPrestatge(String producteA, String producteB) throws IOException {
        PrestatgeIO.modificarPrestatge(producteA, producteB);
    }

    /**
     * Elimina el prestatge de l'emmagatzematge persistent.
     * 
     * @throws IOException Si hi ha un problema d'entrada/sortida.
     */
    public static void eliminarPrestatge() throws IOException {
        PrestatgeIO.eliminar();
    }
}