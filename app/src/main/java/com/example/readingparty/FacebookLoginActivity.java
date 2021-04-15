package com.example.readingparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacebookLoginActivity extends AppCompatActivity {
private TextView userId;
private ImageView profilePicture;
private LoginButton loginButton;

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_layout);

        userId=findViewById(R.id.userId);
        profilePicture=findViewById(R.id.profilePicture);
        loginButton=findViewById(R.id.login_button2);

        callbackManager= CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                userId.setText(loginResult.getAccessToken().getUserId());
                String imageURL="https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Toast.makeText(FacebookLoginActivity.this, "it works", Toast.LENGTH_SHORT).show();
                //Picasso.with().load(imageURL).into(profilePicture);
            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookLoginActivity.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
        final Activity activity = this;

        findViewById(R.id.login_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("fb_login_sdk", "click");
                List<String> perm = new ArrayList<String>();
                perm.add("user_friends");
                LoginManager.getInstance().logInWithReadPermissions(activity, perm);

            }});

        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
