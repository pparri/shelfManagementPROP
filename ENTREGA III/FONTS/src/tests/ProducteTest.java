package tests;

import classes.Producte;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

//assetEquals: Compara si dos objectes son iguals. Si no ho son, el test falla.
//assertNull: Comprova si un objecte és null. Si no ho és, el test falla.
//assertTrue: Comprova si una condició és certa. Si no ho és, el test falla.
//assertFalse: Comprova si una condició és falsa. Si no ho és, el test falla.
public class ProducteTest {
    
    /**
     * Test que comprova que es crea un Producte correctament.
     * 
     * Cas important perquè: Es comprova que es crea un producte correctament amb similituds vàlides.
     */
    @Test
    public void test_ProducteValid() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        assertEquals("producte", (String) p.getNom());
        // Convertim l'ArrayList en una solució de tipus Double[]
        ArrayList<Double> llistaEsperada = new ArrayList<>(Arrays.asList(similituds));
        assertEquals(llistaEsperada, p.getSimilituds());
    }

    /**
     * Test que comprova que es crea un Producte correctament.
     * 
     * Cas important perquè: Es comprova que la llista de similituds del producte és buida si alguna de les similituds és incorrecta.
     */
    @Test
    public void test_ProducteSimNoValides() {
        Double[] similituds = {-0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);

        assertEquals(0, p.getSimilituds().size());
    }

    /**
     * Test que comprova que es crea un Producte correctament.
     * 
     * Cas important perquè: Es comprova que la llista de similituds del producte és buida si no se li passen similituds quan es crea.
     */
    @Test
    public void test_ProducteSimBuides() {
        Double[] similituds = {};
        Producte p = new Producte("producte", similituds);
        
        assertEquals(0, p.getSimilituds().size());
    }


    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que s'afegeix correctament i en la posició que toca la nova similitud del producte.
     */
    @Test
    public void test_afegeixSimilitudCorrecta() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(0.6);

        assertEquals(4, p.getSimilituds().size());
        ArrayList<Double> novesSim = p.getSimilituds();
        assertEquals(0.6, novesSim.get(3), 0.0001);
    }

    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que no s'afegeix la similitud si aquesta és incorrecta (AMB NEGATIU).
     */
    @Test
    public void test_afegeixSimilitudNegativa() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(-0.5);

        //No s'ha afegit a la llista de similituds, ja que la similitud era incorrecta.
        assertEquals(3, p.getSimilituds().size());
    }

    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que no s'afegeix la similitud si aquesta és incorrecta.
     */
    @Test
    public void test_afegeixSimilitudGran1() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(1.2);

        //No s'ha afegit a la llista de similituds, ja que la similitud era incorrecta.
        assertEquals(3, p.getSimilituds().size());
    }

    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que si la similitud és 0.0 si que s'afegeix a les similituds del producte.
     */
    @Test
    public void test_afegeixSimilitud0() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(0.0);

        assertEquals(4, p.getSimilituds().size());
        ArrayList<Double> novesSim = p.getSimilituds();
        assertEquals(0.0, novesSim.get(3), 0.0001);
    }

    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que si la similitud és 1.0 si que s'afegeix a les similituds del producte.
     */
    @Test
    public void test_afegeixSimilitud1() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(1.0);

        assertEquals(4, p.getSimilituds().size());
        ArrayList<Double> novesSim = p.getSimilituds();
        assertEquals(1.0, novesSim.get(3), 0.0001);
    }

    /**
     * Test que comprova que una nova similitud s'afegeix a la llista correctament.
     * 
     * Cas important perquè: Es comprova que no s'afegeix la similitud si aquesta és un NULL.
     */
    @Test
    public void test_afegeixSimilitudNull() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.afegeixSimilitud(null);

        //No s'ha afegit a la llista de similituds, ja que la similitud era incorrecta.
        assertEquals(3, p.getSimilituds().size());
    }


    /**
     * Test que comprova que una similitud s'elimina de la llista correctament.
     * 
     * Cas important perquè: Es comprova que s'elimina correctament la similitud de la posició que li diem, i que les altres similituds es recol·loquen correctament en la llista.
     */
    @Test
    public void test_eliminaSimilitudCorrecta() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.eliminaSimilitud(1);

        assertEquals(2, p.getSimilituds().size());
        assertEquals(0.4, (Double) p.getSimilituds().get(1), 0.0001);
    }

    /**
     * Test que comprova que una similitud s'elimina de la llista correctament.
     * 
     * Cas important perquè: Es comprova que no s'elimina cap similitud si la posició que se li diu està fora del rang de la llista de similituds.
     */
    @Test
    public void test_eliminaSimilitudPosInvalida() {
        Double[] similituds = {0.8, 1.0, 0.4};
        Producte p = new Producte("producte", similituds);
        p.eliminaSimilitud(4);

        assertEquals(3, p.getSimilituds().size());
    }


    /**
     * Test que comprova que la comprovació de les similituds és correcta. Totes amb valors correctes.
     * 
     * Cas important perquè: Es comprova que la comprovació de similituds amb similituds CORRECTES és correcte.
     */
    @Test
    public void test_similitudsCorrectes() {
        Double[] similitudsValides = {0.0, 0.5, 1.0};
        Producte p = new Producte("producte", similitudsValides);
        assertTrue(p.similitudsCorrectes(similitudsValides));
    }

    /**
     * Test que comprova que la comprovació de les similituds és correcta. Similituds fora del rang permés.
     * 
     * Cas important perquè: Es comprova que la comprovació de similituds amb similituds FORA DEL RANG PERMÉS permés és correcte.
     */
    @Test
    public void test_similitudsCorrectesFake() {
        Double[] similitudsFake = {-0.2, 1.1, 0.5};
        Producte p = new Producte("producte", similitudsFake);
        assertFalse(p.similitudsCorrectes(similitudsFake));
    }

    /**
     * Test que comprova que la comprovació de les similituds és correcta. Totes en el límit del rang.
     * 
     * Cas important perquè: Es comprova que la comprovació de similituds amb similituds EN EL LÍMIT DEL RANG PERMÉS és correcte.
     */
    @Test
    public void test_similitudsCorrectesAlLimit() {
        Double[] similitudsAlLimit = {0.0, 1.0, 1.0};
        Producte p = new Producte("producte", similitudsAlLimit);
        assertTrue(p.similitudsCorrectes(similitudsAlLimit));
    }

    /**
     * Test que comprova que la comprovació de les similituds és correcta. Array de similituds buït.
     * 
     * Cas important perquè: Es comprova que la comprovació de similituds amb similituds BUIDES és correcte.
     */
    @Test
    public void test_similitudsCorrectesBuides() {
        Double[] similitudsBuides = {};
        Producte p = new Producte("producte", similitudsBuides);
        assertFalse(p.similitudsCorrectes(similitudsBuides));
    }

    /**
     * Test que comprova que la comprovació de les similituds és correcta. Array de similituds buït.
     * 
     * Cas important perquè: Es comprova que la comprovació de similituds amb similituds que contenen algun NULL és correcte.
     */
    @Test
    public void test_similitudsCorrectesAmbNull() {
        Double[] similitudsNull = {0.0, 0.5, null};
        Producte p = new Producte("producte", similitudsNull);
        assertFalse(p.similitudsCorrectes(similitudsNull));
    }


    /**
     * Test que comprova que la modificació de l'array de similituds d'un producte es fa correctament.
     * 
     * Cas important perquè: Es comprova que la modificació d'una similitud d'un producte es fa correctament i en la posició adequada.
     */
    @Test
    public void test_modificarGrauSimilPValida() {
        Double[] similitudsIniciales = {0.5, 0.6, 0.7};
        Producte p = new Producte("producte", similitudsIniciales);
        int resultat = p.modificarGrauSimilP(1, 0.8);
        assertEquals(1, resultat);
        assertEquals(0.8, (Double) p.getSimilituds().get(1), 0.0001);
    }

    /**
     * Test que comprova que la modificació de l'array de similituds d'un producte es fa correctament.
     * 
     * Cas important perquè: Es comprova que la modificació d'una similitud per una similitud incorrecta no es realitza (amb similitud NEGATIVA).
     */
    @Test
    public void test_modificarGrauSimilPNoValida() {
        Double[] similitudsIniciales = {0.5, 0.6, 0.7};
        Producte p = new Producte("producte", similitudsIniciales);
        int resultat = p.modificarGrauSimilP(1, -0.8);
        assertEquals(-1, resultat);
        assertEquals(0.6, (Double) p.getSimilituds().get(1), 0.0001);
    }

    /**
     * Test que comprova que la modificació de l'array de similituds d'un producte es fa correctament.
     * 
     * Cas important perquè: Es comprova que la modificació d'una similitud per una similitud incorrecta no es realitza (amb similitud FORA DE RANG).
     */
    @Test
    public void test_modificarGrauSimilPForaRang() {
        Double[] similitudsIniciales = {0.5, 0.6, 0.7};
        Producte p = new Producte("producte", similitudsIniciales);
        int resultat = p.modificarGrauSimilP(1, 1.5);
        assertEquals(-1, resultat);
        assertEquals(0.6, (Double) p.getSimilituds().get(1), 0.0001);
    }

    /**
     * Test que comprova que la modificació de l'array de similituds d'un producte es fa correctament.
     * 
     * Cas important perquè: Es comprova que la modificació d'una similitud no es realitza si la posició que 
     *      se li diu està fora del rang de la llista de similituds, és a dir, no existeix la posició.
     */
    @Test
    public void test_modificarGrauSimilPInvalidaPos() {
        Double[] similitudsIniciales = {0.5, 0.6, 0.7};
        Producte p = new Producte("producte", similitudsIniciales);
        int resultat = p.modificarGrauSimilP(5, 0.8);
        assertEquals(-1, resultat);
    }
}