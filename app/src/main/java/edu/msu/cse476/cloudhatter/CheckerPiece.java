package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.View;

import java.io.Serializable;

public class CheckerPiece implements Serializable {
    /**
     * We consider a piece to be in the right location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.05f;

    public void setFirstrow(float firstrow) {
        this.firstrow = firstrow;
    }

    float firstrow;

    public void setLastrow(float lastrow) {
        this.lastrow = lastrow;
    }

    float lastrow;
    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    Checker checker;

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    String color = " ";

    public void setPiece(Bitmap piece) {
        this.piece = piece;
    }

    public void setPieceToKing() {
        this.piece = this.pieceKing;
    }

    /**
     * The image for the actual piece.
     */
    private Bitmap piece;
    private Bitmap pieceKing;

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the puzzle piece.
     */
    private float x = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * y location
     */
    private float y = 0;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public void setOriginalX(float originalX) {
        OriginalX = originalX;
    }

    public void setOriginalY(float originalY) {
        OriginalY = originalY;
    }

    public float getOriginalX() {
        return OriginalX;
    }
    public float getOriginalY() {
        return OriginalY;
    }
    private float OriginalX = 0;
    private float OriginalY = 0;

    public void setDiagMoveX(float diagMoveX) {
        DiagMoveX = diagMoveX;
    }

    public void setDiagMoveY(float diagMoveY) {
        DiagMoveY = diagMoveY;
    }

    public float getDiagMoveX() {
        return DiagMoveX;
    }
    public float getDiagMoveY() {
        return DiagMoveY;
    }
    private float DiagMoveX ;
    private float DiagMoveY ;

    public int getPieceOpp() {
        return pieceOpp;
    }

    private int pieceOpp ;

    public int getPopup() {
        return popup;
    }

    public void setPopup(int popup) {
        this.popup = popup;
    }

    private int popup = 0;

    public void setKing(boolean king) {
        isKing = king;
    }

    public boolean isKing() {
        return isKing;
    }

    private boolean isKing = false;
    public CheckerPiece(Context context, int id, float finalX, float finalY, int opp,int id1) {
        pieceOpp = opp;
        piece = BitmapFactory.decodeResource(context.getResources(), id);
        pieceKing = BitmapFactory.decodeResource(context.getResources(), id1);
        player = MediaPlayer.create(context, R.raw.soundchecker);
        playerking = MediaPlayer.create(context, R.raw.king);

    }
    MediaPlayer player;
    MediaPlayer playerking;

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param checkersize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int checkersize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * checkersize, marginY + y * checkersize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }

    /**
     * Test to see if we have touched a puzzle piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param puzzleSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int puzzleSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * puzzleSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - y) * puzzleSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Test to see if we have touched a puzzle piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param puzzleSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit1(float testX, float testY,
                        int puzzleSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - OriginalX) * puzzleSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - OriginalY) * puzzleSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }


    /**
     * Move the puzzle piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return
     */
    public boolean maybeSnap(float X, float Y, int toggle) {
        if (X <= 1f && Y <= 1f && X >= 0f && Y > 0f && (toggle == 1 || toggle == 2)) {
            if (Math.abs(OriginalX - X + DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y - DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 0 || isKing )) {
                x = OriginalX + DiagMoveX;
                y = OriginalY - DiagMoveY;

                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.setTurnTaken(true);
                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();
                return true;
            }
            if (Math.abs(OriginalX - X + 2 * DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y - 2 * DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 0 || isKing ) && toggle == 2) {

                checker.setTurnTaken(true);

                x = OriginalX + 2 * DiagMoveX;
                y = OriginalY - 2 * DiagMoveY;


                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }

            if (Math.abs(OriginalX - X - DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y - DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 0 || isKing )) {
                checker.setTurnTaken(true);

                x = OriginalX - DiagMoveX;
                y = OriginalY - DiagMoveY;

                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }

            if (Math.abs(OriginalX - X - 2 * DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y - 2 * DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 0 || isKing ) && toggle == 2) {
                checker.setTurnTaken(true);

                x = OriginalX - 2 * DiagMoveX;
                y = OriginalY - 2 * DiagMoveY;


                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }

            if (Math.abs(OriginalX - X - DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y + DiagMoveY) < SNAP_DISTANCE &&(pieceOpp == 1 || isKing )) {
                checker.setTurnTaken(true);

                x = OriginalX - DiagMoveX;
                y = OriginalY + DiagMoveY;

                OriginalX = x;
                OriginalY = y;

                ifKing();
                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }
            if (Math.abs(OriginalX - X - 2 * DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y + 2 * DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 1 || isKing ) && toggle == 2) {
                checker.setTurnTaken(true);

                x = OriginalX - 2 * DiagMoveX;
                y = OriginalY + 2 * DiagMoveY;

                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }
            if (Math.abs(OriginalX - X + DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y + DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 1 || isKing )) {
                checker.setTurnTaken(true);

                x = OriginalX + DiagMoveX;
                y = OriginalY + DiagMoveY;

                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }

            if (Math.abs(OriginalX - X + 2 * DiagMoveX) < SNAP_DISTANCE && Math.abs(OriginalY - Y + 2 * DiagMoveY) < SNAP_DISTANCE && (pieceOpp == 1 || isKing )) {
                checker.setTurnTaken(true);

                x = OriginalX + 2 * DiagMoveX;
                y = OriginalY + 2 * DiagMoveY;

                OriginalX = x;
                OriginalY = y;
                ifKing();

                checker.TurnSet(this, isKing, x,y);
                checker.setSelected_piece_red(null);
                player.start();

                return true;
            }

        }
        if(Math.abs(x - OriginalX) > DiagMoveX/2.0 || Math.abs(y - OriginalY) > DiagMoveY/2.0){
            popup = 1;

        }
        x = OriginalX;
        y = OriginalY;
        return false;
    }

    public void ifKing() {
        if (Math.abs(getOriginalY() - checker.getFirstRow()) < 0.05f && getPieceOpp() == 0){
            setKing(true);
            checker.KingImage(this);
            playerking.start();
        }
        if (Math.abs(getOriginalY() - checker.getLastRow()) < 0.05f && getPieceOpp() == 1){
            setKing(true);
            checker.KingImage(this);
            playerking.start();

        }
    }

        public void AlertBox(View view) {

        // The puzzle is done
        // Instantiate a dialog box builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(view.getContext());

        // Parameterize the builder
        //builder.setTitle(R.string.hurrah);
        //builder.setMessage(R.string.completed_puzzle);
        builder.setPositiveButton(android.R.string.ok, null);

        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
