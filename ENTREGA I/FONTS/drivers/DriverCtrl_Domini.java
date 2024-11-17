package drivers;

import controladors.CtrlDomini;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverCtrl_Domini
{
    private static CtrlDomini dom_ctrl = null;
    protected static Scanner sc = null;
    public static int num_opts = 10;
    private static int s = 0;
    private static int d = 0;

    private static void init_ctrl()
    {
        dom_ctrl = CtrlDomini.getInstancia();
        sc = new Scanner(System.in);
    }

    private static void mostrarVista()
    {
        System.out.println("1. Afegir producte(s).");
        System.out.println("2. Eliminar producte.");
        System.out.println("3. Modificar grau similitud.");
        System.out.println("4. Generar prestatge FB.");
        System.out.println("5. Generar prestatge 2-A.");
        System.out.println("6. Executar i mostrar DFS.");
        System.out.println("7. Executar i mostrar Kruskal.");
        System.out.println("8. Consultar prestatge.");
        System.out.println("9. Consultar cistella.");
        System.out.println("10. Sortir.\n");
    }

    public static void main(String[] args)
    {
        init_ctrl();
        System.out.println("Driver de testeig del CONTROLADOR DE DOMINI!\n");

        int opcio;
        while (true) {
            mostrarVista();
            System.out.print("Tria una opció: ");
            opcio = sc.nextInt();
            sc.nextLine();

            if (opcio < 1 || opcio > num_opts) {
                System.out.println("Opció no vàlida!!\n");
                continue;
            }

            switch(opcio) {
                case 1:
                    afegirProducte();
                    break;
                case 2:
                    eliminarProducte();
                    break;
                case 3:
                    modificarGrauSimilitud();
                    break;
                case 4:
                    try {
                        dom_ctrl.generarPrestatgeForcaBruta();
                        System.out.println("\nSUCCESS: Prestatge creat.\n");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        dom_ctrl.generarPrestatgeDosAprox();
                        System.out.println("\nSUCCESS: Prestatge creat.\n");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        dom_ctrl.generarEuler();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        dom_ctrl.generarKruskal();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        ArrayList<String> prestatge = dom_ctrl.consultarPrestatge();
                        System.out.println("El contingut del Prestatge es el seguent: \n");
                        System.out.println("_______________________________________________________________________________________________________________");
                        for (String element : prestatge) {
                            System.out.print("|" + element + "|");
                        }
                        System.out.print("\n");
                        System.out.print("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
                        System.out.print("\n");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        dom_ctrl.consultarCistella();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 10:
                    System.out.print("Moltes gràcies per provar el DRIVER de DOMINI!\n");
                    return;
            }
        }
    }

    private static void afegirProducte() 
    {
        int metodeEntrada = 0;
        boolean validM = false;
        while (!validM)
        {
            System.out.print("Afegir els productes a mà (1) o des d'un fitxer (2)? ");
            if (sc.hasNextInt())
            {
                metodeEntrada = sc.nextInt();
                validM = true;
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

        if (metodeEntrada == 2) {
            if (d == 1) {
                System.out.println("Error: fitxer ja afegit");
            }
            else 
            {
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
                }
                try (Scanner fileScanner = new Scanner(fitxer)) {
                    id = new String[c];
                    sims = new Double[c][c+s];
                    for (int i = 0; i < c; ++i) {
                        id[i] = fileScanner.nextLine().trim();
                        String[] simArray = fileScanner.nextLine().split(" ");
                        for (int j = 0; j < simArray.length; j++) {
                            sims[i][j] = Double.parseDouble(simArray[j]);
                        }
                    }
                    s = dom_ctrl.afegirProducte(id, sims, c);
                    System.out.println("\nSUCCESS: Producte creat.\n");
                    d = 1;
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
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
            if (N > 0)
            {
                sc.nextLine();
                id = new String[N];
                sims = new Double[N][N+s];
                for (int i = 0; i < N; ++i) {
                    System.out.print("Introdueix el ID del producte: " + (i + 1) + "\n");
                    id[i] = sc.nextLine().trim();
                    System.out.print("Introdueix les similituds per al producte " + id[i] + " (separades per espai): ");
                    String[] simArray = sc.nextLine().split(" ");
                    if (simArray.length != s+N)
                    {
                        System.out.println("Error: Falten o sobren similituds per al producte: "+id[i]);
                        System.out.print("\n");
                        return;
                    }
                    for (int j = 0; j < simArray.length; j++) {
                        sims[i][j] = Double.parseDouble(simArray[j]);
                    }
                }
                try {
                    s = dom_ctrl.afegirProducte(id, sims, N);
                    System.out.println("\nSUCCESS: Producte(s) creat(s).\n");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            else System.out.println("Error: S'han de indicar 1 o més productes.\n");
        }
        else System.out.println("Error: Mètode no vàlid.\n");
    }

    private static void eliminarProducte() 
    {
        int metodeEntrada = 0;
        boolean validM = false;
        while (!validM)
        {
            System.out.print("Eliminar el producte a mà (1) o des d'un fitxer (2)? ");
            if (sc.hasNextInt())
            {
                metodeEntrada = sc.nextInt();
                validM = true;
            }
            else
            {
                System.out.println("Error: No has introduït un enter.");
                sc.next();
            }
        }
        sc.nextLine();
        if (metodeEntrada == 2) 
        {
            System.out.print("Tria els nodes escollits anteriorment -> {1,3,5,8,11}: ");
            String filepath = " ";
            int c = sc.nextInt();
            sc.nextLine();
            if (c == 1) filepath = "drivers/recursos/eliminarProducte1nodos.txt";
            else if (c == 3) filepath = "drivers/recursos/eliminarProducte3nodos.txt";
            else if (c == 5) filepath = "drivers/recursos/eliminarProducte5nodos.txt";
            else if (c == 8) filepath = "drivers/recursos/eliminarProducte8nodos.txt";
            else if (c == 11) filepath = "drivers/recursos/eliminarProducte11nodos.txt";
            File fitxer = new File("");
            try {
                fitxer = new File(filepath);
                if (!fitxer.exists()) throw new Exception("El fitxer no existeix.\n");   
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage());
            }
            try (Scanner fileScanner = new Scanner(fitxer)) {
                while (fileScanner.hasNextLine()) {
                    String id = fileScanner.nextLine().trim();
                    s = dom_ctrl.eliminarProducte(id);
                }
                System.out.println("\nSUCCESS: Producte eliminat.\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } 
        else if (metodeEntrada == 1)
        {
            System.out.print("Introdueix l'ID del producte a eliminar: ");
            String id = sc.nextLine();
            try {
                s = dom_ctrl.eliminarProducte(id);
                System.out.println("\nSUCCESS: Producte eliminat.\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        else System.out.println("Error: Mètode no vàlid.\n");
    }

    private static void modificarGrauSimilitud() 
    {
        int metodeEntrada = 0;
        boolean validM = false;
        while (!validM)
        {
            System.out.print("Modificar els productes a mà (1) o des d'un fitxer (2)? ");
            if (sc.hasNextInt())
            {
                metodeEntrada = sc.nextInt();
                validM = true;
            }
            else
            {
                System.out.println("Error: No has introduït un enter.");
                sc.next();
            }
        }  
        sc.nextLine();
        if (metodeEntrada == 2)
        {
            System.out.print("Tria els nodes escollits anteriorment -> {1,3,5,8,11}: ");
            String filepath = " ";
            int c = sc.nextInt();
            sc.nextLine();
            if (c == 1) filepath = "drivers/recursos/modificarGrauSimilitud1nodos.txt";
            else if (c == 3) filepath = "drivers/recursos/modificarGrauSimilitud3nodos.txt";
            else if (c == 5) filepath = "drivers/recursos/modificarGrauSimilitud5nodos.txt";
            else if (c == 8) filepath = "drivers/recursos/modificarGrauSimilitud8nodos.txt";
            else if (c == 11) filepath = "drivers/recursos/modificarGrauSimilitud11nodos.txt";
            File fitxer = new File("");
            try {
                fitxer = new File(filepath);
                if (!fitxer.exists()) throw new Exception("El fitxer no existeix.\n");   
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage());
            }
            try (Scanner fileScanner = new Scanner(fitxer)) {
                while (fileScanner.hasNextLine()) {
                    String prodA = fileScanner.nextLine().trim();
                    String prodB = fileScanner.nextLine().trim();
                    double sim = Double.parseDouble(fileScanner.nextLine().trim());
                    dom_ctrl.modificarGrauSimil(prodA, prodB, sim);
                }
                System.out.println("\nSUCCESS: Similitud modificada.\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } 
        else if (metodeEntrada == 1)
        {
            System.out.print("Introdueix l'ID del primer producte: ");
            String prodA = sc.nextLine();
            System.out.print("Introdueix l'ID del segon producte: ");
            String prodB = sc.nextLine();
            System.out.print("Introdueix el nou grau de similitud: ");
            double sim = sc.nextDouble();
            try {
                dom_ctrl.modificarGrauSimil(prodA, prodB, sim);
                System.out.println("\nSUCCESS: Similitud modificada.\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        else System.out.println("Error: Mètode no vàlid.\n");
    }
}
