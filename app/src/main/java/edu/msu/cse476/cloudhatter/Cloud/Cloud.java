package edu.msu.cse476.cloudhatter.Cloud;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import edu.msu.cse476.cloudhatter.Checker;
import edu.msu.cse476.cloudhatter.Cloud.Models.Catalog;
import edu.msu.cse476.cloudhatter.Cloud.Models.GameStart;
import edu.msu.cse476.cloudhatter.Cloud.Models.Hat;
import edu.msu.cse476.cloudhatter.Cloud.Models.Item;
import edu.msu.cse476.cloudhatter.Cloud.Models.LoadResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.PullResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.PushResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.SaveResult;
import edu.msu.cse476.cloudhatter.LogInView;
import edu.msu.cse476.cloudhatter.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class Cloud {

    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String USER = "rajadit1";
    private static final String PASSWORD = "Helloworld";
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~rajadit1/cse476/proj2/";
    //public static final String CATALOG_PATH = "hatter-cat.php";
    public static final String SAVE_PATH = "Checker-save.php";
    //public static final String DELETE_PATH = "hatter-delete.php";
    public static final String LOAD_PATH = "Checker-Load.php";
    public static final String JOIN_PATH = "Checker-JoinPlayer.php";
    public static final String GAME_START = "Checker-MatchPlayer.php";
    public static final String PULL = "Checker-Pull.php";
    public static final String PUSH = "Checker-Push.php";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();


    String opponent;

    public String getKing() {
        return king;
    }

    public String getUser() {
        return user;
    }

    public String getPiece() {
        return piece;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    String king;
    String user;
    String piece;
    String x;
    String y;

    /**
     * Open a connection to a hatting in the cloud.
     * @param user id for the hatting
     * @return reference to an input stream or null if this fails
     */
    public boolean openFromCloud(final String user, final String password) {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<LoadResult> response = service.loadHat(user, MAGIC, password).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return false;
            }

            LoadResult result = response.body();
            if (result.getStatus().equals("yes")) {
                return true;
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
        catch (Exception e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
    }

    public boolean join(String user) {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<LoadResult> response = service.JoinPlayer(user, MAGIC).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return false;
            }

            LoadResult result = response.body();
            if (result.getStatus().equals("yes")) {
                return true;
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
        catch (Exception e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }

    }

    public String getOpponent() {
        return opponent;
    }

    public boolean push(String user1,String piece,String king,String x,String y) {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<PushResult> response = service.PushResult(user1,piece,king,x,y).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return false;
            }

            PushResult result = response.body();
            if (result.getStatus().equals("yes")) {
                return true;
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
        catch (Exception e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }

    }

    public boolean pull() {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<PullResult> response = service.PullResult().execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return false;
            }

            PullResult result = response.body();
            if (result.getStatus().equals("yes")) {
                king = result.getKing();
                x = result.getX();
                y = result.getY();
                piece = result.getPiece();
                user = result.getUser();

                return true;
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
        catch (Exception e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }

    }

    public boolean check_start(String user) {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<GameStart> response = service.GameStart(user, MAGIC).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return false;
            }

            GameStart result = response.body();
            if (result.getStatus().equals("yes")) {
                if(result.getMessage().equals("start3")){
                    opponent = "";

                    return true;
                }
                opponent = result.getOpponent();
                return true;
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }
        catch (Exception e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return false;
        }

    }
        /**
         * Save a hatting to the cloud.
         * This should be run in a thread.
         * @param name name to save under
         * @param view view we are getting the data from
         * @return true if successful
         */
    public boolean saveToCloud(String name, String password, LogInView view) {
        name = name.trim();
        if(name.length() == 0) {
            return false;
        }
        // Create an XML packet with the information about the current image
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            xml.setOutput(writer);

            xml.startDocument("UTF-8", true);

            xml.startTag(null, "checker");
//            xml.attribute(null, "user", USER);
//            xml.attribute(null, "pw", PASSWORD);
//            xml.attribute(null, "magic", MAGIC);
            xml.attribute(null, "magic", MAGIC);

            view.saveXml(name, xml);

            xml.endTag(null, "checker");

            xml.endDocument();

        } catch (IOException e) {
            // This won't occur when writing to a string
            return false;
        }

        HatterService service = retrofit.create(HatterService.class);
        try {
            SaveResult result = service.saveHat(writer.toString()).execute().body();
            if (result.getStatus() != null && result.getStatus().equals("yes")) {
                return true;
            }
            Log.e("SaveToCloud", "Failed to save, message = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("SaveToCloud", "Exception occurred while trying to save hat!", e);
            return false;
        }
    }

}
