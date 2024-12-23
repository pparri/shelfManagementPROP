package classes.domini;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Representa l'algorisme de força bruta que genera la millor solució possible per a la distribució del prestatge.
 * Proporciona mètodes per trobar aquesta solució.
 * 
 * Aquest algorisme genera un prestatge, que és el que mostra el sistema.
 */
public class AlgorismeFB
{
    private static Map<String,double[]> Shelf;
    private static String[] defSolution;
    private List<String> LlistaProductes;
    private static int iterador;
    private static double scoreMax;


    /**
     * Constructor de la classe AlgorismeFB.
     */
    public AlgorismeFB()
    {
        Shelf = new LinkedHashMap<>();
        LlistaProductes = new ArrayList<>();
        iterador = 0;
        scoreMax = 0.0;
    }

    /**
     * Métode per configurar el mapaCis (la cistella) y preparar la llista de productes.
     *
     * @param mapaCis Mapa que conté cada producte y el seu vector de similituds.
     */
    public void configurarMapa(Map<String, ArrayList<Double>> mapaCis)
    {
        for (Map.Entry<String, ArrayList<Double>> entry : mapaCis.entrySet()) {
            String producte = entry.getKey();
            ArrayList<Double> similituds = entry.getValue();
            double[] similitudesArray = similituds.stream().mapToDouble(Double::doubleValue).toArray();
            Shelf.put(producte, similitudesArray);
            LlistaProductes.add(producte);
        }
    }

    /**
     * Executa l'algoritme de Força Bruta per trobar la disposició òptima de productes.
     *
     * @return Un ArrayList<String> amb l'ordre de productes que maximitza la similitud total.
     */
    public ArrayList<String> generarPrestatge()
    {
        int N = LlistaProductes.size();
        defSolution = new String[N];
        String[] solution = new String[N];
        boolean[] used = new boolean[N];

        Arrays.fill(used, false);
        scoreMax = 0.0;
        backtracking(solution, used, 0, N);
        return new ArrayList<>(Arrays.asList(defSolution));
    }

    /**
     * Funció per buscar la posició del producte dins del prestatge.
     * 
     * @param product Producte del qual volem buscar la seva posició.
     * @return Integer.
     */
    public static int getIndex(String product)
    {
        int index = 0;
        for (String clave : Shelf.keySet())
        {
            if (clave.equals(product)) return index;
            ++index;
        }
        return -1;
    }

    /**
     * Retorna el prestatge (Shelf).
     * 
     * @return Map<String,double[]>.
     */
    public static Map<String,double[]> getShelf() {
        return Shelf;
    }

    /**
     * Retorna la llista de productes del sistema.
     * 
     * @return List<String>.
     */
    public List<String> getLlistaProductes() {
        return LlistaProductes;
    }

    /**
     * Retorna la defSolution.
     * 
     * @return String[].
     */
    public static String[] getDefSolution() {
        return defSolution;
    }

    /**
     * Retorna la puntuació màxima (scoreMax).
     * 
     * @return double.
     */
    public static double getScoreMax() {
        return scoreMax;
    }

    /**
     * Comprova que el producte que busquem es troba dins de la llista de productes.
     * 
     * @param producte Producte que volem comprovar que es troba en la LlistaProductes.
     * @return Integer.
     */
    public int getSiEstaProducteEnLlista(String producte) {
        for (String prod : LlistaProductes) {
            if (prod.equals(producte)) return 1;
        }
        return -1;
    }

    /**
     * Retorna les similituds del producte que busquem.
     * 
     * @param producte Producte del queal volem veure les seves similituds.
     * @return ArrayList<Double> (les similituds del producte).
     */
    public static ArrayList<Double> getSimilitudsPro(String producte) {
        double[] sim = {};
        for (String pro : Shelf.keySet()) {
            if (pro.equals(producte)) sim = Shelf.get(pro);
        }
        ArrayList<Double> similituds = Arrays.stream(sim).boxed().collect(Collectors.toCollection(ArrayList::new));
        return similituds;
    }


    /**
     * Calcula la puntuació d'una possible solució de distribució del prestatge.
     * 
     * @param solution Vector d'una possible solució de distribució del prestatge.
     * @param N Tamany del prestatge, és a dir la quantitat de productes que conté el prestatge.
     * @return double.
     */
    public static double scoreCalc(String[] solution, int N)
    {
        double score = 0.0;
        for (int i = 0; i < N; ++i)
        {
            String uno = solution[i];
            String dos = solution[(i+1)%N];
            double aux[] = Shelf.get(uno);
            score += aux[getIndex(dos)];
        }
        return score; 
    }

    /**
     * Calcula totes les possibles solucions de distribucions del prestatge, fins quedar-se amb la millor.
     * 
     * @param solution Vector d'una possible solució de distribució del prestatge.
     * @param used Vector d'utilitzats, per anar descartant solucions.
     * @param i Integer per distingir la iteració en la que ens trobem.
     * @param N Tamany del prestatge, és a dir la quantitat de productes que conté el prestatge.
     */
    public static void backtracking(String[] solution, boolean[] used, int i, int N)
    {
        if (i == N)
        {
            ++iterador;
            double scoreActual = scoreCalc(solution,N);
            if (scoreActual > scoreMax)
            {
                scoreMax = scoreActual;
                defSolution = solution.clone();
            }
            return;
        }
        else
        {
            for (String key : Shelf.keySet())
            {
                if (!used[getIndex(key)])
                {
                    solution[i] = key;
                    used[getIndex(key)] = true;
                    backtracking(solution,used,i+1,N);
                    used[getIndex(key)] = false;
                }
            }
        }
    }

    /**
     * Retorna l'iterador.
     * 
     * @return Integer.
     */
    public static int getIterador() {
        return iterador;
    }

    /**
     * Funció per inicialitzar la variable scoreMax (pels tests).
     * 
     * @param score Puntuació a la qual volem inicialitzar la millor puntuació en els tests.
     */
    public static void setScoreMax(double score) {
        scoreMax = score;
    }

    /**
     * Funció per inicialitzar la variable iterador (pels tests).
     * 
     * @param iter Iterador al qual volem inicialitzar l'iterador en els tests.
     */
    public static void setIterador(int iter) {
        iterador = iter;
    }

    /**
     * Funció per inicialitzar la variable defSolution (pels tests).
     * 
     * @param solucio Solució a la qual volem inicialitzar-la en els tests.
     */
    public static void setDefSolution(String[] solucio) {
        defSolution = solucio;
    }
}
