package controladors;

import classes.DistribucioKruskal;
import classes.AlgorismeFB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Representa el controlador dels algorismes.
 * 
 * El controlador dels algorismes té un prestatge i un conjunt de productes.
 * També proporciona mètodes per gestionar l'execució dels algorismes.
 */
public class CtrlAlgoritme
{
   private static CtrlAlgoritme instancia;
   private static Map<String, ArrayList<Double>> mapaCis;
   private ArrayList<String> prestatge;
   List<DistribucioKruskal.Aresta> mst = new ArrayList<>();


    /**
     * Constructora per obtenir un objecte de tipus CtrlAlgoritme
     * 
     * @return CtrlAlgoritme
     */
    public CtrlAlgoritme(Map<String, ArrayList<Double>>mapacis)
    {
        mapaCis = mapacis;
    }
    
    /**
     * Retorna la instancia del controlador de l'Algorisme.
     * 
     * @return CtrlAlgoritme
     */
    public static CtrlAlgoritme getInstancia() 
    {
        if (instancia == null) instancia = new CtrlAlgoritme(mapaCis);
        return instancia;
    }

    /**
     * Executa l'algorisme dos-aproximació per generar la millor solució possible de la distribució dels productes en el prestatge.
     * 
     * @return ArrayList<String> (Solució òptima de la distribució del prestatge).
     */
    public ArrayList<String> executarDosAprox() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        System.out.println("\nConstruint el MST amb l'algorisme de KRUSKAL...");
        //List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();

        System.out.println("\nGenerant el cicle euleria a partir del MST...");
        //List<String> cicleEuleria = DistribucioKruskal.generaCicleEuleria(mst);

        System.out.println("\nConvertint el cicle euleria en un cicle hamiltonia...");
        ArrayList<String> prestatgeFinal = DistribucioKruskal.generarPrestatge();
        return prestatgeFinal;
    }

    /**
     * Executa l'algorisme de Kruskal per generar el MST.
     * 
     * @return List<DistribucioKruskal.Aresta> (MST).
     */
    public List<DistribucioKruskal.Aresta> executarKruskal()
    {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();
        return mst;
    }

    /**
     * Executa l'algorisme DFS.
     * 
     * @return List<String> (cicle eulerià).
     */
    public List<String> executarDFS() {

        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();
        List<String> cicleEuleria = DistribucioKruskal.generaCicleEuleria(mst);
        return cicleEuleria;
    }
    
    /**
     * Executa l'algorisme de força bruta per generar la millor solució possible de la distribució dels productes en el prestatge.
     * 
     * @return ArrayList<String> (Solució òptima de la distribució del prestatge).
     */
    public ArrayList<String> executarForcaBruta()
    {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        algorismeFB.configurarMapa(mapaCis);
        prestatge = algorismeFB.generarPrestatge();
        return prestatge;
    }
}