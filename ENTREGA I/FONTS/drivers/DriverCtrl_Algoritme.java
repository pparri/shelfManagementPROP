package drivers;

import controladors.CtrlAlgoritme;
import java.util.*;
import java.io.File;

import classes.DistribucioKruskal;
import classes.Producte;

/**
 * Driver Ctrl_Algoritme
 */
public class DriverCtrl_Algoritme
{
  private static CtrlAlgoritme alg_ctrl = null;
  protected static Scanner sc = null;
  public static int num_opts = 6;

  //Resultats algorismes
    static private ArrayList<String> prestatge;    //prestatge final obtingut a partir de FB / 2-aprox
    static private List<DistribucioKruskal.Aresta> mst; //resultat kruskal
    static private List<String> cicleEuleria; //resultat DFS


  /**
   * Inicialització del driver
   */
  private static void init_ctrl()
  {
    sc = new Scanner(System.in);

    Map<String, ArrayList<Double>> input = llegirInput();
    alg_ctrl = new CtrlAlgoritme(input);
    if (num_opts != 0) System.out.println("ESTRUCTURES CREADES\n");

  }

  /**
   * Mostra les opcions disponibles.
   */
  private static void mostrarVista()
  {

    System.out.println("1. Generar prestatge FB.");
    System.out.println("2. Generar prestatge 2-A.");
    System.out.println("3. Executar i mostrar DFS.");
    System.out.println("4. Executar i mostrar Kruskal.");
    System.out.println("5. Consultar Prestatge.");
    System.out.println("6. Sortir.\n");
  }

  /**
   * Funció que llegeix l'input.
   */
  private static Map<String,ArrayList<Double>> llegirInput() 
  {
    int metodeEntrada = 0;
    boolean validM = false;
    while (!validM)
    {
        System.out.print("Afegir els productes a mà (1) o des d'un fitxer (2)? ");
        if (sc.hasNextInt())
        {
            metodeEntrada = sc.nextInt();
            if (metodeEntrada == 1 || metodeEntrada == 2) validM = true;
            else System.out.println("Error: Mètode invàlid.");
        }
        else
        {
            System.out.println("Error: No has introduït un enter.");
            sc.next();
        }
    }

    int N;
    String[] id;
    Double[][] sims;
    Map<String,ArrayList<Double>> input = new LinkedHashMap<>();

    if (metodeEntrada == 2) {
      System.out.print("Tria la quantitat de nodes que vols -> {1,3,5,8,11}: ");
      String filepath = " ";
      int c = sc.nextInt();
      sc.nextLine();
      if (c == 1) filepath = "drivers/recursos/afegirProductes1nodos.txt";
      else if (c == 3) filepath = "drivers/recursos/afegirProductes3nodos.txt";
      else if (c == 5) filepath = "drivers/recursos/afegirProductes5nodos.txt";
      else if (c == 8) filepath = "drivers/recursos/afegirProductes8nodos.txt";
      else if (c == 11) filepath = "drivers/recursos/afegirProductes11nodos.txt";
      File fitxer = new File("");
      try {
          fitxer = new File(filepath);
          if (!fitxer.exists()) throw new Exception("El fitxer no existeix.\n");
      } catch (Exception e) {
          System.out.println("Error: "+e.getMessage());
          num_opts = 0;
      }
      try (Scanner fileScanner = new Scanner(fitxer)) {
          id = new String[c];
          sims = new Double[c][c];
          for (int i = 0; i < c; ++i) {
              id[i] = fileScanner.nextLine().trim();
              String[] simArray = fileScanner.nextLine().split(" ");
              for (int j = 0; j < simArray.length; j++) {
                  sims[i][j] = Double.parseDouble(simArray[j]);
              }
            ArrayList<Double> llistaSim = new ArrayList<>(Arrays.asList(sims[i]));
           input.put(id[i], llistaSim);
          }
      } catch (Exception e) {
          System.out.println("Error: " + e.getMessage());
      }
    } 
    else if (metodeEntrada == 1)
    {
      boolean valid = false;
      N = 0;
      while (!valid)
      {
          System.out.print("Introdueix la quantitat de productes a AFEGIR: ");
          if (sc.hasNextInt())
          {
              N = sc.nextInt();
              valid = true;
          }
          else
          {
              System.out.println("Error: No has introduït un enter.");
              sc.next();
          }
      }
      while (N < 1)
      {
        System.out.println("Error: S'han de indicar 1 o més productes.\n");
        System.out.print("Introdueix la quantitat de productes a AFEGIR: ");
        N = sc.nextInt();
      }
      sc.nextLine();
      id = new String[N];
      sims = new Double[N][N];
      for (int i = 0; i < N; ++i) {
          System.out.print("Introdueix el ID del producte: " + (i + 1) + "\n");
          id[i] = sc.nextLine().trim();
          System.out.print("Introdueix les similituds per al producte " + id[i] + " (separades per espai): ");
          String[] simArray = sc.nextLine().split(" ");
          
          if (simArray.length != N) {
              System.out.println("Error: Falten o sobren similituds per al producte:" + id[i]);
              System.out.print("\n");
              return input; 
          }
      
          for (int j = 0; j < N; j++) {
              try {
                  sims[i][j] = Double.parseDouble(simArray[j]);
              } catch (NumberFormatException e) {
                  System.out.println("Error: La similitud proporcionada no és un número vàlid: " + simArray[j]);
                  return input;
              }
          }
          ArrayList<Double> llistaSim = new ArrayList<>(Arrays.asList(sims[i]));
          input.put(id[i], llistaSim);
      }
    }
    else System.out.println("Error: Mètode invàlid.\n");
    return input;
  }

