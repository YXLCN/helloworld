package com.eims.yxl.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * LoginDialog
 * Created by o9ik9ko on 2016/1/6.
 */
public class LoginDialogFragment extends DialogFragment{

    EditText et_username;
    EditText et_password;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null, false);
        et_username = (EditText) view.findViewById(R.id.id_txt_username);
        et_password = (EditText) view.findViewById(R.id.id_txt_password);
        builder.setView(view).setPositiveButton("sign in",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginInputComplete listener = (LoginInputComplete) getActivity();
                        listener.onLoginInputComplete(et_username.getText().toString(),
                                et_password.getText().toString());
                    }
                }).setNegativeButton("cancel", null);
        return builder.create();
    }

    public interface LoginInputComplete{
        void onLoginInputComplete(String username, String password);
    }
}
