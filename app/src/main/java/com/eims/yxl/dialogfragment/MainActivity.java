package com.eims.yxl.dialogfragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginDialogFragment.LoginInputComplete {

    EditNameDialogFragment editDialog;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBarDarkMode(true, MainActivity.this);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Button btn_edit = (Button) findViewById(R.id.edit);
        Button btn_login = (Button) findViewById(R.id.login);
        Button btn_more = (Button) findViewById(R.id.more);

        btn_edit.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        //获取手机信息
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("手机型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                EditNameDialogFragment editDialog = new EditNameDialogFragment();
                editDialog.show(getFragmentManager(), "EditDialog");
                break;
            case R.id.login:
                LoginDialogFragment dialogFragment = new LoginDialogFragment();
                dialogFragment.show(getFragmentManager(), "LoginDialog");
                break;
            case R.id.more:
                showInDifferentScreen();
                break;
            default:
                break;
        }
    }

    private void showInDifferentScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        if (editDialog != null && editDialog.isVisible()){
            editDialog.dismiss();
            return;
        }
        editDialog = new EditNameDialogFragment();
        boolean isLarge = getResources().getBoolean(R.bool.large_layout);
        if (isLarge){
            editDialog.show(fragmentManager, "dialog");
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.id_ly, editDialog).commit();
        }
    }

    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(MainActivity.this, "username："+username+", password："+password,Toast.LENGTH_SHORT).show();
    }

    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
