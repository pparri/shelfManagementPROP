package classes.domini;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representa un producte.
 * 
 * Un producte té un nom, i la seva llista de similituds amb els altres productes.
 */
public class Producte {
    
    private String nom;
    private ArrayList<Double> similituds;

    /**
     * Constructor de la classe Producte.
     * @param nom El nom del producte afegit.
     * @param similituds La llista de similituds del producte amb els altres productes.
     */
    public Producte(String nom, Double[] similituds) {
        this.nom = nom;
        if (similitudsCorrectes(similituds)) {
            ArrayList<Double> llista2 = new ArrayList<>(Arrays.asList(similituds));
            this.similituds = llista2;
        }
        else this.similituds = new ArrayList<>();
    }

    /**
     * Retorna el nom del producte.
     * 
     * @return String.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retorna la llista de similituds del producte.
     * 
     * @return ArrayList<Double>.
     */
    public ArrayList<Double> getSimilituds() {
        return this.similituds;
    }

    /**
     * Afegeix una nova similitud al producte.
     * 
     * @param sim La nova similitud que volem afegir al producte.
     */
    public void afegeixSimilitud(Double sim) {
        if (sim == null || sim < 0 || sim > 1 ) return;
        else this.similituds.add(sim);
    }

    /**
     * Elimina una similitud del producte.
     * 
     * @param pos La posició de la similitud que volem eliminar del producte.
     */
    public void eliminaSimilitud(int pos) {
        if (pos >= 0 && pos < similituds.size()) 
            similituds.remove(pos);
    }

    /**
     * Comprova que les similituds del producte siguin correctes, per després poder afegir-les.
     * 
     * @param similituds La llista de similituds del producte.
     * @return Boolean.
     */
    public boolean similitudsCorrectes(Double[] similituds) {
        if (similituds.length == 0) return false;
        for (Double s : similituds) {
            if (s == null || s < 0 || s > 1) return false;      //son correctes les similituds?
        }
        return true;
    }

    /**
     * Modifica el grau de similitud d'un producte amb un altre.
     * 
     * @param posicio Posició del producte amb el qual se li vol modificar un grau de similitud.
     * @param sim Nou grau de similitud que es vol afegir als productes.
     * @return Integer.
     */
    public int modificarGrauSimilP(int posicio, double sim) {
        if (this.similituds.size() <= posicio) return -1;
        if (sim < 1.0 && sim > 0.0) 
        {
            similituds.set(posicio,sim);
            return 1;
        }
        return -1;
    }

}