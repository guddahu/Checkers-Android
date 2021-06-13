package edu.msu.cse476.cloudhatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.msu.cse476.cloudhatter.Cloud.Cloud;

public class CheckerActivity extends AppCompatActivity {
    public final static String NAME1 =
            "edu.msu.rajadit1.project1.NAME1";

    public final static String NAME2 =
            "edu.msu.rajadit1.project1.NAME2";
    public final static String TURN =
            "edu.msu.rajadit1.project1.TURN";

    private static final String PARAMETERS = "parameters";

    private TextView getCurrentPlayer() {
        return (TextView)findViewById(R.id.textView3);
    }

    boolean alertBox = true;
    int i = 0;
    String SelfUser;
    String CurrentPlayer = " ";
    boolean colorSelect = true;
    String color = " ";
    String n1 = " ";
    String n2 = " ";
    int turn_curr = 1;
    int toggle_player = 1;

    String King;
    int piece;
    double x;
    double y;
    String user;
    int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);

        Intent i = getIntent();
        // Get the message from the intent
        n1 = i.getStringExtra("player1Name");
        n2 = i.getStringExtra("player2Name");
        getCheckerView().setN1(n1);
        getCheckerView().setN2(n2);
        SelfUser = n1;
        TextView mEdit = (TextView) findViewById(R.id.textView3);
        TextView mEdit2 = (TextView) findViewById(R.id.textView3);
        checkForTurn();
        if (savedInstanceState != null) {
            getCheckerView().getFromBundle(PARAMETERS, savedInstanceState);
            turn_curr = getCheckerView().getCurrent_turn();
            toggle_player = getCheckerView().getCurrent_turn();
            //  getSpinner().setSelection(getHatterView().getHat());
        }
        if (turn_curr == 1) {
            if (mEdit != null) {
                mEdit.setText("Current Turn: \n" + n1);

            }
            if (mEdit2 != null) {
                mEdit2.setText("Current Turn: " + n1);

            }


        } else {
            if (mEdit != null) {
                mEdit.setText("Current Turn: \n" + n2);

            }

            if (mEdit2 != null) {
                mEdit2.setText("Current Turn: " + n2);

            }


        }
        getCheckerView().getChecker().setTurnTaken(true);

        getCheckerView().invalidate();
    }

    public void onNextTurn(View view) {

        if(!CurrentPlayer.equals(n1)) {
            getCheckerView().getChecker().setTurnTaken(true);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Cloud cloud = new Cloud();
                    Checker.Turn T = getCheckerView().getChecker().T;
                    if(getCheckerView().getResign() == 1){
                        cloud.push(n1, T.piece, "resign", T.x, T.y);

                    }else{
                        cloud.push(n1, T.piece, "resign1", T.x, T.y);
                    }

                }

                ;
            });

            t1.start();
        }

//        }
        TextView mEdit = (TextView) findViewById(R.id.textView3);
        TextView mEdit2 = (TextView) findViewById(R.id.textView3);

        getCheckerView().invalidate();

        if (getCheckerView().getResign() == 1) {
            Resign = n1;
            this.onResignGame(view);

        }
        if (getCheckerView().getResign() == 2) {
            Resign = n2;
            this.onResignGame(view);

        }
        checkForTurn();
    }
    String Resign = " ";
    public void onResignGame(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        if (getCheckerView().getResign() != 2 || getCheckerView().getResign() != 1) {


            Thread thread = new Thread() {
                public void run(){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Cloud cloud = new Cloud();
                            Checker.Turn T = getCheckerView().getChecker().T;
                            if(getCheckerView().getResign() == 1){
                                cloud.push(n1, T.piece, "resign", T.x, T.y);

                            }else{

                                cloud.push(n1, T.piece, "resign1", T.x, T.y);
                            }

                        }
                    });
                }
            };
            thread.start();
            if(getCheckerView().getResign() == 1){
                intent.putExtra(NAME1, n1);

            }else if(getCheckerView().getResign() == 2){
                intent.putExtra(NAME1, n2);
            }else{
                intent.putExtra(NAME1, n2);

            }

            startActivity(intent);
        }else{
            intent.putExtra(NAME1, Resign);

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getCheckerView().putToBundle(PARAMETERS, outState);

    }

    private CheckerView getCheckerView() {
        return (CheckerView) findViewById(R.id.CheckerView);
    }


    private Button getColorButton() {
        return (Button) findViewById(R.id.NextTurn);
    }
    public void checkForTurn() {

        final Timer timer = new Timer();
        final TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                final Cloud cloud = new Cloud();
                boolean notFail = cloud.pull();
                //  String name = getCheckerView().getPlayer1Name();
                if (notFail) {
                        try{
                        piece = Integer.parseInt(cloud.getPiece());
                        x = Double.parseDouble(cloud.getX());
                        y = Double.parseDouble(cloud.getY());
                        King = cloud.getKing();
                        user = cloud.getUser();
                        } catch (Exception e){

                        }

                    Thread thread = new Thread() {
                        public void run(){
                            runOnUiThread(new Runnable() {
                                public void run() {



                                        TextView mEdit = (TextView)findViewById(R.id.textView3);
                                        if(user.equals(SelfUser)){
                                            getCheckerView().getChecker().setTurnTaken(true);

                                            CurrentPlayer = user;
                                            if(colorSelect){
                                                colorSelect = false;
                                                color = "green";
                                                getCheckerView().getChecker().setColor("green");
                                            }
                                            mEdit.setText("Current Turn: " + n2);
                                            i++;
                                            j = 0;

                                            getCheckerView().getChecker().setCurrentPLayer(n2);
                                            getCheckerView().setTurntaken(true);

                                        }else{
                                            CurrentPlayer = user;
                                            if(colorSelect){
                                                colorSelect = false;
                                                color = "white";

                                                getCheckerView().getChecker().setColor("white");
                                            }
                                            i = 0;
                                            j++;


                                            mEdit.setText("Current Turn: " + n1 +"("+ color+")");

                                            getCheckerView().getChecker().setCurrentPLayer(n1);
                                            getCheckerView().getChecker().setTurnTaken(false);
                                            getCheckerView().setTurntaken(false);
                                        }

                                        getCheckerView().getChecker().SetGameState(King, x, y, piece);
                                        getCheckerView().getChecker().onReleased(getCheckerView(),(float) x,(float) y,1);

                                    getCheckerView().invalidate();

                                }
                            });
                        }
                    };
                    thread.start();

//                    }
                }
                else{


                }
            }

        };

        timer.schedule (timeTask, 01, 100*60);

    }


    public void updateTurn(){
        getCurrentPlayer().setText("Current Turn: \n" + CurrentPlayer);

    }
}