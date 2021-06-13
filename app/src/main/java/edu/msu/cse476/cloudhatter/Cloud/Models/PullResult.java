package edu.msu.cse476.cloudhatter.Cloud.Models;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "checker")
public class PullResult {
    @Attribute(name = "msg", required = false)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Attribute(name = "user")
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String opponent1) {
        this.user = opponent1;
    }
    @Attribute(name = "piece")
    private String piece;

    public String getPiece() {
        return piece;
    }

    public void setPiece(String opponent1) {
        this.piece = opponent1;
    }
    @Attribute(name = "x")
    private String x;

    public String getX() {
        return x;
    }

    public void setX(String opponent1) {
        this.x = opponent1;
    }
    @Attribute(name = "y")
    private String y;

    public String getY() {
        return y;
    }

    public void setY(String opponent1) {
        this.y = opponent1;
    }
    @Attribute(name = "king")
    private String king;

    public String getKing() {
        return king;
    }

    public void setKing(String opponent1) {
        this.king = opponent1;
    }
    @Attribute
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PullResult() {}

    public PullResult(String status, String msg, Hat hat,String user1 ,String piece, String x, String y, String king) {
        this.status = status;
        this.message = msg;
        this.user = user1;
        this.king = king;
        this.piece = piece;
        this.x = x;
        this.y = y;
    }
}
