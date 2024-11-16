package tests;

import classes.Prestatge;

import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

//assetEquals: Compara si dos objectes son iguals. Si no ho son, el test falla.
//assertNull: Comprova si un objecte és null. Si no ho és, el test falla.
//assertTrue: Comprova si una condició és certa. Si no ho és, el test falla.
//assertFalse: Comprova si una condició és falsa. Si no ho és, el test falla.
public class PrestatgeTest {
    
    /**
     * Test que comprova que es crea un Prestatge correctament.
     */
    @Test
    public void test_Prestatge() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        assertEquals(distPrestatgeIni, prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es crea un Prestatge correctament.
     */
    @Test
    public void test_PrestatgeNull() {
        Prestatge prestatge = new Prestatge(null);
        assertNull(prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es crea un Prestatge correctament.
     */
    @Test
    public void test_Prestatge3() {
        ArrayList<String> distPrestatgeSi = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        ArrayList<String> distPrestatgeNo = new ArrayList<>(Arrays.asList("N01", "NO2", "NO3"));
        Prestatge prestatgeSi = new Prestatge(distPrestatgeSi);
        Prestatge prestatgeNo = new Prestatge(distPrestatgeNo);
        assertNotEquals(prestatgeSi.getDistribucio(), prestatgeNo.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteBonCanvi() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("P1", "P2");
        assertEquals(1, resultat);
        assertEquals("P2", prestatge.getDistribucio().get(0));
        assertEquals("P1", prestatge.getDistribucio().get(1));
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNoEx1() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("PX", "P2");
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNoEx2() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("P1", "PX");
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNoExTots() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("PY", "PX");
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNMateixPro() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("P2", "P2");
        assertEquals(1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNP1Null() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte(null, "P2");
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNP2Null() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte("P3", null);
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es modifiquen les posicions de dos productes en el prestatge correctament.
     */
    @Test
    public void test_modificarSWProducteNTotsPNull() {
        ArrayList<String> distPrestatgeIni = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatgeIni);
        int resultat = prestatge.modificarSWProducte(null, null);
        assertEquals(-1, resultat);
        assertEquals(Arrays.asList("P1", "P2", "P3"), prestatge.getDistribucio());
    }

    /**
     * Test que comprova que es llista la distribució del prestatge correctament.
     */
    @Test
    public void test_llistarDistribucio() {
        ArrayList<String> distPrestatge = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
        Prestatge prestatge = new Prestatge(distPrestatge);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;  // Guardem la sortida original
        System.setOut(new PrintStream(outputStream));  // Redirigim System.out

        // Truquem al mètode que volem comprovar
        prestatge.llistarDistribucio();

        // Restablim la sortida estandar
        System.setOut(originalOut);

        // Obtenim la sortida capturada i la comparem amb lo esperat
        String output = outputStream.toString().trim();  // Eliminem espais en blanc al principi i al final
        String expectedOutput = "P1\nP2\nP3";  // Format esperat

        assertEquals(expectedOutput, output);
    }
}
