package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

import edu.msu.cse476.cloudhatter.Cloud.Cloud;

/**
 * TODO: document your custom view class.
 */
public class LogInView extends View {

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }


    public void setPassword2(String password) {
        this.password2 = password;
    }
    String username;

    public boolean isPassmatch() {
        return passmatch;
    }

    public void setPassmatch(boolean passmatch) {
        this.passmatch = passmatch;
    }

    boolean passmatch = false;
    public String getUsername() {
        return username;
    }

    public String getPassword1() {
        return password1;
    }

    String password1;
    String password2;

    public LogInView(Context context) {
        super(context);
        init(null, 0);
    }

    public LogInView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LogInView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void CreateUser(String name, XmlSerializer xml) throws IOException {

    }

    public void saveXml(String name, XmlSerializer xml) throws IOException {
        xml.startTag(null, "checkered");

        xml.attribute(null, "username", username);
        xml.attribute(null, "password", password1);


        xml.endTag(null,  "checkered");
    }
}
