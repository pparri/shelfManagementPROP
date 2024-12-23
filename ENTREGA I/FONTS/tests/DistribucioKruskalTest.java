package tests;

import classes.AlgorismeFB;
import classes.DistribucioKruskal;
import classes.DistribucioKruskal.Aresta;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

//assertEquals: Compara si dos objectes son iguals. Si no ho son, el test falla.
//assertNotEquals: Compara si dos objectes no son iguals. Si ho son, el test falla.
//assertNull: Comprova si un objecte és null. Si no ho és, el test falla.
//assertTrue: Comprova si una condició és certa. Si no ho és, el test falla.
//assertFalse: Comprova si una condició és falsa. Si no ho és, el test falla.
public class DistribucioKruskalTest {

    /**
     * Test que comprova que es configura la cistella de productes correctament (el mapaCis).
     */
    @Test
    public void test_configurarMapaAmbVarisProductes() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();
        mapaCistella.put("Producte1", new ArrayList<>(Arrays.asList(1.0, 0.9, 0.7)));
        mapaCistella.put("Producte2", new ArrayList<>(Arrays.asList(0.9, 1.0, 0.3)));
        mapaCistella.put("Producte3", new ArrayList<>(Arrays.asList(0.7, 0.3, 1.0)));

        distribucioKruskal.configurarMapa(mapaCistella);

        assertEquals(3, distribucioKruskal.getMapa().size());
        assertArrayEquals(new double[]{1.0, 0.9, 0.7}, distribucioKruskal.getMapa().get("Producte1"), 0.0001);
        assertArrayEquals(new double[]{0.9, 1.0, 0.3}, distribucioKruskal.getMapa().get("Producte2"), 0.0001);
        assertArrayEquals(new double[]{0.7, 0.3, 1.0}, distribucioKruskal.getMapa().get("Producte3"), 0.0001);

