package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

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

}
