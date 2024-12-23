package classes;

import java.util.*;

/**
 * Representa l'algorisme que busca la distribució òptima a partir de l'algorisme de Kruskal.
 * 
 * El resultat ha de ser un arbre d'expansió connex sense resultats, en el qual la 
 *      suma de les similituds de les arestes que el formen, ha de ser màxim.
 */

public class DistribucioKruskal {

    static Map<String, double[]> Mapa; 
    static List<Aresta> Arestes; 
    static List<String> LlistaProductes; 

    /* 
     * Constructor de la classe DistribucioKruskal.
    */
    public DistribucioKruskal() {
        Mapa = new LinkedHashMap<>();
        Arestes = new ArrayList<>();
        LlistaProductes = new ArrayList<>();
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
            Mapa.put(producte, similitudesArray);
            LlistaProductes.add(producte);
        }
        construirArestes();      
    }


    /**
     * Retorna la cistella de productes (Mapa).
     * 
     * @return Map<String, double[]>.
     */
    public static Map<String, double[]> getMapa() {
        return Mapa;
    }

    /**
     * Retorna la llista d'arestes (Arestes).
     * 
     * @return List<Aresta>.
     */
    public static List<Aresta> getArestes() {
        return Arestes;
    }

    /**
     * Retorna la llista de productes (LlistaProductes).
     * 
     * @return List<String>.
     */
    public static List<String> getLlistaProductes() {
        return LlistaProductes;
    }

    /**
     * Funció per inicialitzar la variable Mapa (pels tests).
     * 
     * @param map Estructura a la que volem inicialitzar la variable Mapa pels tests.
     */
    public static void setMapa(Map<String, double[]> map) {
        Mapa = map;
    }

    /**
     * Funció per inicialitzar la variable LlistaProductes (pels tests).
     * 
     * @param llistaP Estructura a la que volem inicialitzar la variable LlistaProductes pels tests.
     */
    public static void setLlistaProductes(List<String> llistaP) {
        LlistaProductes = llistaP;
    }

    /**
     * Funció per inicialitzar la variable Arestes (pels tests).
     * 
     * @param arests Estructura a la que volem inicialitzar la variable Arestes pels tests.
     */
    public static void setArestes(List<Aresta> arests) {
        Arestes = arests;
    }

    /**
    * Classe que representa una aresta entre dos productes amb una similitud.
    */
    public static class Aresta implements Comparable<Aresta> {    

        public String producte1;
        public String producte2;
        public double Similitud;

        /**
         * Constructor de la classe Aresta.
         * 
         * @param producte1 Nom del producte1.
         * @param producte2 Nom del producte2.
         * @param Similitud Similitud entre el producte1 i el producte2.
        */
        public Aresta(String producte1, String producte2, double Similitud) {
            this.producte1 = producte1;
            this.producte2 = producte2;
            this.Similitud = Similitud;
        }

        @Override

        /**
         * Funció que ens serveix per a ordenar les arestes de major a menor similitud.
         * 
         * @param other Segona aresta amb la que comparem.
         * @return Integer.
         */
        public int compareTo(Aresta other) {
            return Double.compare(other.Similitud, this.Similitud); 
        }
    }

    /**
     * Construcció d'arestes basada en la matriu de similituds.
     */
    public static void construirArestes() {
        Arestes = new ArrayList<>();
        int N = LlistaProductes.size();
        for (int i = 0; i < N; ++i) {
            for (int j = i + 1; j < N; ++j) {
                String producte1 = LlistaProductes.get(i);
                String producte2 = LlistaProductes.get(j);
                int index2 = LlistaProductes.indexOf(producte2);
                double Similitud = Mapa.get(producte1)[index2]; // Similitud entre producte1 y producte2
                Arestes.add(new Aresta(producte1, producte2, Similitud));
            }
        }
        Collections.sort(Arestes); // Ordenar arestes per similitud (de major a menor)

    }


    /**
     * Funció que troba el pare del grup al que perteneix el node.
     * 
     * @param pare Mapa on es busca el node pare.
     * @param node Producte.
     * @return String.
    */
    public static String find(Map<String, String> pare, String node) {
        if (!pare.get(node).equals(node)) { //si no hem arribat al node pare
            pare.put(node, find(pare, pare.get(node)));
            /* Si A --> B i B ---> C, find ens porta a C i actualitza el map */
        }
        return pare.get(node);
    }

    /**
     * Funció que genera el MST utilitzant l'algorisme de Kruskal.
     * 
     * @return List<Aresta> (llista de les arestes).
     */
    public static List<Aresta> construirMST() {
        List<Aresta> mst = new ArrayList<>();

        if (LlistaProductes.size() < 2) {
            throw new IllegalArgumentException("No es pot formar un MST amb menys de dos vèrtexs.\n");
            //return mst;
        }
        
        Map<String, String> pare = new HashMap<>(); //Mapa per emmagatzemar el grup "pare" de l'element
        //producte --> pare

        // Inicialitzar cada producte en el seu propi conjunt
        for (String producte : LlistaProductes) {
            pare.put(producte, producte);
        }


        // Construir MST afegint les arestes ordenades sense crear un cicle
        for (Aresta aresta : Arestes) {
            String root1 = find(pare, aresta.producte1);
            String root2 = find(pare, aresta.producte2);
            if (!root1.equals(root2)) {
                mst.add(aresta);
                pare.put(root1, root2);
                if (mst.size() == LlistaProductes.size() - 1) break;
            }
        }
        return mst;
    }


    /**
     * Generem un cicle eulerià duplicant les arestes.
     * Un cicle eulerià és un recorregut en un graf que passa exactament un cop per cada aresta.
     * 
     * @param mst List<String> (Minimum Spanning Tree generat per Kruskal).
     * @return List<String>.
     */
    public static List<String> generaCicleEuleria(List<Aresta> mst) {
        List<String> cicle = new ArrayList<>();
        
        if (LlistaProductes.size() < 3) {
            throw new IllegalArgumentException("No es pot formar un cicle Eulerià amb menys de dos vèrtexs.\n");
        }
        
        Map<String, List<String>> connexions = new LinkedHashMap<>();
        for (Aresta aresta : mst) { //creem mapa de connexions duplicant les arestes
            connexions.putIfAbsent(aresta.producte1, new ArrayList<>());
            connexions.putIfAbsent(aresta.producte2, new ArrayList<>());
            connexions.get(aresta.producte1).add(aresta.producte2);
            connexions.get(aresta.producte2).add(aresta.producte1);
        }
        //Identifiquem els productes amb un grau imparell 
        List<String> vertexImparells = new ArrayList<>();
        for (Map.Entry<String, List<String>> prod : connexions.entrySet()) {
            if (prod.getValue().size() % 2 != 0) {
                vertexImparells.add(prod.getKey());
            }
        }

        // Afegim els vertexs imparells a la matriu de connexions
        for (int i = 0; i < vertexImparells.size(); i += 2) {
            String v1 = vertexImparells.get(i);
            String v2 = vertexImparells.get(i + 1);
            connexions.get(v1).add(v2);
            connexions.get(v2).add(v1);     //conectem entre si els vertexs
        }

        for (List<String> neighbors : connexions.values()) {        //ordenem les connexions per ordre alfabètic
            Collections.sort(neighbors);
        }

        Stack<String> stack = new Stack<>();
        stack.push(mst.get(0).producte1);

        // Generem cicle fent un recorregut en DFS
        while (!stack.isEmpty()) {
            String node = stack.peek(); //agafem el top de la pila
            if (!connexions.get(node).isEmpty()) {  //si te connexions les afegim a la pila : A-->B
                String next = connexions.get(node).remove(0);  //Borrem connexio A-->B
                connexions.get(next).remove(node);  //  //Borrem connexio B-->A
                stack.push(next); //push de B
            } else {
                cicle.add(stack.pop());     //si el node no te connexions, s'afegeix al cicle i s'esborra de la pila
            }
        }
        return cicle;
    }


    /**
     * Funció per generar la solució òptima del prestatge, executa l'algorisme de Kruskal.
     * Per fer-ho converteix el cicle Eulerià en un cicle Hamiltonià.
     * 
     * @return Un ArrayList<String> amb l'ordre de productes que maximitza la similitud total.
     */
    public static ArrayList<String> generarPrestatge() {
        ArrayList<String> resultatfinal = new ArrayList<>();
        
        
        // Verifica si no hi ha cap producte
        if (LlistaProductes.isEmpty()) {
            return resultatfinal; // Devuelve una lista vacía si no hay productos
        }
        
        // si només hi ha un producte, l'afegim al resultat
        else if (LlistaProductes.size() == 1) {
            resultatfinal.add(LlistaProductes.get(0));
            return resultatfinal;
        }
        
        List<Aresta> mst = construirMST(); // Construir el MST
        List<String> cicloEuleriano = generaCicleEuleria(mst); // Generar cicle Euleria
        double maxSimil = -1.0;
        
        for (int i = 0; i < cicloEuleriano.size(); ++i) {   //
    
            ArrayList<String> resultat = new ArrayList<>();
            Set<String> visitados = new HashSet<>(); // Set de visitats
            for (int j = 0; j < cicloEuleriano.size(); ++j) {
                String node = cicloEuleriano.get((i+j)%cicloEuleriano.size());
                if (!visitados.contains(node)) {
                    resultat.add(node);
                    visitados.add(node);  // el marquem com a visitat
                }
            }
            double similitudActual = scoreCalc(resultat);
            if (similitudActual > maxSimil) {
                maxSimil = similitudActual;
                resultatfinal = resultat;
            }
        }
        return resultatfinal;
    }

    /**
     * Calcula la puntuació d'una possible solució de distribució del prestatge.
     * 
     * @param solution Llista d'una possible solució de distribució del prestatge.
     * @return double.
     */
    public static double scoreCalc(ArrayList<String> solution) {
        double score = 0.0;
        int N = solution.size();
        for (int i = 0; i < N; ++i)
        {
            String producte1 = solution.get(i);
            String producte2 = solution.get((i+1)%N);
            int index2 = LlistaProductes.indexOf(producte2);
            double Similitud = Mapa.get(producte1)[index2];
            score += Similitud;
        }
        return score;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Número de productos: ");
        int N = scanner.nextInt();
        scanner.nextLine();

        DistribucioKruskal distribucio = new DistribucioKruskal();

        // Leer productos y similitudes
        for (int i = 0; i < N; ++i) {
            System.out.print("Producto " + (i + 1) + ": ");
            String clave = scanner.nextLine().trim();
            LlistaProductes.add(clave);
            double[] similitudes = new double[N];
            System.out.print("Similitudes del Producto " + (i + 1) + ": ");
            String lineaSimilitudes = scanner.nextLine().trim();
            String[] entradas = lineaSimilitudes.split("\\s+");
            for (int j = 0; j < N; ++j) {
                try {
                    similitudes[j] = Double.parseDouble(entradas[j]);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no válida");
                    return;
                }
            }
            Mapa.put(clave, similitudes); // Añadir al mapa de similitudes
        }

        // Construcción de aristas con similitud entre cada par de productos
        construirArestes();

        // Generar y mostrar la distribución circular
        ArrayList<String> resultat = generarPrestatge();
        System.out.println("\nDistribución circular de los productos:");
        for (String producto : resultat) {
            System.out.print(producto + " ");
        }
        System.out.println();

        scanner.close();
    }
}
