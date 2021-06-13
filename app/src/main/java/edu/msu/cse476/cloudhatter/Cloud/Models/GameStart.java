package edu.msu.cse476.cloudhatter.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "checker")
public class GameStart {
    @Attribute(name = "msg", required = false)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Attribute(name = "op", required = false)
    //@Element(required = false, name = "op")
    private String opponent;

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent1) {
        this.opponent = opponent1;
    }
    @Attribute(name = "status", required = false)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GameStart() {}

    public GameStart(String status, String msg,String player, Hat hat) {
        this.status = status;
        this.message = msg;
        this.opponent = player;
    }
}
