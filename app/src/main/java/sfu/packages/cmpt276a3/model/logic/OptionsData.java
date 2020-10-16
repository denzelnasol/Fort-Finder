package sfu.packages.cmpt276a3.model.logic;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Does not work as of yet
 */
public class OptionsData extends AppCompatActivity {
    /*
        Singleton Support
    */
    private static OptionsData instance;
    private OptionsData() {
        // Prevents instantiations
    }
    public static OptionsData getInstance() {
        if (instance == null) {
            instance = new OptionsData();
        }

        return instance;
    }

    /*
        Normal Object Code
    */

    private String BOARD_SIZE;
    private int NUM_MINES;

    public String getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public void setBOARD_SIZE(String BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
    }

    public int getNUM_MINES() {
        return NUM_MINES;
    }

    public void setNUM_MINES(int NUM_MINES) {
        this.NUM_MINES = NUM_MINES;
    }

}
