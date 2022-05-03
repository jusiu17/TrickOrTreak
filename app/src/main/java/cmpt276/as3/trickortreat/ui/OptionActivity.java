/**
*        Activity for player to change the game board size and the number of candy
 */

package cmpt276.as3.trickortreat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cmpt276.as3.trickortreat.model.GameBoard;

public class OptionActivity extends AppCompatActivity {

    private GameBoard game;

    // declared variables
    private static final String selected = "Num candies/board size selected";
    private static final String appPrefs = "AppPrefs";

    private static final String selectedBoard = "board size selected";
    private static final String appBoardPrefs = "AppPrefs Board";

    RadioGroup numCandyGroup;
    RadioGroup boardSizeGroup;
    RadioButton candyButton[];
    RadioButton boardButton[];

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_screen);

        // Get that one instance from Model GameBoard class
        game = GameBoard.getInstance();
        candyButton = new RadioButton[getResources().getIntArray(R.array.num_candies).length];
        boardButton = new RadioButton[getResources().getIntArray(R.array.boards_array).length];

        /* create radio buttons for display the
        number of candies to choose from */
        selectNumCandyRadioButtons();

        //function to display the board size options
        selectBoardSize();

        checkGameOption();
    }

    private void checkGameOption() {
        switch (game.getTot().getNumOfCandy()){
            case 6:
                candyButton[0].setChecked(true);
                break;
            case 10:
                candyButton[1].setChecked(true);
                break;
            case 15:
                candyButton[2].setChecked(true);
                break;
            case 20:
                candyButton[3].setChecked(true);
                break;
        }
        switch (game.getTot().getGridRow()){
            case 4:
                boardButton[0].setChecked(true);
                break;
            case 5:
                boardButton[1].setChecked(true);
                break;
            case 6:
                boardButton[2].setChecked(true);
                break;
        }
    }


    private void selectNumCandyRadioButtons() {
        numCandyGroup = (RadioGroup) findViewById(R.id.option_candy_radio_group);
        int[] numCandies = getResources().getIntArray(R.array.num_candies);

        //Create buttons for number of candies to choose from
        for(int i = 0; i < numCandies.length; i++) {
            final int numCandy = numCandies[i];

            RadioButton button = new RadioButton(this);
            button.setText(numCandy + " Candies");
            button.setTextColor(getResources().getColor(R.color.colorWhite));


            button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //function to store the selected number of candies
                        saveNumCandiesSelected(numCandy, OptionActivity.this);

                    }
            });

            //add radio buttons to radio group
            numCandyGroup.addView(button);
            candyButton[i] = button;

            /* shows selected/default number of candies radio button by
             making that radio button red, indicating that it's the chosen
             number candies */
            if (numCandy == getNumCandiesSelected(this)) {
                button.setChecked(true);
            }

        }
    }

    public static void saveNumCandiesSelected(int numCandy, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(appPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(selected, numCandy);
        editor.apply();
    }

    public static int getNumCandiesSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(appPrefs, MODE_PRIVATE);

        int defaultNumCandies = context.getResources().getInteger(R.integer.default_num_candies);

        return prefs.getInt(selected, defaultNumCandies);
    }


    private void selectBoardSize() {
        boardSizeGroup = (RadioGroup) findViewById(R.id.option_board_radio_group);
        int[] boards = getResources().getIntArray(R.array.boards_array);

        //Create buttons for number of candies to choose from
        for(int i = 0; i < boards.length; i++) {
            final int board = boards[i];

            RadioButton button = new RadioButton(this);
            if(board == 4) {
                button.setText(board + " Rows by 6 Columns");
            }
            if(board == 5) {
                button.setText(board + " Rows by 10 Columns");
            }
            if(board == 6) {
                button.setText(board + " Rows by 15 Columns");
            }
            button.setTextColor(getResources().getColor(R.color.colorWhite));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(OptionScreen.this, "You chose " + numCandy + " candies", Toast.LENGTH_SHORT)
                    //        .show();

                    //function to store the selected number of candies
                    saveBoardSizeSelected(board, OptionActivity.this);

                }
            });
            //add radio buttons to radio group
            boardSizeGroup.addView(button);
            boardButton[i] = button;
            /* shows selected/default number of candies radio button by
             making that radio button red, indicating that it's the chosen
             board size */
            if (board == getBoardSizeSelected(this)  ) {
                button.setChecked(true);
            }

        }
    }

    public static void saveBoardSizeSelected(int board, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(appBoardPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(selectedBoard, board);

        editor.apply();
    }

    public static int getBoardSizeSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(appBoardPrefs, MODE_PRIVATE);

        int defaultBoardSize = context.getResources().getInteger(R.integer.default_board_size);

        return prefs.getInt(selectedBoard, defaultBoardSize);
    }

}
