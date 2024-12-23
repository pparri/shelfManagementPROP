package drivers;

import controladors.CtrlPresentacio;

/**
 * Driver del Control de Presentació.
 */
public class DriverCtrl_Presentacio {
    public static void main(String[] args) {
        CtrlPresentacio cp = new CtrlPresentacio(false);
        cp.main(args);
    }
}
