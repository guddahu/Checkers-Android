package edu.msu.cse476.cloudhatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    public void setPassMatch(boolean passMatch) {
        this.passMatch = passMatch;
    }

    String pass;
    String username;
    boolean passMatch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            String u =  sharedPreferences.getString("user", null);
            String p =  sharedPreferences.getString("password", null);

            if (u != null && p != null) {
                EditText E = (EditText)findViewById(R.id.usernameFill);
                E.setText(u);
                EditText P = (EditText) findViewById(R.id.passwordFill);
                P.setText(p);
            }
        } catch (Exception ex) {
        }
        MediaPlayer player = MediaPlayer.create( this, R.raw.welcome);
        player.start();
    }

    public void onSignup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void onLogin(View view) {
        EditText mEdit;
        EditText mEdit1;
        EditText mEdit2;

        mEdit = (EditText)findViewById(R.id.passwordFill);
        mEdit1 = (EditText)findViewById(R.id.usernameFill);

        pass =  mEdit.getText().toString();
        username =  mEdit1.getText().toString();

        getLogInViewView().setPassword1(pass);
        getLogInViewView().setUsername(username);

        LoginDlg dlg3 = new LoginDlg();
        dlg3.show(getSupportFragmentManager(), "load");

        if(dlg3.isPass1()){

        }
    }

    public void onLoginMove() {
        Intent intent = new Intent(this, WaitingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("PlayerUsername1", username);
        intent.putExtra("PlayerUsername2", pass);

        CheckBox C = (CheckBox)findViewById(R.id.checkBox);

        if(C.isChecked()){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getLogInViewView().getContext());
            final SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", username);
            editor.putString("password", pass);
            editor.apply();
        }
        startActivity(intent);
    }
        /**
         * Get the puzzle view
         * @return PuzzleView reference
         */
    private LogInView getLogInViewView() {
        return (LogInView) this.findViewById(R.id.Loginview);
    }
}
