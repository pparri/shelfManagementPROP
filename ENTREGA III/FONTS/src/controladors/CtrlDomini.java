package controladors;

import classes.domini.DistribucioKruskal;
import classes.domini.Prestatge;
import classes.domini.Producte;
import classes.persistencia.ProducteIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Representa el controlador de domini.
 * 
 * El controlador de domini té un prestatge i un conjunt de productes.
 * També proporciona mètodes per gestionar els productes i el prestatge.
 */
public class CtrlDomini {
    private static CtrlDomini instancia;
    private static CtrlPresentacio ctrlPresentacio;
    private LinkedHashMap<String, ArrayList<Double>> mapaCis;
    private ArrayList<Producte> cistella;
    private ArrayList<String> prestatge; // Prestatge final obtingut a partir de FB / 2-aprox
    List<DistribucioKruskal.Aresta> mst; // Resultat Kruskal
    List<String> cicleEuleria; // Resultat DFS

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);;

    /**
     * Constructor de la classe CtrlDomini.
     */
    public CtrlDomini() {
        mapaCis = new LinkedHashMap<>();
        cistella = new ArrayList<>();
        prestatge = new ArrayList<>();
        try {
            carregarMapaCis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            prestatge = tornaPrestatge();
        } catch (IOException ex) {
            System.err.println("Error al cargar la cistella: " + ex.getMessage());
        }
    }

    /**
     * Funció per a carregar el mapa de la cistella basat en l'ordre guardat prèviament.
     */
    private void carregarMapaCis() throws IOException {
        List<String> orden = ProducteIO.carregarOrdre();
        for (String id : orden) {
            Producte producte = ProducteIO.loadProducte(id);
            if (producte != null) {
                mapaCis.put(id, new ArrayList<>(producte.getSimilituds()));
                cistella.add(producte);
            }
        }
        /*
        // Añadir productos que no están en el orden pero existen en los archivos
        List<String> todosProductos = ProducteIO.LlistarProductes();
        for (String id : todosProductos) {
            if (!mapaCis.containsKey(id)) {
                Producte producte = ProducteIO.loadProducte(id);
                if (producte != null) {
                    mapaCis.put(id, new ArrayList<>((producte.getSimilituds())));
                    cistella.add(producte);
                }
            }
        }
        */
    }

    /**
     * Retorna la instancia del controlador de domini.
     * 
     * @return CtrlDomini
     */
    public static CtrlDomini getInstancia() {
        if (instancia == null)
            instancia = new CtrlDomini();
        return instancia;
    }

    /**
     * Mètode per inicialitzar la instancia del controlador de presentació.
     */
    public void setCtrlPresentacio(CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
    }

    /**
     * Mètode per afegir més d'un producte a l'aplicació.
     * 
     * @param ids Noms dels diferents productes.
     * @param similituds Similituds dels diferents productes.
     * @param N Nombre de nous productes que es volen afegir a l'aplicació.
     * @throws Exception
     */
    public void afegirProducteMultiple(String[] ids, Double[][] similituds, int N) throws Exception 
    {
        if (ids.length != similituds.length || ids.length != N) {
            throw new Exception("La longitud dels arrays no coincideix amb el nombre de productes.");
        }
    
        for (int i = 0; i < N; i++) {
            for (Double sim : similituds[i]) {
                if (sim < 0 || sim > 1) {
                    throw new Exception("Similitud incorrecta per al producte: " + ids[i]);
                }
            }
        }
    
        for (int i = 0; i < N; i++) {
            ArrayList<Double> nuevaListaSimilituds = new ArrayList<>(List.of(similituds[i]));
            Producte nuevoProducto = new Producte(ids[i], nuevaListaSimilituds.toArray(new Double[0]));
            cistella.add(nuevoProducto);
            mapaCis.put(ids[i], nuevaListaSimilituds);
    
            CtrlPersistencia.guardarProducte(nuevoProducto);
        }
        //imprimirMapaCis();
    }
    
    
    /**
     * Afegeix un producte a la gestió de productes.
     * 
     * @param id Nom (identificador) del producte.
     * @param similituds Vector de similitud del producte amb altres productes, inclosa la similitud amb ell mateix.
     * @return El nou size del vector Cistella.
     * 
     * @throws Exception Si ocorre un error en la generació del prestatge.
     */
    public int afegirProducteSingle(String id, Double[] similituds) throws Exception 
    {
        try {
            Producte existent = CtrlPersistencia.carregarProducte(id);
            if (existent != null) {
                throw new Exception("Producte ja existeix: " + id);
            }
        } catch (IOException e) {
            throw new Exception("Error accedint a la persistència per verificar l'existència del producte: " + id, e);
        }

        if (similituds.length != cistella.size() + 1) {
            throw new Exception("La longitud del vector de similituds no coincideix amb el nombre de productes existents més la similitud amb ell mateix.");
        }
        for (Double sim : similituds) {
            if (sim < 0 || sim > 1) {
                throw new Exception("Similitud amb valor incorrecte de producte: " + id);
            }
        }

        for (int i = 0; i < cistella.size(); i++) {
            Producte productoActual = cistella.get(i);

            ArrayList<Double> simActual = productoActual.getSimilituds();
            simActual.add(similituds[i]);

            String idActual = productoActual.getNom();
            mapaCis.put(idActual, new ArrayList<>(simActual));
            CtrlPersistencia.guardarProducte(productoActual);
        }

        ArrayList<Double> nuevaListaSimilituds = new ArrayList<>(List.of(similituds));
        Producte nuevoProducto = new Producte(id, nuevaListaSimilituds.toArray(new Double[0]));
        cistella.add(nuevoProducto);
        mapaCis.put(id, nuevaListaSimilituds);
        CtrlPersistencia.guardarProducte(nuevoProducto);

        //imprimirMapaCis();

        return cistella.size();
    }

    /**
     * Imprimeix el contingut de la cistella.
     */
    public void imprimirMapaCis() {
        for (Map.Entry<String, ArrayList<Double>> entry : mapaCis.entrySet()) {
            String producte = entry.getKey();
            ArrayList<Double> similitudes = entry.getValue();
            System.out.println("Producte: " + producte);
            System.out.print("Similituds: ");
            for (Double sim : similitudes) {
                System.out.print(sim + " ");
            }
            System.out.println("\n--------------------------");
        }
    }
    
    /**
     * Elimina un producte de la gestió de productes.
     * 
     * @param id Nom (identificador) del producte a eliminar.
     * @return El nou size del vector Cistella.
     * 
     * @throws Exception Si ocorre un error en la gestió dels productes.
     */
    public int eliminarProducte(String id) throws Exception 
    {
        Producte producteAEliminar = null;
        int indexAEliminar = -1;

        for (int i = 0; i < cistella.size(); i++) {
            if (cistella.get(i).getNom().equals(id)) {
                producteAEliminar = cistella.get(i);
                indexAEliminar = i;
                break;
            }
        }

        if (producteAEliminar == null) {
            throw new Exception("El producte no existeix: " + id);
        }

        cistella.remove(indexAEliminar);
        mapaCis.remove(id);

        for (Producte productoActual : cistella) {
            ArrayList<Double> simActual = productoActual.getSimilituds();
            if (indexAEliminar < simActual.size()) {
                simActual.remove(indexAEliminar);
            }
            String idActual = productoActual.getNom();
            mapaCis.put(idActual, simActual);
            CtrlPersistencia.guardarProducte(productoActual);
        }
        CtrlPersistencia.eliminarProducte(id);

        //imprimirMapaCis();

        return cistella.size();
    }

    /**
     * Mètode per obtenir les dades de la cistella.
     * 
     * @return LinkedHashMap<String, ArrayList<Double>>
     */
    public LinkedHashMap<String, ArrayList<Double>> consultaCistella()
    {
        return new LinkedHashMap<>(mapaCis);  
    }

    /**
     * Consulta el Prestatge.
     * 
     * @return Retorna un ArrayList<String> (el Prestatge).
     */
    public ArrayList<String> consultarPrestatge()
    {
        return prestatge;
    }

    /**
     * Consulta el Prestatge.
     * 
     * @return Retorna un ArrayList<String> (el Prestatge).
     */
    public ArrayList<String> tornaPrestatge() throws IOException
    {
        ArrayList<String> distribucio = new ArrayList<>();
        try {
            Prestatge prestatge = CtrlPersistencia.carregarPrestatge();
            distribucio = prestatge.getDistribucio();
        } catch (IOException e) {
            System.err.println("Error al carregar el prestatge: " + e.getMessage());
        }
        prestatge = distribucio;
        return consultarPrestatge();
    }

    /**
     * Modifica el grau de similitud d'un producte amb un altre.
     * 
     * @param producteA Primer producte al qual se li vol modificar un grau de similitud.
     * @param producteB Segon producte al qual se li vol modificar un grau de similitud.
     * @param sim Nou grau de similitud que es vol afegir als productes.
     * @throws Exception Si ocorre un error en la generació del prestatge.
     */
    public void modificarGrauSimil(String producteA, String producteB, double sim) throws Exception {
        if (sim < 0 || sim > 1) {
            throw new Exception("La similitud no es correcte.\n");
        }
        
        //S'ha de fer servir mapaCis que és el LinkedHashMap
        List<String> keyList = new ArrayList<>(mapaCis.keySet()); 
        int indexA = keyList.indexOf(producteA);
        int indexB = keyList.indexOf(producteB);
        
        if (indexA != -1 && indexB != -1) {
            mapaCis.get(producteA).set(indexB, sim);
            mapaCis.get(producteB).set(indexA, sim);
            //imprimirMapaCis();
        }

        //actualitzem en persistencia
        CtrlPersistencia.modificarGrauSimil(producteA, producteB, sim);
    }

    /**
     * Genera un prestatge i el guarda en persistència.
     * 
     * @throws Exception Si ocorre un error en la generació del prestatge.
     */
    public void generarPrestatgeDosAprox() throws Exception {
        HashMap<String, ArrayList<Double>> elmapa = consultaCistella();
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(elmapa);
        prestatge = ctrlAlgoritme.executarDosAprox();

        // Guardar el prestatge en persistencia
        Prestatge prestatgeObj = new Prestatge(prestatge);
        CtrlPersistencia.guardarPrestatge(prestatgeObj);
    }

    /**
     * Genera un prestatge utilitzant força bruta.
     * 
     * @throws Exception Si ocorre un error en la generació del prestatge.
     */
    public void generarPrestatgeForcaBruta() throws Exception {
        HashMap<String, ArrayList<Double>> elmapa = consultaCistella();
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(elmapa);
        prestatge = ctrlAlgoritme.executarForcaBruta();

        // Guardar el prestatge generat en persistència
        Prestatge prestatgeObj = new Prestatge(prestatge);
        CtrlPersistencia.guardarPrestatge(prestatgeObj);
    }
    
    /**
     * Mètode per guardar el prestatge en l'aplicació.
     * 
     * @param prestatge Distribució del prestatge que es vol guardar.
     * @throws Exception
     */
    public void guardarPrestatge(ArrayList<String> prestatge) throws Exception
    {
        Prestatge prestatgeObj = new Prestatge(prestatge);
        CtrlPersistencia.guardarPrestatge(prestatgeObj);
    }

    /**
     * Aquest mètode serveix per modificar la distribució del prestatge des de l'aplicació.
     * 
     * @param producteA Nom del primer producte del qual es vol canviar la posició amb un altre.
     * @param producteB Nom del segon producte del qual es vol canviar la posició amb el primer.
     * @throws IOException
     */
    public void modificarPrestatge(String producteA, String producteB) throws IOException
    {   
       CtrlPersistencia.modificarPrestatge(producteA,producteB);
       Prestatge prestatgeOBJ = CtrlPersistencia.carregarPrestatge();
       CtrlPersistencia.guardarPrestatge(prestatgeOBJ);
       prestatge = prestatgeOBJ.getDistribucio();
    }

    /**
     * Mètode que serveix per poder eliminar el prestatge des de l'aplicació.
     * @throws IOException
     */
    public void eliminarDistPrestatge() throws IOException {
        try {
            CtrlPersistencia.eliminarPrestatge();
        } catch (IOException e) {
            System.err.println("Error al eliminar la distribució del prestatge: " + e.getMessage());
        }
        prestatge.clear();
    }
    
    /**
     * Surt de l'aplicacio.
     */
    public void sortir() {
        scheduler.schedule(() -> System.exit(0), 5, TimeUnit.SECONDS); //Controla el temps de guardar les dades
    }
    
    /**
     * Mètode per afegir un nou producte a l'aplicació.
     * 
     * @param id Nom del producte nou.
     * @return Producte.
     * @throws IOException
     */
    public Producte carregarProducte(String id) throws IOException
    {
        return CtrlPersistencia.carregarProducte(id);
    }
}
