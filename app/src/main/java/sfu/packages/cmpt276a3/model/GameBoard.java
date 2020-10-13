package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import sfu.packages.cmpt276a3.R;

public class GameBoard extends AppCompatActivity {

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 15;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateBoard();
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

                button.setText("" + col + "," + row);

                // Make text not clip for small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boardButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void boardButtonClicked(int col, int row) {
        Toast.makeText(this, "Button Clicked: " + col + "," + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        // Locks button sizes
        lockButtonSizes();

        // Scale image to background
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fort);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        // Change text on button
        button.setText("" + col);

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