package classes;

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
     * Modifica la distribució del prestatge. Canvia les posicions de dos productes.
     * 
     * @param producteA Primer producte al que se li vol canviar la posició en el prestatge.
     * @param producteB Segon producte al que se li vol canviar la posició en el prestatge.
     * @return Integer.
     */
    public int modificarSWProducte(String producteA, String producteB) {
        if (producteA == null || producteB == null) return -1;
        int posicioA = distPrestatge.indexOf(producteA);
        int posicioB = distPrestatge.indexOf(producteB);
        if (posicioA == -1 || posicioB == -1) return -1;

        String temporal = distPrestatge.get(posicioA);
        distPrestatge.set(posicioA, producteB);
        distPrestatge.set(posicioB, temporal);
        return 1;
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