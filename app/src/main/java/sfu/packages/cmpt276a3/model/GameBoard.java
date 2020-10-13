package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import sfu.packages.cmpt276a3.R;

public class GameBoard extends AppCompatActivity {

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 7;
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
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setText("" + row + "," + col);

                // Make text not clip for small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boardButtonClicked();
                    }
                });

                tableRow.addView(button);
            }
        }
    }

    private void boardButtonClicked() {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
    }
}