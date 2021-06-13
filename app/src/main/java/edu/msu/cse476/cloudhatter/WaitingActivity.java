package edu.msu.cse476.cloudhatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import edu.msu.cse476.cloudhatter.Cloud.Cloud;

import static java.lang.Thread.sleep;

public class WaitingActivity extends AppCompatActivity {
    private String Username1;
    private String op;
    boolean play = true;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        final Intent intent = getIntent();
        Username1 = intent.getExtras().getString("PlayerUsername1");
        MediaPlayer player = MediaPlayer.create( this, R.raw.finding);
        player.start();
        new Thread(new Runnable() {

            @Override
            public void run() {

                Cloud cloud = new Cloud();
                final boolean ok = cloud.join(Username1);
            }
        }).start();

        poll_again();
    }

    public void poll_again(){
        final Timer timer = new Timer();
        final TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                final Cloud cloud = new Cloud();
                boolean notFail = cloud.check_start(Username1);
                if (notFail)
                {
                    if (i == 1){
                        i = 2;
                    }

                    op = cloud.getOpponent();
                    checkOp(timer);
//
//                    try {
//                        sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        };
        timer.schedule (timeTask, 01, 100*60);
    }
    public void checkOp(Timer timer)
    {
        if(i == 2){
            i =2;
        }
        i++;
        if (!op.equals(""))
        {
            //Button button = (Button)findViewById((R.id.button));
            //button.setVisibility(View.VISIBLE);
            play = true;
            timer.cancel();
            timer.purge();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView player2 = (TextView) findViewById(R.id.player_found);
                    player2.setText(op);
                    Button button = (Button) findViewById(R.id.playButton);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            onStart(v);
                        }
                    });
                }
            });


        }
        else{
        }
    }
    public void onStart(View view)
    {
        if (play) {
            Intent intent = new Intent(this, CheckerActivity.class);
            intent.putExtra("player1Name", Username1);
            intent.putExtra("player2Name", op);
            startActivity(intent);
        }
    }

}