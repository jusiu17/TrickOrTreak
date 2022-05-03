/**
 *      A dialog that pop up when i player win the game.
 *      Player can select "back" button to check the game board or "new game" to create new game.
 */

package cmpt276.as3.trickortreat.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import cmpt276.as3.trickortreat.model.GameBoard;

public class WinningMessage extends AppCompatDialogFragment {

    private GameBoard game;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create the dialog layout
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.winning_message_popup, null);
        game = GameBoard.getInstance();
        // create button to back to main menu and create a new game
        DialogInterface.OnClickListener listener = (dialog, which) -> {

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    game.restart();
                    getActivity().finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        AlertDialog winningMsg = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.dialog_newGame_button,listener)
                .setNegativeButton(R.string.dialog_back_button, listener)
                .create();

        // Build the dialog message
        return winningMsg;
    }

}
