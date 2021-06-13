package edu.msu.cse476.cloudhatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    String user, pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onSigningUp(View view) {

        EditText mEdit;
        EditText mEdit1;
        EditText mEdit2;

        mEdit = (EditText)findViewById(R.id.PassFill1);
        mEdit1 = (EditText)findViewById(R.id.PassFill2);
        mEdit2 = (EditText)findViewById(R.id.SGusernameFill);

        String pass1 =  mEdit.getText().toString();
        String pass2 =  mEdit1.getText().toString();
        String username =  mEdit2.getText().toString();

        getLogInViewView().setPassword1(pass1);
        getLogInViewView().setPassword1(pass2);
        getLogInViewView().setUsername(username);

        if(pass1.equals(pass2)){
            SignUpDlg dlg3 = new SignUpDlg();

            dlg3.show(getSupportFragmentManager(), "save");
        }else{
            Toast.makeText(view.getContext(),
                    R.string.passwordmatch,
                    Toast.LENGTH_SHORT).show();
        }
//        Intent intent = new Intent(this, SignUp.class);
//        startActivity(intent);
    }

    public void OnLogin(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    /**
     * Get the puzzle view
     * @return PuzzleView reference
     */
    private LogInView getLogInViewView() {
        return (LogInView) this.findViewById(R.id.SignupView);
    }
}