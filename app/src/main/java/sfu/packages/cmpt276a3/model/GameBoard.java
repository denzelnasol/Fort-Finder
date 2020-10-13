package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;

import sfu.packages.cmpt276a3.R;

public class GameBoard extends AppCompatActivity {

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 7;
    private static final int NUM_MINES = 8;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    // Array for location of mines, if spot contains hidden mine, int is 1,
    // if contains revealed mine, int is 2, if spot already clicked once, int is 3, else int is 0
    int mines[][] = new int[NUM_ROWS][NUM_COLS];
    int nearbyHiddenMines[][] = new int[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        populateBoard();
        setMines();
    }

    private void populateBoard() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForGameBoard);
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
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Make text not clip for small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boardButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

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

    private void boardButtonClicked(int row, int col) {
        Button button = buttons[row][col];

        // Locks button sizes
        lockButtonSizes();

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
        }
        else if (mines[row][col] == 4) {
            mines[row][col] = 3;
        }
        else {
            mines[row][col] = 3;
            scanBoard(row, col);
        }
        updateNearbyHiddenMines();
    }

    private void scanBoard(int mineRow, int mineCol) {
        Button button = buttons[mineRow][mineCol];
        int mineCount = 0;
        //boolean mineFound = false;

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
        nearbyHiddenMines[mineRow][mineCol] = mineCount;
        button.setText("" + nearbyHiddenMines[mineRow][mineCol]);
        mineCount = 0;
    }

    private void updateNearbyHiddenMines() {
        Button button;
        int mineCount = 0;

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (mines[row][col] == 3) {
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
                    button.setText("" + nearbyHiddenMines[row][col]);
                    mineCount = 0;
                }
                if (mines[row][col] == 2) {
                    mines[row][col] = 4;
                }
            }
        }
    }

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
}