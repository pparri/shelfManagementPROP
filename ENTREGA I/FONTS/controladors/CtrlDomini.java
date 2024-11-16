package controladors;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import classes.Prestatge;
import classes.Producte;
import classes.DistribucioKruskal;
import java.util.LinkedHashMap;


/**
 * Representa el controlador de domini.
 * 
 * El controlador de domini té un prestatge i un conjunt de productes.
 * També proporciona mètodes per gestionar els productes i el prestatge.
 */
public class CtrlDomini
{
    private static CtrlDomini instancia;
	private HashMap<String, ArrayList<Double>> mapaCis;
    private ArrayList<Producte> cistella;
	private ArrayList<String> prestatge;    //prestatge final obtingut a partir de FB / 2-aprox
    List<DistribucioKruskal.Aresta> mst; //resultat kruskal
    List<String> cicleEuleria; //resultat DFS


    /**
     * Constructor de la classe CtrlDomini.
     */
	public CtrlDomini()
	{
        mapaCis = new LinkedHashMap<String, ArrayList<Double>>();
        cistella = new ArrayList<Producte>();
        prestatge = new ArrayList<String>();
        System.out.println("\nEstructures creades.\n");
	}

    /**
     * Retorna la instancia del controlador de domini.
     * 
     * @return CtrlDomini
     */
    public static CtrlDomini getInstancia() 
    {
        if (instancia == null) instancia = new CtrlDomini();
        return instancia;
    }


    /**
     * Afegeix un producte a la gestió de productes.
     * 
     * @param id Noms (identificadors) dels productes.
     * @param similituds Vectors de similitud(s) dels producte(s) amb altres productes.
     * @param N Quantitat de productes a afegir
     * @return El nou size del vector Cistella
     */
    public int afegirProducte(String[] ids, Double[][] similituds, int N) throws Exception 
    {
        for (String id : ids) {
            if (mapaCis.containsKey(id)) {
                throw new Exception("Producte ja existeix: " + id);
            }
        }
        int oldSize = cistella.size();
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            Double[] similitud = similituds[i];
            Producte prod = new Producte(id, similitud);

            int sum  = N+oldSize;
            if (similitud.length != N+oldSize) {
                throw new Exception("Les similituds no son correctes per al producte: " + id);
            }
            cistella.add(prod);
            mapaCis.put(id, prod.getSimilituds());
        }

        int newSize = cistella.size();
        if (N < newSize)
        {
            for (int i = 0; i < oldSize; i++) 
            {
                for (int j = 0; j < N; j++) 
                {
                    cistella.get(i).afegeixSimilitud(similituds[j][i]);
                }
            }
        }
        return newSize;
    }   

    /**
     * Elimina un producte de la gestió de productes.
     * 
     * @param id Nom (identificador) del producte que es vol eliminar.
     */
	public void eliminarProducte(String id) throws Exception
	{
		if (mapaCis.remove(id) == null) throw new Exception("Producte no existeix.");
		else 
        {
            int pos = 0;
            for (Producte produ : cistella) 
            {
                if (produ.getNom().equals(id)) 
                {
                    for (int i = 0; i < cistella.size(); ++i)
                    {
                        if (i != pos) cistella.get(i).eliminaSimilitud(pos);
                    }
                    cistella.remove(produ);
                    return;
                }
                ++pos;
            }
        }
	}

    /**
     * Executa l'algoritme de Kruskal
     *
     * @return ArrayList prestatge ordenat.
     */
    public void generarPrestatgeDosAprox()
    {
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(mapaCis);
        prestatge = ctrlAlgoritme.executarDosAprox();
    }

    /**
     * Executa l'algoritme de Forca Bruta
     */
    public void generarPrestatgeForcaBruta()
    {
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(mapaCis);
        prestatge = ctrlAlgoritme.executarForcaBruta();
    }

    /**
     * Executa l'algoritme de Kruskal
     */
    public void generarKruskal()
    {
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(mapaCis);
        mst = ctrlAlgoritme.executarKruskal();
        mostrarMST(mst);
    }

    /**
     * Recorregut en DFS que genera un cicle eulerià
     */
    public void generarEuler()
    {
        CtrlAlgoritme ctrlAlgoritme = new CtrlAlgoritme(mapaCis);
        cicleEuleria = ctrlAlgoritme.executarDFS();
        mostrarCicleEuleria(cicleEuleria);
    }

    /**
     * Consulta el PRESTATGE
     */
    public ArrayList<String> consultarPrestatge()
    {
       return prestatge;
	}

     /**
     * Consulta el Arbre d'expansió mínima (KRUSKAL)
     */
    public void mostrarMST(List<DistribucioKruskal.Aresta> mst) 
    {
        System.out.println("MST generat:\n");
        for (DistribucioKruskal.Aresta aresta : mst) {
            System.out.println(aresta.producte1 + " -- " + aresta.producte2 + " (Similitud: " + aresta.Similitud + ")");
        }
        System.out.print("\n");
    }

    /**
     * Consulta el Cicle Eulerià generat fent un DFS en base al Kruskal
     */
    public void mostrarCicleEuleria(List<String> cicloEuleriano) 
    {
        System.out.println("Cicle Euleria generat:");
        for (String producto : cicloEuleriano) {
            System.out.print(producto + " ");
        }
        System.out.println("\n");
    }

    /**
     * Consulta la CISTELLA
     */
    public void consultarCistella()
    {
        System.out.println("\nEl contingut de la Cistella es el següent: ");
        for (Producte prod : cistella)
        {
            System.out.println("Nom del Producte: "+prod.getNom());
            System.out.println("Similituds: "+prod.getSimilituds());
        }
        System.out.println("\n");
    }

    /**
     * Modifica el grau de similitud d'un producte amb un altre.
     * 
     * @param producteA Primer producte al qual se li vol modificar un grau de similitud.
     * @param producteB Segon producte al qual se li vol modificar un grau de similitud.
     * @param sim Nou grau de similitud que es vol afegir als productes.
     */
    public void modificarGrauSimil(String producteA, String producteB, double sim)
    {
        int posA = 0;
        int posB = 0;
        int it = 0;
        for (String s : mapaCis.keySet()) { 
            if (s.equals(producteA)) posA = it;
            if (s.equals(producteB)) posB = it;
            ++it; 
        }
        for (Producte prod : cistella) {
            if (prod.getNom().equals(producteA)) prod.modificarGrauSimilP(posB, sim);
            if (prod.getNom().equals(producteB)) prod.modificarGrauSimilP(posA, sim);
        }
    }

}
