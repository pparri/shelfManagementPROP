package classes;

import java.util.*;

public class DistribucioKruskal {

    /*
     * Algorisme que busca la distribució òptima a partir de l'algorisme de Kruskal
      El resultat ha de ser un arbre d'expansió connex sense resultats, en el qual la 
     * suma de les similituds de les arestes que el formen, ha de ser màxim
    */

    static Map<String, double[]> Mapa; 
    static List<Aresta> Arestes; 
    static List<String> LlistaProductes; 

    /* 
     * Funció per a crear una instancia de l'algorisme
    */
    public DistribucioKruskal() {
        Mapa = new LinkedHashMap<>();
        Arestes = new ArrayList<>();
        LlistaProductes = new ArrayList<>();
    }

    /**
     * Métode per configurar el mapaCis
     * @param mapaCis Mapa que conte cada producte y el seu vector de similituds
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
    * Classe que representa una aresta entre dos productes amb una similitud
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
         * Funció que ens serveix per a ordenar les arestes de major a menor similitut.
         * 
         * @param other Segona aresta amb la que comparem.
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
     * Funció que troba el pare del grup al que perteneix node.
     * 
     * @param pare 
     * @param node Producte.
    */
    public static String find(Map<String, String> pare, String node) {
        if (!pare.get(node).equals(node)) { //si no hem arribat al node pare
            pare.put(node, find(pare, pare.get(node)));
            /* Si A --> B i B ---> C, find ens porta a C i actualitza el map */
        }
        return pare.get(node);
    }

    // Generar el MST utilitzant l'Algorisme de Kruskal
    public static List<Aresta> construirMST() {
        List<Aresta> mst = new ArrayList<>();
        
        if (LlistaProductes.size() < 2) {
            throw new IllegalArgumentException("No es pot formar un MST amb menys de dos vèrtexs.\n");
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
     * Generem un cicle eulerià duplicant les arestes
     * Un cicle eulerià és un recorregut en un graf que passa exactament un cop per cada aresta
     * 
     * @param mst Minimum Spanning Tree generat per Kruskal
     */
    public static List<String> generaCicleEuleria(List<Aresta> mst) {
        Map<String, List<String>> connexions = new HashMap<>();
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
            connexions.get(v2).add(v1);   //conectem entre si els vertexs
        }

        for (List<String> neighbors : connexions.values()) {   //ordenem les connexions per ordre alfabètic
            Collections.sort(neighbors);
        }

        List<String> cicle = new ArrayList<>();
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

    /*
     * Convertir el ciclo Eulerià en un cicle Hamiltonià
    */
    public static ArrayList<String> generarPrestatge() {
        List<Aresta> mst = construirMST(); // Construir el MST
        List<String> cicloEuleriano = generaCicleEuleria(mst); // Generar cicle Euleria
        Set<String> visitados = new HashSet<>(); // Set de visitats
        ArrayList<String> resultat = new ArrayList<>();

        if(cicloEuleriano.isEmpty()) return resultat; 

        // Inicialitzem el primer producto agafant el primer del cicle Euleria
        String anterior = cicloEuleriano.get(0);
        resultat.add(anterior);
        visitados.add(anterior); //el marquem com a visitat

        //Recorrem la llista de productes possibles fins que el resultat tingui tots els productes únics
        while (resultat.size() < LlistaProductes.size()) {
            String mejorSiguiente = null;
            double maxSimilitud = -1;

            // Buscar el producte amb màxima similitud pel producte anterior
            for (String candidato : LlistaProductes) {
                if (!visitados.contains(candidato)) {
                    double similitud = Mapa.get(anterior)[LlistaProductes.indexOf(candidato)];
                    if (similitud > maxSimilitud) {
                        maxSimilitud = similitud;
                        mejorSiguiente = candidato;     // ens guardem el producte amb maxima similitud

                    }
                }
            }
            //Un cop hem recorregut la llista, afegim el producte al cicle hamiltonià
            if (mejorSiguiente != null) {
                resultat.add(mejorSiguiente);
                visitados.add(mejorSiguiente);
                anterior = mejorSiguiente;     //actualitzem anterior
            }
        }
        return resultat; 
    }           
}

    
