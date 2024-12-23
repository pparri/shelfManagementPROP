package tests;

import classes.AlgorismeFB;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

//assertEquals: Compara si dos objectes son iguals. Si no ho son, el test falla.
//assertNotEquals: Compara si dos objectes no son iguals. Si ho son, el test falla.
//assertNull: Comprova si un objecte és null. Si no ho és, el test falla.
//assertTrue: Comprova si una condició és certa. Si no ho és, el test falla.
//assertFalse: Comprova si una condició és falsa. Si no ho és, el test falla.
public class AlgorismeFBTest {
    
    /**
     * Test que comprova que es configura la cistella de productes correctament.
     */
    @Test
    public void test_configurarMapa1() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        Map<String, ArrayList<Double>> mapaIni = new HashMap<>();
        ArrayList<Double> similituds = new ArrayList<>();
        similituds.add(0.5);
        similituds.add(0.6);
        mapaIni.put("ProducteIni", similituds);

        algorismeFB.configurarMapa(mapaIni);

        assertNotEquals(-1, algorismeFB.getIndex("ProducteIni"));
        assertEquals(similituds, algorismeFB.getSimilitudsPro("ProducteIni"));
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("ProducteIni"));
    }

    /**
     * Test que comprova que es configura la cistella de productes correctament.
     */
    @Test
    public void test_configurarMapaSenseDuplicats() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        Map<String, ArrayList<Double>> mapaIni = new HashMap<>();
        ArrayList<Double> similituds1 = new ArrayList<>(Arrays.asList(0.8, 0.3));
        ArrayList<Double> similituds2 = new ArrayList<>(Arrays.asList(0.4, 0.2));

        mapaIni.put("ProducteIni", similituds1);
        mapaIni.put("ProducteIni", similituds2);

        algorismeFB.configurarMapa(mapaIni);

        assertEquals(1, algorismeFB.getLlistaProductes().size());
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("ProducteIni"));
        assertNotEquals(-1, algorismeFB.getIndex("ProducteIni"));
    }

    /**
     * Test que comprova que es configura la cistella de productes correctament.
     */
    @Test
    public void test_configurarMapaBuit() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        Map<String, ArrayList<Double>> mapaIni = new HashMap<>();;

        algorismeFB.configurarMapa(mapaIni);

        assertEquals(-1, algorismeFB.getIndex("ProducteIni"));
        assertEquals(0, algorismeFB.getLlistaProductes().size());
    }

    /**
     * Test que comprova que es configura la cistella de productes correctament.
     */
    @Test
    public void test_configurarMapaAmbVarisProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        Map<String, ArrayList<Double>> mapaIni = new HashMap<>();
        ArrayList<Double> similituds1 = new ArrayList<>(Arrays.asList(0.8, 0.3));
        ArrayList<Double> similituds2 = new ArrayList<>(Arrays.asList(0.4, 0.2));

        mapaIni.put("Producte1", similituds1);
        mapaIni.put("Producte2", similituds2);

        algorismeFB.configurarMapa(mapaIni);

        assertEquals(2, algorismeFB.getLlistaProductes().size());
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("Producte1"));
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("Producte2"));
        assertNotEquals(-1, algorismeFB.getIndex("Producte1"));
        assertNotEquals(-1, algorismeFB.getIndex("Producte2"));
        assertEquals(similituds1, algorismeFB.getSimilitudsPro("Producte1"));
        assertEquals(similituds2, algorismeFB.getSimilitudsPro("Producte2"));
    }


    /**
     * Test que comprova que es genera el prestatge correctament.
     */
    @Test
    public void test_generarPrestatge1() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.getLlistaProductes().add("Producte1");
        algorismeFB.getLlistaProductes().add("Producte2");

        ArrayList<String> resultat = algorismeFB.generarPrestatge();

        assertEquals(2, resultat.size());
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("Producte1"));
        assertEquals(1, algorismeFB.getSiEstaProducteEnLlista("Producte2"));
    } 

    /**
     * Test que comprova que es genera el prestatge correctament.
     */
    @Test
    public void test_generarPrestatgeBuit() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        ArrayList<String> resultat = algorismeFB.generarPrestatge();

        assertEquals(0, resultat.size());
        assertEquals(0, algorismeFB.getDefSolution().length);
    }

    /**
     * Test que comprova que es genera el prestatge correctament.
     */
    @Test
    public void test_generarPrestatgeMillorSolucio() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        algorismeFB.getLlistaProductes().add("Producte1");
        algorismeFB.getLlistaProductes().add("Producte2");
        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.9});
        algorismeFB.getShelf().put("Producte2", new double[]{0.9, 1.0});

        ArrayList<String> resultat = algorismeFB.generarPrestatge();

        assertEquals(2, resultat.size());
        assertEquals("Producte1", resultat.get(0));
        assertEquals("Producte2", resultat.get(1));
        assertTrue(algorismeFB.getScoreMax() > 0);
    }

    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcAmbDosProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.9});
        algorismeFB.getShelf().put("Producte2", new double[]{0.9, 1.0});

        String[] solucio = {"Producte1", "Producte2"};
        int n = solucio.length;

        double score = algorismeFB.scoreCalc(solucio, n);

        assertEquals(1.8, score, 0.0001);
    }

    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcAmbUnProducte() {
        AlgorismeFB algorismeFB = new AlgorismeFB();

        algorismeFB.getShelf().put("Producte1", new double[]{1.0});

        String[] solucio = {"Producte1"};
        int n = solucio.length;

        double score = algorismeFB.scoreCalc(solucio, n);
        
        assertEquals(1.0, score, 0.0001);
    }

    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcBuit() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        String[] solucio = {};
        int n = solucio.length;

        double score = algorismeFB.scoreCalc(solucio, n);
        assertEquals(0.0, score, 0.0001);
    }


    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingAmbDosProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.9});
        algorismeFB.getShelf().put("Producte2", new double[]{0.9, 1.0});

        String[] solucio = new String[2];
        boolean[] utilitzats = new boolean[2];
        int n = 2;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(2, algorismeFB.getIterador());
        assertEquals(1.8, algorismeFB.getScoreMax(), 0.0001);
        assertArrayEquals(new String[]{"Producte1", "Producte2"}, algorismeFB.getDefSolution());
    }

    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingAmbTresProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.9, 0.7});
        algorismeFB.getShelf().put("Producte2", new double[]{0.9, 1.0, 0.3});
        algorismeFB.getShelf().put("Producte3", new double[]{0.7, 0.3, 1.0});

        String[] solucio = new String[3];
        boolean[] utilitzats = new boolean[3];
        int n = 3;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(6, algorismeFB.getIterador());
        assertEquals(1.9, algorismeFB.getScoreMax(), 0.0001);
        assertNotNull(algorismeFB.getDefSolution());
    }

    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingAmbQuatreProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.1, 0.9, 0.8});
        algorismeFB.getShelf().put("Producte2", new double[]{0.1, 1.0, 0.1, 0.1});
        algorismeFB.getShelf().put("Producte3", new double[]{0.9, 0.1, 1.0, 0.9});
        algorismeFB.getShelf().put("Producte4", new double[]{0.8, 0.1, 0.9, 1.0});

        String[] solucio = new String[4];
        boolean[] utilitzats = new boolean[4];
        int n = 4;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(24, algorismeFB.getIterador());
        assertEquals(2.0, algorismeFB.getScoreMax(), 0.0001);
        assertNotNull(algorismeFB.getDefSolution());
    }

    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingAmbCincProductes() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        algorismeFB.getShelf().put("Producte1", new double[]{1.0, 0.1, 0.9, 0.0, 0.8});
        algorismeFB.getShelf().put("Producte2", new double[]{0.1, 1.0, 0.1, 0.0, 0.1});
        algorismeFB.getShelf().put("Producte3", new double[]{0.9, 0.1, 1.0, 0.0, 0.9});
        algorismeFB.getShelf().put("Producte4", new double[]{0.0, 0.0, 0.0, 1.0, 0.0});
        algorismeFB.getShelf().put("Producte5", new double[]{0.8, 0.1, 0.9, 0.0, 1.0});

        String[] solucio = new String[5];
        boolean[] utilitzats = new boolean[5];
        int n = 5;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(120, algorismeFB.getIterador());
        assertEquals(1.9, algorismeFB.getScoreMax(), 0.0001);
        assertNotNull(algorismeFB.getDefSolution());
    }

    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingAmbUnProducte() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        algorismeFB.getShelf().put("Producte1", new double[]{1.0});

        String[] solucio = new String[1];
        boolean[] utilitzats = new boolean[1];
        int n = 1;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(1, algorismeFB.getIterador());
        assertEquals(1.0, algorismeFB.getScoreMax(), 0.0001);
        assertArrayEquals(new String[]{"Producte1"}, algorismeFB.getDefSolution());
    }

    /**
     * Test que comprova que el càlcul de la millor solució del prestatge es fa correctament.
     */
    @Test
    public void test_backtrackingBuit() {
        AlgorismeFB algorismeFB = new AlgorismeFB();
        algorismeFB.setScoreMax(0.0);
        algorismeFB.setIterador(0);
        algorismeFB.setDefSolution(null);

        String[] solucio = new String[0];
        boolean[] utilitzats = new boolean[0];
        int n = 0;

        algorismeFB.backtracking(solucio, utilitzats, 0, n);

        assertEquals(1, algorismeFB.getIterador());
        assertEquals(0.0, algorismeFB.getScoreMax(), 0.0001);
        assertNull(algorismeFB.getDefSolution());
    }
    
}
