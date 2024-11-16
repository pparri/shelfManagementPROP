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
     * Retorna la instancia del controlador de Algoritme.
     * 
     * @return CtrlAlgoritme
     */
    public static CtrlAlgoritme getInstancia() 
    {
        if (instancia == null) instancia = new CtrlAlgoritme(mapaCis);
        return instancia;
    }


    public ArrayList<String> executarDosAprox() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        System.out.println("\nConstruint el MST amb l'algorisme de KRUSKAL...");
        List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();

        System.out.println("\nGenerant el cicle euleria a partir del MST...");
        List<String> cicleEuleria = DistribucioKruskal.generaCicleEuleria(mst);

        System.out.println("\nConvertint el cicle euleria en un cicle hamiltonia...");
        ArrayList<String> prestatgeFinal = DistribucioKruskal.generarPrestatge();
        return prestatgeFinal;
    }

    public List<DistribucioKruskal.Aresta> executarKruskal()
    {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();
        return mst;
    }

    public List<String> executarDFS() {

        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();
        distribucioKruskal.configurarMapa(mapaCis);
        List<DistribucioKruskal.Aresta> mst = DistribucioKruskal.construirMST();
        List<String> cicleEuleria = DistribucioKruskal.generaCicleEuleria(mst);
        return cicleEuleria;
    }
    
    public ArrayList<String> executarForcaBruta()
    {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        algorismeFB.configurarMapa(mapaCis);
        prestatge = algorismeFB.generarPrestatge();
        return prestatge;
    }
}