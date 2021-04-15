package com.example.readingparty;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private EditText txtName, txtUsername, txtEmail, txtPass, txtPassRepeat;
    private TextView txtWarnName, txtWarnUsername,txtWarnEmail, txtWarnPass,txtWarnPassRepeat, txtWarnPassMatch,txtWarnAge;
    private Button btnCreateAccount;
    private ConstraintLayout parent;
    private EditText txtAge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        initViews();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SignUpCompleted();
                //creating user entity

                UserEntity userEntity=new UserEntity();
                userEntity.setName(txtName.getText().toString());
                userEntity.setAge(txtAge.getText().toString());
                userEntity.setEmail(txtEmail.getText().toString());
                userEntity.setPassword(txtPass.getText().toString());
                userEntity.setUsername(txtUsername.getText().toString());

                if(validateData(userEntity)){
                    UserDatabase userDatabase=UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao=userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SignUpCompleted();

                                }
                                });
                            }
                    }).start();

                    }
                else {
                    Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();
                }

                }



            });





    }

    public void showSnackBar(){
        txtWarnUsername.setVisibility(View.GONE);
        txtWarnPass.setVisibility(View.GONE);
        txtWarnPassRepeat.setVisibility(View.GONE);
        txtWarnPassMatch.setVisibility(View.GONE);
        txtWarnName.setVisibility(View.GONE);
        txtWarnEmail.setVisibility(View.GONE);
        txtWarnAge.setVisibility(View.GONE);

    }

    public void SignUpCompleted(){


            showSnackBar();
            Snackbar.make(parent, "Account created", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Dismiss", new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            txtName.setText("");
                            txtPass.setText("");
                            txtPassRepeat.setText("");
                            txtEmail.setText("");
                            txtUsername.setText("");
                            txtAge.setText("");
                        }
                    }).show();
        }





//    private boolean validateData(UserEntity userEntity){
//        if (txtName.getText().toString().equals("")) {
//            txtWarnName.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if(txtEmail.getText().toString().equals("")){
//            txtWarnEmail.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if(txtUsername.getText().toString().equals("")){
//            txtWarnUsername.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if (txtPass.getText().toString().equals("")){
//            txtWarnPass.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if (txtPassRepeat.getText().toString().equals("")){
//            txtWarnPassRepeat.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if(!txtPassRepeat.getText().toString().equals(txtPass.getText().toString())){
//            txtWarnPassMatch.setVisibility(View.VISIBLE);
//            return false;
//        }
//        return true;
//    }



    private boolean validateData(UserEntity userEntity){
        if (userEntity.getName().isEmpty()) {
            txtWarnName.setVisibility(View.VISIBLE);
            return false;
        }
        if(userEntity.getEmail().isEmpty()){
            txtWarnEmail.setVisibility(View.VISIBLE);
            return false;
        }
        if(userEntity.getUsername().isEmpty()){
            txtWarnUsername.setVisibility(View.VISIBLE);
            return false;
        }
        if (userEntity.getPassword().isEmpty()){
            txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        }

        if(userEntity.getAge().isEmpty()){
            txtWarnAge.setVisibility(View.VISIBLE);
            return false;
        }

        if (txtPassRepeat.getText().toString().equals("")){
            txtWarnPassRepeat.setVisibility(View.VISIBLE);
            return false;
        }
        if(!txtPassRepeat.getText().toString().equals(txtPass.getText().toString())){
            txtWarnPassMatch.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }




        private void initViews(){
        btnCreateAccount=findViewById(R.id.btnCreateAccount);
        txtName=findViewById(R.id.txtNameSignUp);
        txtEmail=findViewById(R.id.txtEmail);
        txtUsername=findViewById(R.id.txtUsernameSignUp);
        txtPass=findViewById(R.id.txtPassSignUp);
        txtPassRepeat=findViewById(R.id.txtReEntPass);
        txtWarnName=findViewById(R.id.txtWarnName);
        txtWarnEmail=findViewById(R.id.txtWarnEmail);
        txtWarnUsername=findViewById(R.id.txtWarnUsernameSignUp);
        txtWarnPass=findViewById(R.id.txtWarnPassSignUp);
        txtWarnPassMatch=findViewById(R.id.txtWarnMatchPass);
        txtWarnPassRepeat=findViewById(R.id.txtWarnPassRepeatSignUp);
        parent=findViewById(R.id.parent);
        txtAge=findViewById(R.id.txtAge);
        txtWarnAge=findViewById(R.id.txtWarnAge);

    }


}