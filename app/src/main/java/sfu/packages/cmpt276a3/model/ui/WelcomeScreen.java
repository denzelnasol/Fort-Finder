package sfu.packages.cmpt276a3.model.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sfu.packages.cmpt276a3.R;

/**
 *Welcome screen shows on app startup
 */
public class WelcomeScreen extends AppCompatActivity {

    // Animation code found at: https://www.youtube.com/watch?v=QD9nhyWX-gs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        titleAnimation();
        imageAnimation();
        skipAnimations();
    }

    private void titleAnimation() {
        TextView titleText = (TextView) findViewById(R.id.titleWelcomeView);
        TextView byText = (TextView) findViewById(R.id.byNameView);

        Animation fadeIn = new AlphaAnimation(0.1f, 1.0f);
        fadeIn.setDuration(3000);

        byText.setAnimation(fadeIn);
        titleText.setAnimation(fadeIn);
    }

    private void imageAnimation() {
        ImageView fortImage = (ImageView) findViewById(R.id.fortImage);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        fortImage.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent intent = Menu.makeIntent(WelcomeScreen.this);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void skipAnimations() {
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        Button button = (Button) findViewById(R.id.skipAnimButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slash.start();
                Intent intent = Menu.makeIntent(WelcomeScreen.this);
                startActivity(intent);
            }
        });
    }
}