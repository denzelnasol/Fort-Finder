package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import sfu.packages.cmpt276a3.R;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        
        createMineRadioButtons();
        createBoardSizeRadioButton();
    }

    private void createBoardSizeRadioButton() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_board_size);
        String[] boardSizes = getResources().getStringArray(R.array.game_board_sizes);

        // Create the buttons
        for (int i = 0; i < boardSizes.length; i++) {
            String stringBoardSize = boardSizes[i];

            RadioButton button = new RadioButton(this);
            button.setText(stringBoardSize);

            group.addView(button);
        }
    }

    private void createMineRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_number_of_mines);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);

        // Create the buttons
        for (int i = 0; i < numMines.length; i++) {
            int numMineSize = numMines[i];

            RadioButton button = new RadioButton(this);
            button.setText(numMineSize + " Mines");

            group.addView(button);
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Options.class);
    }
}
