package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import sfu.packages.cmpt276a3.R;

// Displays options activity and allows user to change settings for the game
public class Options extends AppCompatActivity {
    private static final String NUM_MINES = "Number of Mines";
    private static final String BOARD_SIZE = "Board Size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setUpEraseButton(this);

        createMineRadioButtons();
        createBoardSizeRadioButton();

    }

    private void setUpEraseButton(Context context) {
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        Button button = findViewById(R.id.eraseTimesPlayedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slash.start();
                boolean eraseCheck = true;
                SharedPreferences prefs = Options.this.getSharedPreferences("ErasePrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("Erase Check", eraseCheck);
                editor.apply();
            }
        });
    }

    private void createBoardSizeRadioButton() {
        RadioGroup group = findViewById(R.id.radio_group_board_size);
        String[] boardSizes = getResources().getStringArray(R.array.game_board_sizes);

        // Create the buttons
        for (final String stringBoardSize : boardSizes) {
            RadioButton button = new RadioButton(this);
            button.setText(stringBoardSize);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBoardSize(stringBoardSize);
                }
            });

            // Add to radio group
            group.addView(button);

            // Select default button
            if (stringBoardSize.equals(getBoardSize(this))) {
                button.setChecked(true);
            }
        }
    }

    private void saveBoardSize(String boardSize) {
        SharedPreferences prefs = this.getSharedPreferences("BoardPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BOARD_SIZE, boardSize);
        editor.apply();
    }

    static public String getBoardSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("BoardPrefs", MODE_PRIVATE);

        return prefs.getString(BOARD_SIZE, context.getString(R.string.fourBySix));
    }

    private void createMineRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_number_of_mines);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);

        // Create the buttons
        for (final int numMine : numMines) {
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.mines, numMine));
            button.setTextColor(Color.parseColor("#FFFFFF"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumberOfMines(numMine);
                }
            });
            // Add to radio group
            group.addView(button);

            // Select default button
            if (numMine == getNumMines(this)) {
                button.setChecked(true);
            }
        }
    }

    private void saveNumberOfMines(int numMineSize) {
        SharedPreferences prefs = this.getSharedPreferences("MinePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_MINES, numMineSize);
        editor.apply();
    }

    static public int getNumMines(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MinePrefs", MODE_PRIVATE);
        int defaultNumMines = context.getResources().getInteger(R.integer.default_num_mines);

        return prefs.getInt(NUM_MINES, defaultNumMines);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Options.class);
    }
}
