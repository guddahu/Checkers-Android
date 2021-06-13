package edu.msu.cse476.cloudhatter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.fragment.app.DialogFragment;
import android.widget.Toast;

import edu.msu.cse476.cloudhatter.Cloud.Cloud;

public class SignUpDlg extends DialogFragment {

    private AlertDialog dlg;

    /**
     * Create the dialog box
     * @param savedInstanceState The saved instance bundle
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle("Signing Up");

        save(" a", " a");
        // Create the dialog box
        dlg = builder.create();
        return dlg;
    }

    /**
     * Actually save the hatting
     * @param user name to save it under
     */
    public void save(final String user, final String password) {
        if (!(getActivity() instanceof SignUp)) {
            return;
        }
        final SignUp activity = (SignUp) getActivity();
        final LogInView view = (LogInView) activity.findViewById(R.id.SignupView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.saveToCloud(user, password, view);
                if(!ok) {
                    dlg.dismiss();
                    view.post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(view.getContext(),
                                    R.string.account_not_created,
                                    Toast.LENGTH_SHORT).show();
                        }

                    });


                }else{
                    dlg.dismiss();
                    view.post(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(),
                                    R.string.account_created,
                                    Toast.LENGTH_SHORT).show();
                        }

                    });


                }

            }
        }).start();

    }

}
