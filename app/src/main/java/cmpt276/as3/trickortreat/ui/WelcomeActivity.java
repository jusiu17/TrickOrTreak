/**
*        Activity that show up at the start of the application with animation
 */

package cmpt276.as3.trickortreat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Animation rotate;
    TextView textView;
    Animation move;
    Button button;
    Animation blink;
    Animation slide;
    Animation fade;
    ImageView imageView;

    Handler startPage;
    Runnable skipToMenu = () -> {
        Intent intent = MainMenuActivity.makeIntent(WelcomeActivity.this);
        startActivity(intent);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        startPage = new Handler();
        startPage.postDelayed(() -> {
            findViewById(R.id.welcome_constraint_layout).setForeground(null);
            //---------------------------------------------------------------------------------
            //function that allows the title of the game to rotate
            rotateAnimation();
            //---------------------------------------------------------------------------------
            //fadeAnimationDisplayAuthor();
            slideAnimation();
            fadeAnimationDisplayAuthorSiu();
            fadeAnimationDisplayAuthorSu();
            rotateCandyImageAnimation();
            blinkAnimationSkipButton();
        },2500);


        // function for the skip button
        setupSkipButton();
        startPage.postDelayed(skipToMenu,10000);

    }



    //--------------------------------------------------------------------------------------
    // source: https://www.youtube.com/watch?v=goVoYf2qie0
    private void rotateAnimation() {
        textView = (TextView) findViewById(R.id.game_title);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        textView.startAnimation(rotate);
    }

    private void slideAnimation() {
        textView = (TextView) findViewById(R.id.welcome_author_title);
        slide = AnimationUtils.loadAnimation(this, R.anim.slide);
        textView.startAnimation(slide);
    }

    private void fadeAnimationDisplayAuthorSiu() {
        textView = (TextView) findViewById(R.id.welcome_author_siu);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        textView.startAnimation(fade);
    }

    private void fadeAnimationDisplayAuthorSu() {
        textView = (TextView) findViewById(R.id.welcome_author_su);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade_later);
        textView.startAnimation(fade);
    }

    private void rotateCandyImageAnimation() {
        imageView = (ImageView) findViewById(R.id.welcome_candy);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotate);
    }

    private void blinkAnimationSkipButton() {
        button = (Button) findViewById(R.id.welcome_skip_button);
        blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        button.startAnimation(blink);
    }

    private void moveAnimation() {
        button = (Button) findViewById(R.id.welcome_skip_button);
        move = AnimationUtils.loadAnimation(this, R.anim.move);
        button.startAnimation(move);
    }
    //--------------------------------------------------------------------------------------

    private void setupSkipButton() {
        Button button = findViewById(R.id.welcome_skip_button);
        button.setOnClickListener(v -> {
            //function that allows the button to move right when clicked
            startPage.removeCallbacks(skipToMenu);
            moveAnimation();
            Intent intent = MainMenuActivity.makeIntent(WelcomeActivity.this);
            startActivity(intent);
            finish();
        });

    }

}