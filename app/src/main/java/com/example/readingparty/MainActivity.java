package com.example.readingparty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;

import com.facebook.FacebookException;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnSignIn, btnSignUp;
    private ConstraintLayout parent;
    private LoginButton loginButton;
    private EditText username, password;
    private TextView txtWarnUsername, txtWarnPass;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String KEY_USER="username";
    private static final String KEY_PASSWORD="password";


    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //SharedPreferences sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //sharedpreferencesss=getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
        String login = sharedPreferences.getString(KEY_USER, null);


        if (login==null) {

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String txtUsername = username.getText().toString();
                    String txtPass = password.getText().toString();

                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (userDao.recordExistsUSername(username.getText().toString(), password.getText().toString())) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SignInCompleted();
                                        //btnSignIn.setText("Sign Out");
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Snackbar.make(parent, "Incorrect username or password. If you don't have an account please, SIGN UP first. :)", Snackbar.LENGTH_INDEFINITE)
                                                .setAction("Dismiss", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        password.setText("");
                                                        username.setText("");
                                                    }
                                                }).show();

                                    }
                                });
                            }
                        }
                    }).start();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USER, txtUsername);
                    editor.putString(KEY_PASSWORD, txtPass);
                    editor.apply();

                }
            });
        }else {

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Already logged in", Toast.LENGTH_LONG).show();
                }
            });

        }
        loginButton=findViewById(R.id.login_button);
        callbackManager= CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(MainActivity.this, "it works", Toast.LENGTH_SHORT).show();
                //Picasso.with().load(imageURL).into(profilePicture);
            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {
                //Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
            }
        });

        //final Activity activity = this;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //List<String> perm = new ArrayList<String>();
                //perm.add("user_friends");
                //LoginManager.getInstance().logInWithReadPermissions(activity, perm);

            }});

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });


    }
//TODO daca userul e deja logat e directionat la Home
    //TODO adaug buton de sign out
/////////////////////////////////////////////////////////
////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//////////////////////
private void showSnackBar(){
    txtWarnUsername.setVisibility(View.GONE);
    txtWarnPass.setVisibility(View.GONE);

}
///////////////////////////////////////////
    public void initViews(){
        btnSignIn=findViewById(R.id.btnSignIn);
        username=findViewById(R.id.txtUsername);
        password=findViewById(R.id.txtPassword);
        txtWarnPass=findViewById(R.id.txtWarnPass);
        txtWarnUsername=findViewById(R.id.txtWarnUsername);
        parent=findViewById(R.id.parent);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);
        parent=findViewById(R.id.parent);
    }
////////////////////////////////////////////
    private void SignInCompleted(){

        if(validateData()) {
            showSnackBar();
        }
        Snackbar.make(parent, "User Registered", Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        password.setText("");
                        username.setText("");
                    }
                }).show();}

///////////////////////////////////////////
    private boolean validateData(){
        if(username.getText().toString().equals("")){
            txtWarnUsername.setVisibility(View.VISIBLE);
            return false;
        }
        if(password.getText().toString().equals("")){
            txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }
}


