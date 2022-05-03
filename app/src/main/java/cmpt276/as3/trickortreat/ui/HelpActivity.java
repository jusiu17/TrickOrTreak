/**
*        Activity for the game information, game rule and about the author
 */

package cmpt276.as3.trickortreat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class HelpActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        TextView tvCourseLink = findViewById(R.id.link_textview);
        tvCourseLink.setMovementMethod(LinkMovementMethod.getInstance());


    }

}
