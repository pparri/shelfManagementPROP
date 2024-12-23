package classes.domini;

import java.util.ArrayList;

/**
 * Representa un prestatge, que és el conjunt de productes que hi ha al sistema.
 */
public class Prestatge {

    private ArrayList<String> distPrestatge;  

    /**
     * Constructor de la classe Prestatge.
     * 
     * @param distPrestatge Prestatge final amb la seva distribució.
     */
    public Prestatge(ArrayList<String> distPrestatge) {
        this.distPrestatge = distPrestatge;
    }

    /**
     * Retorna la llista de productes distribuïts al prestatge.
     * 
     * @return ArrayList<String>.
     */
    public ArrayList<String> getDistribucio() {
        return this.distPrestatge;
    }

    /**
     * Escriu la distribució final del prestatge.
     */
    public void llistarDistribucio() {
        for (String s: distPrestatge) {
            System.out.println(s);
        }
    }
    
}