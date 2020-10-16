package sfu.packages.cmpt276a3.model.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Random;

import sfu.packages.cmpt276a3.R;
import sfu.packages.cmpt276a3.model.logic.GameLogic;

/**
 * Displays the game board and uod
 */
public class GameUI extends AppCompatActivity {

    // NOTE: The term 'Mines' refers to 'Forts'

    private static int NUM_ROWS = 4;
    private static int NUM_COLS = 6;
    private static int NUM_MINES = 6;
    private static int NUM_GAMES_PLAYED = 0;

    private static int minesFound = 0;
    private static int scansUsed = 0;

    // Array of buttons on board
    Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];

    // Array for location of mines,
    // if spot contains hidden mine, value is 1,
    // if contains revealed mine, value is 2,
    // if spot already scanned at least once, value is 3,
    // if revealed mine is pressed again, value is 4
    // if spot already clicked twice, value is 5
    // else value is 0
    int[][] mines = new int[NUM_ROWS][NUM_COLS];

    // Array containing number of surround mines for each button
    int[][] nearbyHiddenMines = new int[NUM_ROWS][NUM_COLS];

    GameLogic logic = new GameLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        scansUsed = 0;
        minesFound = 0;

        updateGamesPlayed();
        updateNumMines();
        updateBoardSize();

        populateBoard();
        setMines();
    }

    void updateGamesPlayed() {
        SharedPreferences sharedPreferences = getSharedPreferences("ErasePrefs", MODE_PRIVATE);
        boolean eraseCheck = sharedPreferences.getBoolean("Erase Check", false);

        if (eraseCheck) {
            NUM_GAMES_PLAYED = 0;
            sharedPreferences.edit().clear().apply();
            setGamesPlayed();
        }
        else {
            NUM_GAMES_PLAYED = getGamesPlayed(this);
        }
        TextView text = findViewById(R.id.gamesPlayedView);
        text.setText(getString(R.string.games_played, NUM_GAMES_PLAYED));
    }

    static public int getGamesPlayed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("PreferencesName", MODE_PRIVATE);
        return prefs.getInt("Games Played", 0); // 0 is default;

    }

    void setGamesPlayed() {
        SharedPreferences.Editor editor = getSharedPreferences("PreferencesName", MODE_PRIVATE).edit();
        editor.putInt("Games Played", NUM_GAMES_PLAYED);
        editor.apply();
    }

    // TODO: THIS IS GAME LOGIC
    private void updateBoardSize() {
        String boardSize = Options.getBoardSize(this);
        switch (boardSize) {
            case "5 x 10":
                NUM_ROWS = 5;
                NUM_COLS = 10;
                break;
            case "6 x 15":
                NUM_ROWS = 6;
                NUM_COLS = 15;
                break;
            default:
                NUM_ROWS = 4;
                NUM_COLS = 6;
                break;
        }
        buttons = new Button[NUM_ROWS][NUM_COLS];
        mines = new int[NUM_ROWS][NUM_COLS];
        nearbyHiddenMines = new int[NUM_ROWS][NUM_COLS];
    }

    // TODO: THIS IS GAME LOGIC
    private void setMines() {
        int timesRun = NUM_MINES;
        for (int i = 0; i < timesRun; i++) {
            Random rand = new Random();
            int resultRow = rand.nextInt(NUM_ROWS);
            int resultCol = rand.nextInt(NUM_COLS);

            if (mines[resultRow][resultCol] == 1) {
                timesRun++;
            }
            else {
                mines[resultRow][resultCol] = 1;
            }
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (mines[row][col] != 1) {
                    mines[row][col] = 0;
                }
            }
        }
    }

    // TODO: THIS IS GAME LOGIC
    private void boardButtonClicked(int row, int col) {
        Button button = buttons[row][col];

        // Locks button sizes
        lockButtonSizes();

        // Check if mine found
        if (mines[row][col] == 1) {
            // Set revealed value to mine
            mines[row][col] = 2;

            // Scale image to background
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fort);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            minesFound++;
            TextView text = findViewById(R.id.numberOfMinesFound);
            text.setText(getString(R.string.number_mines_found, minesFound, NUM_MINES));

            checkPlayerWon();

        }
        // Check if revealed mine clicked again, then set to value of 3 if so
        else if (mines[row][col] == 4) {
            mines[row][col] = 3;
            scansUsed++;
            TextView text = findViewById(R.id.scansUsed);
            text.setText(getString(R.string.number_scans_used, scansUsed));
        }
        //// Check if button has been scanned already, if so, only scan
        else if (mines[row][col] == 5) {
            scanBoard(row, col);
        }
        //Scan board and set value to 5 to prevent redundant scanning
        else {
            mines[row][col] = 3;
            scanBoard(row, col);
            mines[row][col] = 5;
        }
        // Update values on buttons for number of hidden mines
        updateNearbyHiddenMines();
    }

    // TODO: THIS IS GAME LOGIC
    private void checkPlayerWon() {
        if (minesFound == NUM_MINES) {
            FragmentManager manager = getSupportFragmentManager();
            WinFragment dialog = new WinFragment();
            dialog.show(manager, "WinMessageDialog");

            NUM_GAMES_PLAYED++;
            setGamesPlayed();

            // Reset values
            NUM_MINES = 0;
            scansUsed = 0;
            minesFound = 0;
        }
    }

    // TODO: THIS IS GAME LOGIC
    private void scanBoard(int mineRow, int mineCol) {
        Button button = buttons[mineRow][mineCol];
        int mineCount = 0;

        for (int boardRow = 0; boardRow < NUM_ROWS; boardRow++) {
            for (int boardCol = 0; boardCol < NUM_COLS; boardCol++) {

                if (mineRow == boardRow) {
                    if (mines[boardRow][boardCol] == 1) {
                        mineCount++;
                    }
                }

                if (mineCol == boardCol) {
                    if (mines[boardRow][boardCol] == 1) {
                        mineCount++;
                    }
                }
            }
        }
        if (mines[mineRow][mineCol] != 5) {
            scansUsed++;
        }


        TextView text = findViewById(R.id.scansUsed);
        text.setText(getString(R.string.number_scans_used, scansUsed));
        nearbyHiddenMines[mineRow][mineCol] = mineCount;
        button.setText(getString(R.string.empty, nearbyHiddenMines[mineRow][mineCol]));
    }

    // TODO: THIS IS GAME LOGIC
    private void updateNearbyHiddenMines() {
        Button button;
        int mineCount = 0;

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (mines[row][col] == 3 || mines[row][col] == 5) {
                    for (int boardRow = 0; boardRow < NUM_ROWS; boardRow++) {
                        for (int boardCol = 0; boardCol < NUM_COLS; boardCol++) {

                            if (row == boardRow) {
                                if (mines[boardRow][boardCol] == 1) {
                                    mineCount++;
                                }
                            }

                            if (col == boardCol) {
                                if (mines[boardRow][boardCol] == 1) {
                                    mineCount++;
                                }
                            }
                        }
                    }
                    nearbyHiddenMines[row][col] = mineCount;
                    button = buttons[row][col];
                    button.setText(getString(R.string.empty, nearbyHiddenMines[row][col]));
                    mineCount = 0;
                }
                if (mines[row][col] == 2) {
                    mines[row][col] = 4;
                }
            }
        }
    }

    // TODO: THIS IS UI
    private void lockButtonSizes() {
        for  (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth((width));
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight((height));
                button.setMaxHeight(height);
            }
        }
    }

    // TODO: THIS IS UI
    private void updateNumMines() {
        // Refresh NUM_MINES
        NUM_MINES = Options.getNumMines(this);
        TextView text = findViewById(R.id.numberOfMinesFound);
        text.setText(getString(R.string.found_zero_of_mine_nums, NUM_MINES));
    }

    // TODO: THIS IS UI
    private void populateBoard() {
        TableLayout table = findViewById(R.id.tableForGameBoard);
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int col = 0; col < NUM_COLS; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setTextSize(25);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Make text not clip for small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slash.start();
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        if (mines[FINAL_ROW][FINAL_COL] == 1) {
                            vibrator.vibrate(250);
                        }
                        else {
                            vibrator.vibrate(100);
                        }
                        boardButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    // TODO: EXTRA FUNCTIONS
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameUI.class);
    }
}
