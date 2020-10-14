package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sfu.packages.cmpt276a3.R;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Options.class);
    }
}