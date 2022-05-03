/**
*       Activity for the game display with a game board.
*       Player play the game in this activity by click the button in the table layout.
 */

package cmpt276.as3.trickortreat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import cmpt276.as3.trickortreat.model.GameBoard;

public class GameGridActivity extends AppCompatActivity {

    private int numRow;
    private int numCol;
    private GameBoard game;
    private int vibrateIndex;
    private Vibrator vibrator;
    private int vibrateIncrement;
    private boolean loadDialog;


    Button buttonGrid[][];

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameGridActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid);

        //get the game info
        game = game.getInstance();
        loadDialog = false;

        numRow = game.getTot().getGridRow();
        numCol = game.getTot().getGridColumn();
        buttonGrid = new Button[numRow][numCol];
        vibrateIndex = 50;
        vibrateIncrement = 2000/game.getTot().getNumOfCandy();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        generateButtons();
    }

    //  make change after the view is fit in the screen
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fixIcon();
        checkClickedButton();
        findViewById(R.id.grid_constraint_layout).setForeground(null);
        updateUI();
        checkGoal();
    }

    //  change button to door icon and fix the size of it after the activity show
    private void fixIcon() {
        for (int row = 0; row != numRow; row++)
            for (int col = 0; col!= numCol; col++) {
                Button button = buttonGrid[row][col];
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.door);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            }
    }

    //  learn from youtube video lecture about dynamic button for generate the button grid
    private void generateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.buttonTable);

        for (int row = 0; row != numRow; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    0.5f));

            table.addView(tableRow);

            for (int col = 0; col != numCol; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);

                button.setTextColor(Color.WHITE);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        0.5f));

                button.setOnClickListener(v -> {
                    if(!game.isGridScanned(FINAL_ROW,FINAL_COL))
                        gridButtonClicked(FINAL_ROW, FINAL_COL,false);
                });

                tableRow.addView(button);
                buttonGrid[row][col] = button;
            }

        }
    }

    //  check which button had been clicked before while generate the button
    private void checkClickedButton() {
        for (int row = 0; row != numRow; row++)
            for (int col = 0; col!= numCol; col++) {
                Button button = buttonGrid[row][col];
                if(game.isGridScanned(row,col))
                    gridButtonClicked(row,col,true);
            }

    }

    //  method for button click in the button grid
    protected void gridButtonClicked(int row, int col, Boolean generate) {
        Button button = buttonGrid[row][col];

        // Lock Button Sizes:
        lockButtonSizes();

        // set scanned in board detail
        button.setEnabled(false);
        if(game.isCandy(row,col)) {
            // Scale image to button with fixed size
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.candy);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            if(!generate) {
                vibrator.vibrate(vibrateIndex);
                vibrateIndex += vibrateIncrement;
                final MediaPlayer found = MediaPlayer.create(this, R.raw.found_candy);
                found.start();
            }
        }else{
            button.setBackgroundColor(Color.TRANSPARENT);
            if(!generate) {
                final MediaPlayer fail = MediaPlayer.create(this, R.raw.fail);
                fail.start();
            }
        }
        if(!generate) {
            game.scanCell(row, col);
            checkGoal();
        }
        updateUI();
    }

    // update the screen UI while a change made
    private void updateUI() {
        TextView showNumofCandy = (TextView) findViewById(R.id.show_numOfCandy);
        TextView showNumofFound = (TextView) findViewById(R.id.show_numOfFound);
        TextView showNumofScan = (TextView) findViewById(R.id.show_numOfScan);

        showNumofCandy.setText("" +game.getTot().getNumOfCandy());
        showNumofFound.setText("" + game.getTot().getNumOfFound());
        showNumofScan.setText("" + game.getTot().getNumOfScan());
        refreshButtonText();
        saveData();
    }

    // refresh the text in screen while updating the UI
    private void refreshButtonText() {
        for(int row=0; row<numRow; row++){
            for(int col=0; col<numCol; col++){
                if(game.isGridScanned(row,col)) {
                    Button button = buttonGrid[row][col];
                    button.setText("" + game.getGridCount(row,col));
                }
            }
        }
    }

    //  lock the button size while a button clicked
    private void lockButtonSizes() {
        for (int row = 0; row != numRow; row++) {
            for (int col = 0; col != numCol; col++) {
                Button button = buttonGrid[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    //  disable all button while game reach the goal
    private void disableALLButton(){
        for (int row = 0; row != numRow; row++) {
            for (int col = 0; col != numCol; col++) {
                Button button = buttonGrid[row][col];
                button.setEnabled(false);
            }
        }
    }

    //  method for checking is the game reach the goal
    private void checkGoal() {
        if (!loadDialog && game.getTot().getNumOfCandy() == game.getTot().getNumOfFound()){
            loadDialog = true;
            disableALLButton();
            final MediaPlayer complete = MediaPlayer.create(this, R.raw.complete);
            complete.start();
            WinningMessage dialog = new WinningMessage();
            dialog.show(getSupportFragmentManager(), "winning msg");
        }
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


}