        assertEquals(3, distribucioKruskal.getLlistaProductes().size());
        assertEquals("Producte1", distribucioKruskal.getLlistaProductes().get(0));
        assertEquals("Producte2", distribucioKruskal.getLlistaProductes().get(1));
        assertEquals("Producte3", distribucioKruskal.getLlistaProductes().get(2));
    }

    /**
     * Test que comprova que es configura la cistella de productes correctament (el mapaCis).
     */
    @Test
    public void test_configurarMapaAmbUnProducte() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();
        mapaCistella.put("Producte1", new ArrayList<>(Arrays.asList(1.0)));

        distribucioKruskal.configurarMapa(mapaCistella);

        assertEquals(1, distribucioKruskal.getMapa().size());
        assertArrayEquals(new double[]{1.0}, distribucioKruskal.getMapa().get("Producte1"), 0.0001);

        assertEquals(1, distribucioKruskal.getLlistaProductes().size());
        assertEquals("Producte1", distribucioKruskal.getLlistaProductes().get(0));
    }

    /**
     * Test que comprova que es configura la cistella de productes correctament (el mapaCis).
     */
    @Test
    public void test_configurarMapaBuit() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();

        distribucioKruskal.configurarMapa(mapaCistella);

        assertTrue(distribucioKruskal.getMapa().isEmpty());

        assertTrue(distribucioKruskal.getLlistaProductes().isEmpty());
    }


    /**
     * Test que comprova que es construeixen les arestes correctament.
     */
    @Test
    public void test_construirArestesAmbVarisProductes() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        mapa.put("Producte1", new double[]{1.0, 0.9, 0.7});
        mapa.put("Producte2", new double[]{0.9, 1.0, 0.3});
        mapa.put("Producte3", new double[]{0.7, 0.3, 1.0});

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3"));

        distribucioKruskal.construirArestes();

        assertEquals(3, distribucioKruskal.getArestes().size());

        assertEquals("Producte1", distribucioKruskal.getArestes().get(0).producte1);
        assertEquals("Producte2", distribucioKruskal.getArestes().get(0).producte2);
        assertEquals(0.9, distribucioKruskal.getArestes().get(0).Similitud, 0.0001);

        assertEquals("Producte1", distribucioKruskal.getArestes().get(1).producte1);
        assertEquals("Producte3", distribucioKruskal.getArestes().get(1).producte2);
        assertEquals(0.7, distribucioKruskal.getArestes().get(1).Similitud, 0.0001);

        assertEquals("Producte2", distribucioKruskal.getArestes().get(2).producte1);
        assertEquals("Producte3", distribucioKruskal.getArestes().get(2).producte2);
        assertEquals(0.3, distribucioKruskal.getArestes().get(2).Similitud, 0.0001);
    }

    /**
     * Test que comprova que es construeixen les arestes correctament.
     */
    @Test
    public void test_construirArestesAmbUnProducte() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        mapa.put("Producte1", new double[]{1.0});
        
        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1"));

        distribucioKruskal.construirArestes();

        assertTrue(distribucioKruskal.getArestes().isEmpty());
    }

    /**
     * Test que comprova que es construeixen les arestes correctament.
     */
    @Test
    public void test_construirArestesBuit() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<String> llistaP = new ArrayList<>();

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(llistaP);

        distribucioKruskal.construirArestes();

        assertTrue(distribucioKruskal.getArestes().isEmpty());
    }

    /**
     * Test que comprova que es construeixen les arestes correctament.
     */
    @Test
    public void test_construirArestesAmbSimilitutsRepetides() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        mapa.put("Producte1", new double[]{1.0, 0.4, 0.4});
        mapa.put("Producte2", new double[]{0.4, 1.0, 0.4});
        mapa.put("Producte3", new double[]{0.4, 0.4, 1.0});

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3"));

        distribucioKruskal.construirArestes();

        assertEquals(3, distribucioKruskal.getArestes().size());

        assertEquals("Producte1", distribucioKruskal.getArestes().get(0).producte1);
        assertEquals("Producte2", distribucioKruskal.getArestes().get(0).producte2);
        assertEquals(0.4, distribucioKruskal.getArestes().get(0).Similitud, 0.0001);

        assertEquals("Producte1", distribucioKruskal.getArestes().get(1).producte1);
        assertEquals("Producte3", distribucioKruskal.getArestes().get(1).producte2);
        assertEquals(0.4, distribucioKruskal.getArestes().get(1).Similitud, 0.0001);

        assertEquals("Producte2", distribucioKruskal.getArestes().get(2).producte1);
        assertEquals("Producte3", distribucioKruskal.getArestes().get(2).producte2);
        assertEquals(0.4, distribucioKruskal.getArestes().get(2).Similitud, 0.0001);
    }


    /**
     * Test que comprova 
     */
    @Test
    public void test_find1() {  //Node que és el seu propi pare
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, String> pare = new HashMap<>();
        pare.put("Producte1", "Producte1");

        assertEquals("Producte1", distribucioKruskal.find(pare, "Producte1"));
    }

    /**
     * Test que comprova 
     */
    @Test
    public void test_find2() {  //Node del qual el pare és un altre
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, String> pare = new HashMap<>();
        pare.put("Producte1", "Producte2");
        pare.put("Producte2", "Producte2");

        assertEquals("Producte2", distribucioKruskal.find(pare, "Producte1"));
        assertEquals("Producte2", pare.get("Producte1"));
    }

    /**
     * Test que comprova 
     */
    @Test
    public void test_find3() {  //Estructura en cadena (A -> B -> C)
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, String> pare = new HashMap<>();
        pare.put("Producte1", "Producte2");
        pare.put("Producte2", "Producte3");
        pare.put("Producte3", "Producte3");

        assertEquals("Producte3", distribucioKruskal.find(pare, "Producte1"));
        assertEquals("Producte3", pare.get("Producte1"));
        assertEquals("Producte3", pare.get("Producte2"));
    }

    /**
     * Test que comprova 
     */
    @Test
    public void test_find4() {  //Estructura en arbre (A -> B, B -> C, D -> C)
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, String> pare = new HashMap<>();
        pare.put("Producte1", "Producte2");
        pare.put("Producte2", "Producte3");
        pare.put("Producte3", "Producte3");
        pare.put("Producte4", "Producte3");

        assertEquals("Producte3", distribucioKruskal.find(pare, "Producte1"));
        assertEquals("Producte3", pare.get("Producte1"));
        assertEquals("Producte3", pare.get("Producte2"));

        assertEquals("Producte3", distribucioKruskal.find(pare, "Producte4"));
        assertEquals("Producte3", pare.get("Producte4"));
    }

    /**
     * Test que comprova 
     */
    @Test
    public void test_find5() {  //Node inexistent
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, String> pare = new HashMap<>();
        pare.put("Producte1", "Producte1");
        assertThrows(NullPointerException.class, () -> distribucioKruskal.find(pare, "ProducteX"));
    }


    /**
     * Test que comprova que es construeix el MST de l'algorisme Kruskal correctament.
     */
    @Test
    public void test_construirMSTGrafBuit() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<String> llistaP = new ArrayList<>();
        List<Aresta> arestes = new ArrayList<>();

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(llistaP);
        distribucioKruskal.setArestes(arestes);

        assertThrows(IllegalArgumentException.class, () -> distribucioKruskal.construirMST());
    } 

    /**
     * Test que comprova que es construeix el MST de l'algorisme Kruskal correctament.
     */
    @Test
    public void test_construirMSTAmbUNSolNode() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<Aresta> arestes = new ArrayList<>();

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setArestes(arestes);

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1"));

        assertThrows(IllegalArgumentException.class, () -> distribucioKruskal.construirMST());
    }

    /**
     * Test que comprova que es construeix el MST de l'algorisme Kruskal correctament.
     */
    @Test
    public void test_construirMSTAmbDosNodesConnectats() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<String> llistaP = new ArrayList<>();
        List<Aresta> arestes = new ArrayList<>();

        mapa.put("Producte1", new double[]{1.0, 0.0});
        mapa.put("Producte2", new double[]{0.0, 1.0});
        llistaP.add("Producte1"); 
        llistaP.add("Producte2");
        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 1.0));

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(llistaP);
        distribucioKruskal.setArestes(arestes);

        List<Aresta> mst = distribucioKruskal.construirMST();
        assertEquals(1, mst.size());
        assertEquals("Producte1", mst.get(0).producte1);
        assertEquals("Producte2", mst.get(0).producte2);
        assertEquals(1.0, mst.get(0).Similitud, 0.0001);
    }

    /**
     * Test que comprova que es construeix el MST de l'algorisme Kruskal correctament.
     */
    @Test
    public void test_construirMSTAmbTresNodesConnectats() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<Aresta> arestes = new ArrayList<>();

        mapa.put("Producte1", new double[]{1.0, 0.9, 0.7});
        mapa.put("Producte2", new double[]{0.9, 1.0, 0.3});
        mapa.put("Producte3", new double[]{0.7, 0.3, 1.0});

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3"));

        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.9));
        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte3", 0.7));
        arestes.add(new DistribucioKruskal.Aresta("Producte2", "Producte3", 0.3));
        distribucioKruskal.setArestes(arestes);

        List<Aresta> mst = distribucioKruskal.construirMST();

        assertEquals(2, mst.size());
        assertEquals("Producte1", mst.get(0).producte1);
        assertEquals("Producte2", mst.get(0).producte2);
        assertEquals(0.9, mst.get(0).Similitud, 0.0001);
    }

     /**
     * Test que comprova que es construeix el MST de l'algorisme Kruskal correctament.
     */
    @Test
    public void test_construirMSTAmbQuatreNodesGrafComplet() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, double[]> mapa = new LinkedHashMap<>();
        List<Aresta> arestes = new ArrayList<>();

        mapa.put("Producte1", new double[]{1.0, 0.9, 0.7, 0.2});
        mapa.put("Producte2", new double[]{0.9, 1.0, 0.3, 0.4});
        mapa.put("Producte3", new double[]{0.7, 0.3, 1.0, 0.1});
        mapa.put("Producte4", new double[]{0.2, 0.4, 0.1, 1.0});

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3", "Producte4"));

        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.9));
        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte3", 0.7));
        arestes.add(new DistribucioKruskal.Aresta("Producte1", "Producte4", 0.2));
        arestes.add(new DistribucioKruskal.Aresta("Producte2", "Producte3", 0.3));
        arestes.add(new DistribucioKruskal.Aresta("Producte2", "Producte4", 0.4));
        arestes.add(new DistribucioKruskal.Aresta("Producte3", "Producte4", 0.1));
        distribucioKruskal.setArestes(arestes);

        List<Aresta> mst = distribucioKruskal.construirMST();

        assertEquals(3, mst.size());
    }


    /**
     * Test que comprova que el cicle eulerià es genera correctament.
     */
    @Test
    public void test_generaCicleEuleria1() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2"));

        List<Aresta> mst = new ArrayList<>();
        mst.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte2", "Producte1", 0.0));

        assertThrows(IllegalArgumentException.class, () -> distribucioKruskal.generaCicleEuleria(mst));
    }

    /**
     * Test que comprova que el cicle eulerià es genera correctament.
     */
    @Test
    public void test_generaCicleEuleriaAmbVarisProductes() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3", "Producte4"));

        List<Aresta> mst = new ArrayList<>();
        mst.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte1", "Producte3", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte2", "Producte4", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte3", "Producte4", 0.0));

        List<String> cicle = distribucioKruskal.generaCicleEuleria(mst);

        assertEquals(5, cicle.size());
        //comprovem que comença i acaba en el mateix node
        assertEquals(cicle.get(0), cicle.get(cicle.size() - 1));
    }

    /**
     * Test que comprova que el cicle eulerià es genera correctament.
     */
    @Test
    public void test_generaCicleEuleriaAmbArestesDuplicades() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2", "Producte3"));

        List<Aresta> mst = new ArrayList<>();
        mst.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte2", "Producte3", 0.0));
        mst.add(new DistribucioKruskal.Aresta("Producte3", "Producte1", 0.0));

        List<String> cicle = distribucioKruskal.generaCicleEuleria(mst);

        assertEquals(4, cicle.size());
        //comprovem que comença i acaba en el mateix node
        assertEquals(cicle.get(0), cicle.get(cicle.size() - 1));
    }

    /**
     * Test que comprova que el cicle eulerià es genera correctament.
     */
    @Test
    public void test_generaCicleEuleriaSenseCicle() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2"));

        List<Aresta> mst = new ArrayList<>();
        mst.add(new DistribucioKruskal.Aresta("Producte1", "Producte2", 0.0));

        assertThrows(IllegalArgumentException.class, () -> distribucioKruskal.generaCicleEuleria(mst));
    }


    /**
     * Test que comprova que es genera el prestatge definitiu correctament.
     */
    @Test
    public void test_generarPrestatge1() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();
        mapaCistella.put("Producte1", new ArrayList<>(Arrays.asList(1.0, 0.9, 0.7)));
        mapaCistella.put("Producte2", new ArrayList<>(Arrays.asList(0.9, 1.0, 0.3)));
        mapaCistella.put("Producte3", new ArrayList<>(Arrays.asList(0.7, 0.3, 1.0)));

        distribucioKruskal.configurarMapa(mapaCistella);

        ArrayList<String> prestatge = distribucioKruskal.generarPrestatge();
        assertEquals(3, prestatge.size());
        assertTrue(prestatge.contains("Producte1"));
        assertTrue(prestatge.contains("Producte2"));
        assertTrue(prestatge.contains("Producte3"));
    }

    /**
     * Test que comprova que es genera el prestatge definitiu correctament.
     */
    @Test
    public void test_generarPrestatgeAmbMesProductes() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();
        mapaCistella.put("Producte1", new ArrayList<>(Arrays.asList(1.0, 0.9, 0.7, 0.8)));
        mapaCistella.put("Producte2", new ArrayList<>(Arrays.asList(0.9, 1.0, 0.3, 0.9)));
        mapaCistella.put("Producte3", new ArrayList<>(Arrays.asList(0.7, 0.3, 1.0, 0.2)));
        mapaCistella.put("Producte4", new ArrayList<>(Arrays.asList(0.8, 0.9, 0.2, 1.0)));

        distribucioKruskal.configurarMapa(mapaCistella);

        ArrayList<String> prestatge = distribucioKruskal.generarPrestatge();
        assertEquals(4, prestatge.size());
        assertTrue(prestatge.contains("Producte1"));
        assertTrue(prestatge.contains("Producte2"));
        assertTrue(prestatge.contains("Producte3"));
        assertTrue(prestatge.contains("Producte4"));
    }

    /**
     * Test que comprova que es genera el prestatge definitiu correctament.
     */
    @Test
    public void test_generarPrestatgeAmbUnSolProducte() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        Map<String, ArrayList<Double>> mapaCistella = new LinkedHashMap<>();
        mapaCistella.put("Producte1", new ArrayList<>(Arrays.asList(1.0)));

        distribucioKruskal.configurarMapa(mapaCistella);

        ArrayList<String> prestatge = distribucioKruskal.generarPrestatge();

        assertEquals(1, prestatge.size());
        assertEquals("Producte1", prestatge.get(0));
    }


    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcAmbDosProductes() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        ArrayList<String> solucio = new ArrayList<>();
        solucio.add("Producte1");
        solucio.add("Producte2");

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1", "Producte2"));

        Map<String, double[]> mapa = new LinkedHashMap<>();

        mapa.put("Producte1", new double[]{1.0, 0.9});
        mapa.put("Producte2", new double[]{0.9, 1.0});

        distribucioKruskal.setMapa(mapa);

        double score = distribucioKruskal.scoreCalc(solucio);

        assertEquals(1.8, score, 0.0001);
    }

    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcAmbUnProducte() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        ArrayList<String> solucio = new ArrayList<>();
        solucio.add("Producte1");

        distribucioKruskal.setLlistaProductes(Arrays.asList("Producte1"));

        Map<String, double[]> mapa = new LinkedHashMap<>();
        mapa.put("Producte1", new double[]{1.0});
        distribucioKruskal.setMapa(mapa);

        double score = distribucioKruskal.scoreCalc(solucio);

        assertEquals(1.0, score, 0.0001);
    }

    /**
     * Test que comprova que el càlcul de la puntuació del prestatge és correcte.
     */
    @Test
    public void test_scoreCalcBuit() {
        DistribucioKruskal distribucioKruskal = new DistribucioKruskal();

        ArrayList<String> solucio = new ArrayList<>();

        Map<String, double[]> mapa = new LinkedHashMap<>();

        distribucioKruskal.setMapa(mapa);
        distribucioKruskal.setLlistaProductes(Arrays.asList());

        double score = distribucioKruskal.scoreCalc(solucio);

        assertEquals(0.0, score, 0.0001);
    }
}