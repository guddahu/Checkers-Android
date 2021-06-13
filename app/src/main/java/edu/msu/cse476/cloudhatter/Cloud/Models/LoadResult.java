package edu.msu.cse476.cloudhatter.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "checker")
public class LoadResult {
    @Attribute(name = "msg", required = false)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Attribute
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoadResult() {}

    public LoadResult(String status, String msg, Hat hat,String player) {
        this.status = status;
        this.message = msg;
        //this.opponent = player;
    }
}
