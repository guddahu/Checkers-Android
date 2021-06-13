package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import edu.msu.cse476.cloudhatter.Cloud.Cloud;
import edu.msu.cse476.cloudhatter.Cloud.Models.Hat;

public class LoginDlg  extends DialogFragment {
    /**
     * Id for the image we are loading
     */
    private String catId;

    public boolean isPass1() {
        return pass1;
    }

    boolean pass1 = false;
    /**
     * Create the dialog box
     */

    /**
     * Set true if we want to cancel
     */
    private volatile boolean cancel = false;

    private final static String ID = "id";
    private AlertDialog dlg;

    private String username, pass;
    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.LoggingIn);


        // Create the dialog box
         dlg = builder.create();

        // Get a reference to the view we are going to load into
        final LogInView view = (LogInView) getActivity().findViewById(R.id.Loginview);

        username = view.getUsername();
        pass = view.getPassword1();
        /*
         * Create a thread to load the hatting from the cloud
         */


        save(" a", " a");

        return dlg;
    }

    /**
     * Actually save the hatting
     * @param user name to save it under
     */
    public void save(final String user, final String password) {
        if (!(getActivity() instanceof StartActivity)) {
            return;
        }
        final StartActivity activity = (StartActivity) getActivity();
        final LogInView view = (LogInView) activity.findViewById(R.id.Loginview);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Cloud cloud = new Cloud();
                boolean ok = cloud.openFromCloud(username, pass);
                // hat loaded successfully
                if(ok) {
                    pass1 = true;
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            dlg.dismiss();
                            // Success!
                            view.setPassmatch(true);
                            Toast.makeText(view.getContext(),
                                    R.string.LoginPass,
                                    Toast.LENGTH_SHORT).show();
                            pass1 = true;

                            activity.onLoginMove();

                        }
                    });

                }else{

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            dlg.dismiss();
                            Toast.makeText(view.getContext(),
                                    R.string.invalid_login,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

        }).start();
    }

    /**
     * Save the instance state
     */
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString(ID, catId);
    }

    /**
     * Called when the view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel = true;
    }
}
