package edu.msu.cse476.cloudhatter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


/**
 * Custom view class for our Checker.
 */
public class CheckerView extends View {
    public void setCurrentTurn(String currentTurn) {
        CurrentTurn = currentTurn;
    }

    /**
     * Paint object we will use to draw a line
     */

    String CurrentTurn = "Default";
    String Opponent;
    private Paint linePaint;

    public Checker getChecker() {
        return checker;
    }

    /**
     * The actual Checker
     */
    private Checker checker;

    public int getResign() {
        return Resign;
    }

    private int Resign = 0;
    String n1 = " ";
    String n2 = " ";
    /**
     * Get the installed image path
     * @return path or null if none
     */
    public String getname1() {
        return n1;
    }
    public String getname2() {
        return n2;
    }

    public void setN1(String name) {
        n1 = name;

    }
    public void setN2(String name) {
        n2 = name;

    }

    public void setCurrent_turn(int current_turn) {
        this.current_turn = current_turn;
    }

    public int getCurrent_turn() {
        return current_turn;
    }

    private int current_turn = 1;

    public void setTurntaken(boolean turntaken) {
        this.turntaken = turntaken;
    }

    boolean turntaken = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!turntaken){
            return checker.onTouchEvent(this, event);
        }
        return false;
    }



    public CheckerView(Context context) {
        super(context);
        init(null, 0);
    }

    public CheckerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CheckerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        checker = new Checker(getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        TextView mEdit = (TextView) findViewById(R.id.textView3);
//
//        if(!CurrentTurn.equals("Default")) {
//
//            if(n1.equals(" ")){
//                mEdit.setText("Current Turn: Connecting....\n" );
//            }
//            else if (CurrentTurn.equals(n1)) {
//                mEdit.setText("Current Turn: \n" + n2);
//            } else {
//                checker.setTurnTaken(false);
//                mEdit.setText("Current Turn: \n" + n1);
//            }
//        }


        checker.setName1(n1);
        checker.setName2(n2);
        checker.setCurrent_Turn(current_turn);
        Resign = checker.getResign();
        super.onDraw(canvas);
        checker.draw(canvas);
    }


    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
    public void putToBundle(String key, Bundle bundle) {
        edu.msu.cse476.cloudhatter.Checker.Parameters params = checker.getParams();
        bundle.putSerializable(key, params);

    }

    /**
     * Get the view state from a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle) {
        edu.msu.cse476.cloudhatter.Checker.Parameters params = (edu.msu.cse476.cloudhatter.Checker.Parameters)bundle.getSerializable(key);
        checker.setParams(params);
        current_turn =  params.current_Turn;
    }
}