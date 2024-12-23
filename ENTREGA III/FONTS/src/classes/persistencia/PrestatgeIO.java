package classes.persistencia;

import classes.domini.Prestatge;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Gestor de persistència per a prestatges.
 * 
 * Proporciona funcionalitats per guardar, carregar i eliminar prestatges
 *      a l'emmagatzematge persistent en format JSON.
 */
public class PrestatgeIO {

    private static final Gson gson = new Gson();
    public static final String BASE_DIRECTORY = "data/prestatge"; // Defineix el directori base per als prestatges

    static {
        File dir = new File(BASE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Desa un prestatge a un fitxer JSON.
     * 
     * @param prestatge El prestatge que es vol desar.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void escriure(Prestatge prestatge) throws IOException {
        String filepath = BASE_DIRECTORY + "/prestatge.json";
        File file = new File(filepath);
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(prestatge, writer);
        }
    }

    /**
     * Carrega un prestatge des d'un fitxer JSON.
     * 
     * @return El prestatge carregat.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static Prestatge llegir() throws IOException {
        String filepath = BASE_DIRECTORY + "/prestatge.json";
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Prestatge.class);
        }
    }

    /**
     * Elimina un prestatge esborrant el seu fitxer JSON.
     * 
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void eliminar() throws IOException {
        String filepath = BASE_DIRECTORY + "/prestatge.json";
        File file = new File(filepath);
     try {
        if (file.exists()) {
            if (!file.delete()) {
                JOptionPane.showMessageDialog(null, "No s'ha pogut eliminar el prestatge.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Prestatge eliminat correctament.", "Éxit", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El prestatge no està guardat i no es pot eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error inesperat: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

    /**
     * Modifica un prestatge a partir de dos productes.
     * 
     * @param producteA Identificador del primer producte.
     * @param producteB Identificador del segon producte.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void modificarPrestatge(String producteA, String producteB) throws IOException {
        String filepath = BASE_DIRECTORY + "/prestatge.json";

        Prestatge prestatge;
        try (FileReader reader = new FileReader(filepath)) {
            prestatge = gson.fromJson(reader, Prestatge.class);
        }

        if (prestatge == null || prestatge.getDistribucio() == null) {
            throw new IOException("No s'ha pogut llegir el prestatge o és buit.");
        }
        ArrayList<String> prods = prestatge.getDistribucio();

        int indexA = prods.indexOf(producteA);
        int indexB = prods.indexOf(producteB);

        if (indexA == -1 || indexB == -1) {
            throw new IllegalArgumentException("Un o ambdós productes no existeixen al prestatge.");
        }

        // Intercambiar posiciones
        prods.set(indexA, producteB);
        prods.set(indexB, producteA);

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(prestatge, writer);
        }
    }
}
