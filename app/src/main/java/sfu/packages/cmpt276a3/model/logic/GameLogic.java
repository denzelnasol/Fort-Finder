package sfu.packages.cmpt276a3.model.logic;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Does not work as of yet
 */
public class GameLogic{
    private int minesFound = 0;
    private int scansUsed = 0;

    public int getScansUsed() {
        return scansUsed;
    }

    public void setScansUsed(int scansUsed) {
        this.scansUsed = scansUsed;
    }
}
