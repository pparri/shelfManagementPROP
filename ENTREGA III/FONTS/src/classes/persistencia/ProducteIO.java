package classes.persistencia;

import classes.domini.Producte;
import controladors.CtrlDomini;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gestor de persistència per a productes.
 * 
 * Proporciona funcionalitats per guardar, carregar, afegir, eliminar i modificar productes
 *      a l'emmagatzematge persistent en format JSON.
 */
public class ProducteIO {

    private static final Gson gson = new Gson();
    public static final String BASE_DIRECTORY = "data/productes"; 
    private static final String ORDER_FILE = "data/product_order.json"; // arxiu que guarda l'ordre dels productes

    private static ProducteIO instancia;

    /**
     * Constructor de la classe Gestor_Producte.
     */
    private ProducteIO() {
    }

    /**
     * Retorna la instancia del gestor de producte.
     *
     * @return Gestor_Producte.
     */
    static public ProducteIO getInstancia() {
        if (instancia == null) instancia = new ProducteIO();
        return instancia;
    }
    
    /**
     * Funció que guarda l'ordre dels productes en un fitxer JSON 
     */
    public static void guardarOrdre(List<String> orden) throws IOException {
        String json = gson.toJson(orden);
        try {
            Files.write(Paths.get(ORDER_FILE), json.getBytes());
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Funció que carrega l'ordre dels productes
     */
    public static List<String> carregarOrdre() throws IOException {
        Path path = Paths.get(ORDER_FILE);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        String json = new String(Files.readAllBytes(path));
    
        Type listType = TypeToken.getParameterized(List.class, String.class).getType();
        List<String> orden = gson.fromJson(json, listType);
        
        return orden != null ? orden : new ArrayList<>();
    }
    
    /**
     * Llista tots els productes guardats en l'ordre
     * 
     * @return Lista de IDs de productes.
     * @throws IOException Si hi ha algun error al accedir al sistema de fitxer
     */
    public static List<String> LlistarProductes() throws IOException {
        List<String> listaProductes = new ArrayList<>();

        // Obtiene el directorio base
        Path dir = Paths.get(BASE_DIRECTORY);

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new IOException("El directori base no existeix o no es un directori: " + BASE_DIRECTORY);
        }

        // Filtra los archivos con extensión .json
        DirectoryStream.Filter<Path> filtroJson = entry -> {
            return Files.isRegularFile(entry) && entry.toString().endsWith(".json");
        };

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filtroJson)) {
            for (Path entry : stream) {
                String nombreArchivo = entry.getFileName().toString();
                // Elimina la extensión .json para obtener el ID del producto
                if (nombreArchivo.endsWith(".json")) {
                    String idProducte = nombreArchivo.substring(0, nombreArchivo.length() - 5);
                    listaProductes.add(idProducte);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al llistar els productes al: " + BASE_DIRECTORY, e);
        }

        return listaProductes;
    }

    /**
     * Funció que s'assegura que el directori base existeixi.
     */
    static {
        File dir = new File(BASE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Desa un producte a un fitxer JSON.
     * 
     * @param producte Producte que es vol desar.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void saveProducte(Producte producte) throws IOException {
        String filepath = BASE_DIRECTORY + "/" + producte.getNom() + ".json";
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(producte, writer);
        }
             
        // Actualizar el orden solo si no está ya presente
        List<String> orden = carregarOrdre();
        if (!orden.contains(producte.getNom())) {
            orden.add(producte.getNom());
            guardarOrdre(orden);
        }

    }

    /**
     * Carrega un producte des d'un fitxer JSON.
     * 
     * @param id Identificador del producte que es vol carregar.
     * @return El producte carregat.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static Producte loadProducte(String id) throws IOException {
        String filepath = BASE_DIRECTORY + "/" + id + ".json";
        File file = new File(filepath);
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Producte.class);
        }
    }

    /**
     * Afegeix un nou producte creant un fitxer JSON per a ell.
     * 
     * @param id Identificador del producte.
     * @param similituds Vector de similituds del producte amb altres productes.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void afegirProducte(String id, Double[] similituds) throws IOException {
        Producte producte = new Producte(id, similituds);
        saveProducte(producte);

    }

    /**
     * Elimina un producte esborrant el seu fitxer JSON.
     * 
     * @param id Identificador del producte que es vol eliminar.
     */
    public static void eliminarProducte(String id) throws IOException {
        String filepath = BASE_DIRECTORY + "/" + id + ".json";
        File file = new File(filepath);
        if (file.exists()) {
            if (file.delete()) {
                List<String> orden = carregarOrdre();
                orden.remove(id);
                guardarOrdre(orden);
            } 
        }
        
    }


    /**
     * Modifica el grau de similitud entre dos productes i actualitza els fitxers JSON.
     * 
     * @param producteA Identificador del primer producte.
     * @param producteB Identificador del segon producte.
     * @param sim Nou grau de similitud.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public static void modificarGrauSimil(String producteA, String producteB, double sim) throws IOException {
        Producte prodA = loadProducte(producteA);
        Producte prodB = loadProducte(producteB);
    
        if (prodA != null && prodB != null) {
            List<String> keyList = new ArrayList<>(CtrlDomini.getInstancia().consultaCistella().keySet());
            int indexB = keyList.indexOf(producteB);
            int indexA = keyList.indexOf(producteA);
    
            if (indexB != -1 && indexA != -1) {
                prodA.getSimilituds().set(indexB, sim);
                prodB.getSimilituds().set(indexA, sim);
    
                saveProducte(prodA);
                saveProducte(prodB);
            } else {
                throw new IOException("No s'han trobat les posicions dels producte en el mapaCis.");
            }
        } else {
            throw new IOException("No s'han pogut carregar els productes.");
        }
    }
}