    /**
     * Mètode principal del Driver.
     *
     * @param args Arguments de la línia de comandes.
     */
    public static void main (String [] args)
    {
        //Inicialitzacio del Driver
        init_ctrl();
        if (num_opts == 0) return;
        System.out.println("Driver de testeig del CONTROLADOR DE ALGORITME!\n");
        int opcio = -1;
        boolean validG = false;
        while (true) {
            mostrarVista();
            System.out.print("Tria una opció: ");
            while (!validG)
            {
                if (sc.hasNextInt())
                {
                    opcio = sc.nextInt();
                    validG = true;
                }
                else
                {
                    System.out.println("Error: No has introduït un enter.");
                    System.out.print("Tria una opció: ");
                    sc.next();
                }
            }
            validG = false;
            sc.nextLine();
            switch(opcio)
            {
              case 1:
                  try {
                  prestatge = alg_ctrl.executarForcaBruta();
                    System.out.println("\nSUCCESS: Prestatge creat.\n");
                  } catch (Exception e){
                    System.out.println("Error: "+e.getMessage());
                  }
                  break;
              case 2:
                try {
                  prestatge = alg_ctrl.executarDosAprox();
                  System.out.println("\nSUCCESS: Prestatge creat.\n");
                  }catch (Exception e){
                      System.out.println("Error: "+e.getMessage());
                  }
                  break;
              case 3:
                try {
                  cicleEuleria = alg_ctrl.executarDFS();
                  mostrarCicleEuleria(cicleEuleria);
                 } catch (Exception e){
                  System.out.println("Error: "+e.getMessage());
                 }
                break;
              case 4:
                try {
                  mst = alg_ctrl.executarKruskal();
                  mostrarMST(mst);
                } catch (Exception e){
                    System.out.println("Error: "+e.getMessage());
                }
                break;

              case 5: 
              try {
                consultarPrestatge();
              } catch (Exception e){
                System.out.println("Error: "+e.getMessage());
              }
              break;
              
              case 6:
              System.out.print("Moltes gràcies per provar el DRIVER de ALGORITME!\n");
              return;

            default:
              System.out.println("Opció no vàlida!\n");
            }
        }

      }

/* ----------------------------------FUNCIONS CONSULTA------------------------------ */

    /**
     * Mètode que consulta el PRESTATGE, i el mostra a l'usuari.
     */
   static public void consultarPrestatge()
    {
        if (prestatge != null)
        {
          System.out.println("El contingut del Prestatge es el seguent: \n");
          System.out.println("_______________________________________________________________________________________________________________");
          for (String element : prestatge) {
              System.out.print("|" + element + "|");
          }
          System.out.print("\n");
          System.out.print("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
          System.out.print("\n");
        }
        else System.out.println("\nError: Primer genera un prestatge.\n");
	  }

     /**
     * Mètode que consulta el Arbre d'expansió mínima (KRUSKAL), i el mostra a l'usuari.
     */
    static public void mostrarMST(List<DistribucioKruskal.Aresta> mst) 
    {
        System.out.println("MST generat:\n");
        for (DistribucioKruskal.Aresta aresta : mst) {
            System.out.println(aresta.producte1 + " -- " + aresta.producte2 + " (Similitud: " + aresta.Similitud + ")");
        }
        System.out.print("\n");
    }

    /**
     * Mètode que consulta el Cicle Eulerià generat fent un DFS en base al Kruskal, i el mostra a l'usuari.
     */
    static public void mostrarCicleEuleria(List<String> cicloEuleriano) 
    {
        System.out.println("Ciclo Euleriano generat:");
        for (String producto : cicloEuleriano) {
            System.out.print(producto + " ");
        }
        System.out.println("\n");
    }

  }