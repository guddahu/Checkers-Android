package edu.msu.cse476.cloudhatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        String n1 = intent.getStringExtra(CheckerActivity.NAME1);
        TextView mEdit = (TextView) findViewById(R.id.PlayerWinsView);
        mEdit.setText(n1 + " Wins !!!!!!");
    }

    public void onResignGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}