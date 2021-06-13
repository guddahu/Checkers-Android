package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class Checker {


    public void setName1(String name1) {
        this.params.name1 = name1;
    }

    public void setName2(String name2) {
        this.params.name2 = name2;
    }

    public void setColor(String color) {
        this.color = color;
    }

    String color = " ";

    public int getResign() {
        return params.resign;
    }

    public void setCurrent_Turn(int current_Turn) {
        this.params.current_Turn = current_Turn;

    }

    public int getCurrent_Turn() {
        return params.current_Turn;
    }

    public void setTurnTaken(boolean turnTaken) {
        this.turnTaken = turnTaken;
    }

    boolean turnTaken = true;

    public String getCurrentPLayer() {
        return CurrentPLayer;
    }

    public void setCurrentPLayer(String currentPLayer) {
        CurrentPLayer = currentPLayer;
    }

    String CurrentPLayer = " ";

    private int top = 0;
    private int right = 0;
    private int left = 0;
    private int bottom = 0;
    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

    private CheckerPiece piece_released1 = null;

    public void setSelected_piece_red(CheckerPiece selected_piece_red) {
        this.selected_piece_red = selected_piece_red;
    }

    private CheckerPiece selected_piece_red = null;

    private CheckerPiece piece_del = null;
    /**
     * The size of the puzzle in pixels
     */
    private int checkerSize;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;


    /**
     * Percentage of the display width or height that
     * is occupied by the checker.
     */
    final static float SCALE_IN_VIEW = 0.9f;
    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;
    private Paint fillPaint_grid;
    private Paint PaintText;

    /**
     * Paint for outlining the area the puzzle is in
     */
    private Paint outlinePaint;

    /**
     * Completed puzzle bitmap
     */
    private Bitmap puzzleComplete;
    /**
     * Collection of puzzle pieces
     */

    public ArrayList<Integer>[] al = new ArrayList[65];

    public Parameters getParams() {
        return params;
    }

    public void setParams(Parameters params) {
        this.params = params;
    }

    /**
     * The current parameters
     */
    private Parameters params = new Parameters();
    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */

    private int store_coor = 0;

    public float getLastRow() {
        return lastRow;
    }

    private float lastRow;

    public float getFirstRow() {
        return firstRow;
    }

    private float firstRow;
    public Checker(Context context) {
        // Create paint for filling the area the puzzle will
        // be solved in.
        PaintText = new Paint();
        PaintText.setColor(Color.RED);
        PaintText.setStyle(Paint.Style.FILL);
        PaintText.setTextSize(50);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        fillPaint_grid = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint_grid.setColor(Color.GRAY);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(Color.RED);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(10);
        puzzleComplete =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.board);

        // Load the solved puzzle image
        // Load the puzzle pieces

        for (int i = 0; i < 12; i++) {
            params.pieces.add(new CheckerPiece(context,
                    R.drawable.spartan_white,
                    0.259f,
                    0.238f, 1,R.drawable.king_white));
            params.pieces.get(i).setColor("white");

        }



        for (int i = 0; i < 12; i++) {
            params.pieces.add(new CheckerPiece(context,
                    R.drawable.spartan_green,
                    0.259f,
                    0.238f, 0,R.drawable.king_green));
            //params.pieces.get(i).setColor("green");

        }

        for (int i = 12; i < 24; i++) {
            params.pieces.get(i).setColor("green");

        }

            for (int i = 0; i < 24; i++) {
            params.pieces.get(i).setChecker(this);
        }

            // initializing
        for (int i = 0; i < 65; i++) {
            al[i] = new ArrayList<Integer>();
        }
    }


    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        checkerSize = (int) (minDim * 0.9);

        // Compute the margins so we center the puzzle
        marginX = (wid - checkerSize) / 2;
        marginY = (hit - checkerSize) / 2;
        //
        // Draw the outline of the puzzle
        //
        canvas.drawRect(marginX, marginY, marginX + checkerSize, marginY + checkerSize, fillPaint);

        scaleFactor = (float) checkerSize /
                (float) puzzleComplete.getHeight();

        int x = marginX;
        int y = marginY;
        int colortoggle = 0;

        int count = 0;

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (count > 63) {
                    x = x + checkerSize / 8;
                    continue;
                }

                canvas.drawRect((float) x, (float) y,
                        (float) (x + checkerSize / 8), (float) (y + checkerSize / 8), fillPaint_grid);

                if (store_coor == 0 && i < 65) {
                    al[count].add(x);
                    al[count].add(y);
                }

                if (colortoggle == 0) {
                    fillPaint_grid.setColor(Color.GREEN);
                    colortoggle = 1;

                } else {
                    fillPaint_grid.setColor(Color.GRAY);
                    colortoggle = 0;
                }

                count = count + 1;
                x = x + checkerSize / 8;
            }
            y = y + checkerSize / 8;
            x = marginX;
            if (colortoggle == 0) {
                fillPaint_grid.setColor(Color.GREEN);
                colortoggle = 1;
            } else {
                fillPaint_grid.setColor(Color.GRAY);
                colortoggle = 0;
            }
        }


        int count_draw = 62;
        int d = 0;
        for (int j = params.pieces.size() - 1; j > 11; j--) {
            if (params.pieces.get(j).getX() == 0 && params.pieces.get(j).getX() == 0) {
                params.pieces.get(j).setX((float) (((al[count_draw].get(0)) + checkerSize / 16) - marginX) / checkerSize);
                params.pieces.get(j).setY((float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize);

                params.pieces.get(j).setOriginalX((float) (((al[count_draw].get(0)) + checkerSize / 16) - marginX) / checkerSize);
                params.pieces.get(j).setOriginalY((float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize);
                if(j == params.pieces.size() - 1){
                    lastRow = (float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize;

                }

                params.pieces.get(j).setDiagMoveX((float) ((checkerSize / 8)) / checkerSize);
                params.pieces.get(j).setDiagMoveY((float) ((checkerSize / 8)) / checkerSize);
            }
            //((al[count_draw].get(0)) + checkerSize/16) - marginX) / checkerSize
            params.pieces.get(j).draw(canvas, (int) marginX, (int) marginY, checkerSize, scaleFactor / 4);

            d++;
            count_draw--;

            if (d == 8) {
                count_draw--;
                count_draw--;
            }
            if ((d) % 4 != 0) {
                count_draw--;

            }

        }
        count_draw = 1;
        d = 0;
        for (int j = 0; j < 12; j++) {
            if (params.pieces.get(j).getX() == 0 && params.pieces.get(j).getX() == 0) {
                params.pieces.get(j).setX((float) (((al[count_draw].get(0)) + checkerSize / 16) - marginX) / checkerSize);
                params.pieces.get(j).setY((float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize);

                params.pieces.get(j).setOriginalX((float) (((al[count_draw].get(0)) + checkerSize / 16) - marginX) / checkerSize);
                params.pieces.get(j).setOriginalY((float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize);
                if(j==0){
                    firstRow = (float) (((al[count_draw].get(1)) + checkerSize / 16) - marginY) / checkerSize;

                }

                params.pieces.get(j).setDiagMoveX((float) ((checkerSize / 8)) / checkerSize);
                params.pieces.get(j).setDiagMoveY((float) ((checkerSize / 8)) / checkerSize);
            }
            params.pieces.get(j).draw(canvas, marginX, marginY, checkerSize, scaleFactor / 4);

            d++;
            count_draw++;

            if (d == 8) {
                count_draw++;
                count_draw++;
            }
            if ((d) % 4 != 0) {
                count_draw++;

            }

        }
        store_coor = 1;
        if (selected_piece_red != null) {
            canvas.drawRect(left, top, right, bottom, outlinePaint);
        }
    }

    /**
     * Handle a touch event from the view.
     *
     * @param view  The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / checkerSize;
        float relY = (event.getY() - marginY) / checkerSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                if(!turnTaken) {

                    view.invalidate();
                    if (selected_piece_red != null && selected_piece_red.getColor().equals(color) && onTouched(relX, relY)) {
                        dragging = selected_piece_red;
                    } else if (dragging == null && selected_piece_red != null && selected_piece_red.getColor().equals(color)) {

                        dragging = selected_piece_red;
                        dragging.setX(relX);
                        dragging.setY(relY);
                        onReleased(view, relX, relY, 0);
                        dragging = null;
                        selected_piece_red = null;
                    }
                    view.invalidate();

                    return onTouched(relX, relY);
                }
                view.invalidate();


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(!turnTaken && dragging != null && dragging.getColor().equals(color)) {
                    view.invalidate();

                    return onReleased(view, relX, relY, 1);
                }
                view.invalidate();

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(!color.equals(" ") && dragging != null && dragging.getColor().equals(color)) {

                    if (!turnTaken) {
                        if (dragging != null) {
                            dragging.move(relX - lastRelX, relY - lastRelY);
                            lastRelX = relX;
                            lastRelY = relY;
                            view.invalidate();
                            return true;
                        }
                    }
                }else{
                   // dragging = null;
                }

                //break;
        }

        return false;
    }
    public void SetGameState(String king, double x , double y , int piece){
//        params.pieces.get(piece).setOriginalX((float) x);
//        params.pieces.get(piece).setOriginalY((float) y);
//        params.pieces.get(piece).setX((float) x);
//        params.pieces.get(piece).setY((float) y);

        dragging =  params.pieces.get(piece);

        if(king.equals("yes")){
            params.pieces.get(piece).setKing(true);
            KingImage(params.pieces.get(piece));
        }else if(king.equals("resign")){
            params.resign = 2;
        }
        else if(king.equals("resign1")){
            params.resign = 1;
        }

    }
    /**
     * Handle a touch message. This is when we get an initial touch
     *
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    int count = 0;

    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        if(!turnTaken) {
            for (int p = params.pieces.size() - 1; p >= 0; p--) {
                if (params.pieces.get(p).hit(x, y, checkerSize, scaleFactor / 4)) {
                    // We hit a piece!
                    dragging = params.pieces.get(p);
                    selected_piece_red = params.pieces.get(p);
                    float iop = dragging.getOriginalX() * checkerSize + marginX - (float) checkerSize / 16;
                    left = (int) iop;//- checkerSize/8;
                    iop = dragging.getOriginalY() * checkerSize + marginY - (float) checkerSize / 16;

                    top = (int) iop;//+ checkerSize/16;
                    iop = dragging.getOriginalX() * checkerSize + marginX + (float) checkerSize / 16;

                    right = (int) iop;
                    iop = dragging.getOriginalY() * checkerSize + marginY + (float) checkerSize / 16;

                    bottom = (int) iop;

                    lastRelX = x;
                    lastRelY = y;
                   // turnTaken = true;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean onTouched1(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for (int p = params.pieces.size() - 1; p >= 0; p--) {
            if (params.pieces.get(p).hit1(x, y, checkerSize, scaleFactor / 4)) {
                // We hit a piece!
                piece_del = params.pieces.get(p);
                break;
            }
        }

        return false;
    }

    /**
     * Handle a release of a touch message.
     *
     * @param x x location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    public boolean onReleased(View view, float x, float y, int popup) {
        int toggle = 0;
        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for (int p = params.pieces.size() - 1; p >= 0; p--) {
            if (params.pieces.get(p).hit1(x, y, checkerSize, scaleFactor / 4)) {
                // We hit a piece!
                piece_released1 = params.pieces.get(p);
                break;
//                lastRelX = x;
//                lastRelY = y;
            }
        }

        if (piece_released1 == null) {
            toggle = 1;
        }

        if (dragging != null) {

            if (Math.abs(dragging.getOriginalX() - x + 2 * dragging.getDiagMoveX()) < 0.09f && Math.abs(dragging.getOriginalY() - y - 2 * dragging.getDiagMoveY()) < 0.09f && (dragging.getPieceOpp() == 0 || dragging.isKing())) {
                onTouched1(dragging.getOriginalX() + dragging.getDiagMoveX(), dragging.getOriginalY() - dragging.getDiagMoveY());
                if (piece_del == null || piece_released1 != null || (piece_del.getPieceOpp() == 0 && dragging.getPieceOpp() == 0)) {
                    dragging.maybeSnap(x, y, 0);
                    if (dragging.getPopup() == 1) {
                        AlertBox(view);
                        dragging.setPopup(0);
                    }
                    view.invalidate();
                } else if (dragging.maybeSnap(x, y, 2)) {
                    if(piece_del.getPieceOpp() == 0){
                        params.green_del_count++;
                        if(params.green_del_count == 12){
                            params.resign = 1;
                        }
                    }else{
                        params.white_del_count++;
                        if(params.white_del_count == 12){
                            params.resign = 1;
                        }
                    }
                    piece_del.setOriginalX(1.2f);
                    piece_del.setOriginalY(1.2f);
                    piece_del.setX(1.2f);
                    piece_del.setY(1.2f);
                    view.invalidate();

                }
                view.invalidate();
                if (Math.abs(dragging.getOriginalY() - firstRow) < 0.05f && dragging.getPieceOpp() == 0){
                    dragging.setKing(true);
                    KingImage(dragging);
                }
                if (Math.abs(dragging.getOriginalY() - lastRow) < 0.05f && dragging.getPieceOpp() == 1){
                    dragging.setKing(true);
                    KingImage(dragging);
                }
                dragging = null;

                piece_del = null;
                piece_released1 = null;
                view.invalidate();

                return true;
            } else if (Math.abs(dragging.getOriginalX() - x - 2 * dragging.getDiagMoveX()) < 0.09f && Math.abs(dragging.getOriginalY() - y - 2 * dragging.getDiagMoveY()) < 0.09f && (dragging.getPieceOpp() == 0 || dragging.isKing())) {
                onTouched1(dragging.getOriginalX() - dragging.getDiagMoveX(), dragging.getOriginalY() - dragging.getDiagMoveY());
                if (piece_del == null || piece_released1 != null || (piece_del.getPieceOpp() == 0 && dragging.getPieceOpp() == 0) ) {
                    dragging.maybeSnap(x, y, 0);
                    if (dragging.getPopup() == 1) {
                        AlertBox(view);
                        dragging.setPopup(0);
                    }
                    view.invalidate();
                } else if (dragging.maybeSnap(x, y, 2)) {
                    if(piece_del.getPieceOpp() == 0){
                        params.green_del_count++;
                        if(params.green_del_count == 12){
                            params.resign = 1;
                        }
                    }else{
                        params.white_del_count++;
                        if(params.white_del_count == 12){
                            params.resign = 1;
                        }
                    }
                    piece_del.setOriginalX(1.2f);
                    piece_del.setOriginalY(1.2f);
                    piece_del.setX(1.2f);
                    piece_del.setY(1.2f);
                    view.invalidate();

                }
                view.invalidate();
                if (Math.abs(dragging.getOriginalY() - firstRow) < 0.05f && dragging.getPieceOpp() == 0){
                    dragging.setKing(true);
                    KingImage(dragging);
                }
                if (Math.abs(dragging.getOriginalY() - lastRow) < 0.05f && dragging.getPieceOpp() == 1){
                    dragging.setKing(true);
                    KingImage(dragging);
                }

                dragging = null;

                piece_del = null;
                piece_released1 = null;
                view.invalidate();

                return true;
            } else if (Math.abs(dragging.getOriginalX() - x - 2 * dragging.getDiagMoveX()) < 0.09f && Math.abs(dragging.getOriginalY() - y + 2 * dragging.getDiagMoveY()) < 0.09f && (dragging.getPieceOpp() == 1 || dragging.isKing())) {
                onTouched1(dragging.getOriginalX() - dragging.getDiagMoveX(), dragging.getOriginalY() + dragging.getDiagMoveY());
                if (piece_del == null || piece_released1 != null || (piece_del.getPieceOpp() == 1 && dragging.getPieceOpp() == 1)) {
                    dragging.maybeSnap(x, y, 0);
                    if (dragging.getPopup() == 1) {
                        AlertBox(view);
                        dragging.setPopup(0);
                    }
                    view.invalidate();
                } else if (dragging.maybeSnap(x, y, 2)) {
                    if(piece_del.getPieceOpp() == 0){
                        params.green_del_count++;
                        if(params.green_del_count == 12){
                            params.resign = 1;
                        }
                    }else{
                        params.white_del_count++;
                        if(params.white_del_count == 12){
                            params.resign = 1;
                        }
                    }
                    piece_del.setOriginalX(1.2f);
                    piece_del.setOriginalY(1.2f);
                    piece_del.setX(1.2f);
                    piece_del.setY(1.2f);
                    view.invalidate();

                }
                view.invalidate();
                if (Math.abs(dragging.getOriginalY() - firstRow) < 0.05f && dragging.getPieceOpp() == 0){
                    dragging.setKing(true);
                    KingImage(dragging);
                }
                if (Math.abs(dragging.getOriginalY() - lastRow) < 0.05f && dragging.getPieceOpp() == 1){
                    dragging.setKing(true);
                    KingImage(dragging);
                }

                dragging = null;

                piece_del = null;
                piece_released1 = null;
                return true;
            } else if (Math.abs(dragging.getOriginalX() - x + 2 * dragging.getDiagMoveX()) < 0.09f && Math.abs(dragging.getOriginalY() - y + 2 * dragging.getDiagMoveY()) < 0.09f && (dragging.getPieceOpp() == 1 || dragging.isKing())) {
                onTouched1(dragging.getOriginalX() + dragging.getDiagMoveX(), dragging.getOriginalY() + dragging.getDiagMoveY());
                if (piece_del == null || piece_released1 != null || (piece_del.getPieceOpp() == 1 && dragging.getPieceOpp() == 1)) {
                    dragging.maybeSnap(x, y, 0);
                    if (dragging.getPopup() == 1) {
                        AlertBox(view);
                        dragging.setPopup(0);
                    }
                    view.invalidate();
                } else if (dragging.maybeSnap(x, y, 2)) {
                    if(piece_del.getPieceOpp() == 0){
                        params.green_del_count++;
                        if(params.green_del_count == 12){
                            params.resign = 1;
                        }
                    }else{
                        params.white_del_count++;
                        if(params.white_del_count == 12){
                            params.resign = 1;
                        }
                    }
                    piece_del.setOriginalX(1.2f);
                    piece_del.setOriginalY(1.2f);
                    piece_del.setX(1.2f);
                    piece_del.setY(1.2f);
                    view.invalidate();

                }
                if (Math.abs(dragging.getOriginalY() - firstRow) < 0.05f && dragging.getPieceOpp() == 0){
                    dragging.setKing(true);
                    KingImage(dragging);
                }
                if (Math.abs(dragging.getOriginalY() - lastRow) < 0.05f && dragging.getPieceOpp() == 1){
                    dragging.setKing(true);
                    KingImage(dragging);
                }

                dragging = null;

                piece_del = null;
                piece_released1 = null;
                view.invalidate();

                return true;
            }
            if (dragging.maybeSnap(x, y, toggle)) {
                // We have snapped into place
                view.invalidate();
            }
            if (dragging.getPopup() == 1) {
                AlertBox(view);
                dragging.setPopup(0);
            }

            if (Math.abs(dragging.getOriginalY() - firstRow) < 0.05f && dragging.getPieceOpp() == 0){
                dragging.setKing(true);
                KingImage(dragging);
            }
            if (Math.abs(dragging.getOriginalY() - lastRow) < 0.05f && dragging.getPieceOpp() == 1){
                dragging.setKing(true);
                KingImage(dragging);
            }
            view.invalidate();
            dragging.setPopup(0);
            dragging = null;
            piece_released1 = null;
            return true;
        }

        return false;

    }

    public void AlertBox(View view) {

        // The puzzle is done
        // Instantiate a dialog box builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(view.getContext());

        // Parameterize the builder
        builder.setTitle(R.string.hurrah);
        builder.setMessage(R.string.completed_puzzle);
        builder.setPositiveButton(android.R.string.ok, null);

        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        view.invalidate();
    }
    public static class Parameters implements Serializable {
        public transient ArrayList<CheckerPiece> pieces = new ArrayList<CheckerPiece>();

        public String name1 = "Aditya";
        public String name2 = "BOND";
        public int current_Turn = 1;

        public int white_del_count = 0;
        public int green_del_count = 0;
        public int resign = 0;

    }

    public static class Turn implements Serializable {
        String piece;
        String x;
        String y;
        String king;
        String user;

    }
    Turn T = new Turn();;
    public void TurnSet(CheckerPiece piece, boolean king, double x, double y){

        T.x = Double.toString(x);
        T.y = Double.toString(y);
        if (king){
            T.king = "yes";
        }else{
            T.king = "no";
        }

        for (int i = 0; i < 24; i++) {
            if(piece == params.pieces.get(i)){
                T.piece = Integer.toString(i);
            }
        }

    }

    public void KingImage(CheckerPiece piece){
        piece.setPieceToKing();

    }

}
