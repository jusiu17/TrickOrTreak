/**
*        Activity for the menu that player can navigate to the game board, option and help screen
 */

package cmpt276.as3.trickortreat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.crypto.spec.OAEPParameterSpec;

import cmpt276.as3.trickortreat.model.GameBoard;
import cmpt276.as3.trickortreat.model.TrickOrTreat;


public class MainMenuActivity extends AppCompatActivity {

    private GameBoard game;
    Animation rotate;
    TextView textView;
    Animation slide;
    Button button;
    Animation move;
    Animation blink;
    ImageView imageView;

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //private GameBoard game;

        setContentView(R.layout.activity_main_menu);

        //get the game info
        game = GameBoard.getInstance();
        loadData();

        //setup the start button
        setupStartButton();

        //setup the Help button
        setupHelpButton();

        //setup the Option button
        setupOptionButton();

        // animations for the title and buttons
        slideTitleAnimation();
        rotateStartButton();
        rotateHelpButton();
        rotateOptionButton();
        blinkBigGhost();
        moveSmallGhost();
        rotateBats();

        //refresh the main menu screen
        refreshGameByOption();

    }


    private void slideTitleAnimation() {
        textView = (TextView) findViewById(R.id.game_title);
        slide = AnimationUtils.loadAnimation(this, R.anim.slide);
        textView.startAnimation(slide);
    }

    /*
            rotation on the button
     */
    private void rotateStartButton() {
        button = (Button) findViewById(R.id.menu_start_button);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        button.startAnimation(rotate);
    }
    private void rotateHelpButton() {
        button = (Button) findViewById(R.id.menu_help_button);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        button.startAnimation(rotate);
    }
    private void rotateOptionButton() {
        button = (Button) findViewById(R.id.menu_option_button);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        button.startAnimation(rotate);
    }

    private void blinkBigGhost() {
        imageView = (ImageView) findViewById(R.id.menu_black_ghost);
        blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        imageView.startAnimation(blink);
    }

    private void moveSmallGhost() {
        imageView = (ImageView) findViewById(R.id.menu_small_black_ghost);
        move = AnimationUtils.loadAnimation(this, R.anim.move);
        imageView.startAnimation(move);
    }

    private void rotateBats() {
        imageView = (ImageView) findViewById(R.id.menu_bats);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotate);
    }

    private void setupStartButton() {
        Button button = findViewById(R.id.menu_start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameGridActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupOptionButton() {
        Button button = findViewById(R.id.menu_option_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OptionActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupHelpButton() {
        Button button = findViewById(R.id.menu_help_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

    // getting the row size and candy size from option activity
    public void refreshGameByOption(){
        // get the selected value from option screen
        int numCandies = OptionActivity.getNumCandiesSelected(this);
        int rowSize = OptionActivity.getBoardSizeSelected(this);


        // change board and game setting if is not same as option screen
        TrickOrTreat tot = game.getTot();
        if (tot.getNumOfCandy() != numCandies || tot.getGridRow() != rowSize){
            int colSize = getColumnSize(rowSize);
            tot = new TrickOrTreat(numCandies,rowSize,colSize);
            System.out.println("\t\t\tre---start");
            game.setTot(tot);
            game.restart();
            saveData();
            loadData();
        }
    }

    // getting the column size by row size
    private int getColumnSize(int rowSize) {
        switch (rowSize) {
            case 4:
                return 6;
            case 5:
                return 10;
            case 6:
                return 15;
        }
        return 0;
    }


    /*
            refresh/show the updated number of candies in the main menu activity
            after the user selects the number of candies from the option screen
    */
    @Override
    protected void onResume() {
        super.onResume();
        refreshGameByOption();
    }

    //  saving the state to Json file
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(game);
        editor.putString("Game Board",json);
        editor.apply();
    }

    //  loading the state from Json file
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Game Board",null);
        Type type = new TypeToken<GameBoard>() {}.getType();
        GameBoard newGame = gson.fromJson(json,type);
        if (newGame == null)
            saveData();
        else{
            game.setInstance(newGame);
            OptionActivity.saveBoardSizeSelected(game.getTot().getGridRow(),this);
            OptionActivity.saveNumCandiesSelected(game.getTot().getNumOfCandy(),this);
        }


    }

}
