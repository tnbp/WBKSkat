package wbkskat;

import javax.swing.SwingUtilities;

public class WBKSkatRun {

/*
	Driver-Klasse für das WBKSkat-Spiel.
*/

    public static void main(String[] args) {
    	SkatController sc = new SkatController();
        SwingUtilities.invokeLater(() -> new SkatGUI(sc)); // Voodoo™
    }

}
