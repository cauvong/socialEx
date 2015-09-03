package com.example.vynv.socialnetworking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vynv.socialnetworking.googleplus.GoogleLoginActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button loginFaceBook;
    private Button loginTwitter;
    private Button loginGoogle;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFaceBook=(Button)findViewById(R.id.login_facebook);
        loginFaceBook.setOnClickListener(this);
        loginTwitter=(Button)findViewById(R.id.login_twitter);
        loginTwitter.setOnClickListener(this);
        loginGoogle=(Button)findViewById(R.id.login_google);
        loginGoogle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_facebook:

                intent=new Intent(getApplicationContext(),FacebookActivity.class);
                startActivity(intent);
                break;
            case R.id.login_twitter:
                intent=new Intent(getApplicationContext(),TwitterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_google:
                intent=new Intent(getApplicationContext(),GoogleLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
