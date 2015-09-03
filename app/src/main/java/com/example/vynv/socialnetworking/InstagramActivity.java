package com.example.vynv.socialnetworking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vynv.socialnetworking.Instagram.ApplicationData;
import com.example.vynv.socialnetworking.Instagram.InstagramApp;
import com.example.vynv.socialnetworking.Instagram.RelationshipActivity;
import com.example.vynv.socialnetworking.lazyload.ImageLoader;

import java.util.HashMap;

public class InstagramActivity extends Activity implements View.OnClickListener {
    private InstagramApp mApp;
    private Button btnConnect;
    private Button btnViewInfo;
    private Button btnGetAllImages;
    private Button btnFollowers;
    private Button btnFollwing;
    private LinearLayout llAfterLoginView;
    private HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == InstagramApp.WHAT_FINALIZE) {
                userInfoHashmap = mApp.getUserInfo();
            } else {
                Toast.makeText(InstagramActivity.this, "Application Error",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        setWidgetReference();
        bindEventHandlers();
        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
        mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
            @Override
            public void onSuccess() {
                btnConnect.setText("Connected");
                llAfterLoginView.setVisibility(View.VISIBLE);
                mApp.fetchUserName(handler);
            }
            @Override
            public void onFail(String error) {
                Toast.makeText(InstagramActivity.this, error, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        if (mApp.hasAccessToken()) {
            btnConnect.setText("Connected");
            llAfterLoginView.setVisibility(View.VISIBLE);
            mApp.fetchUserName(handler);

        }

    }
    private void bindEventHandlers() {
        btnConnect.setOnClickListener(this);
        btnViewInfo.setOnClickListener(this);
        btnGetAllImages.setOnClickListener(this);
        btnFollwing.setOnClickListener(this);
        btnFollowers.setOnClickListener(this);
    }

    private void setWidgetReference() {
        llAfterLoginView = (LinearLayout) findViewById(R.id.llAfterLoginView);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnViewInfo = (Button) findViewById(R.id.btnViewInfo);
        btnGetAllImages = (Button) findViewById(R.id.btnGetAllImages);
        btnFollowers = (Button) findViewById(R.id.btnFollows);
        btnFollwing = (Button) findViewById(R.id.btnFollowing);
    }

    @Override
    public void onClick(View view) {
        String url="";
        switch (view.getId()){
            case R.id.btnConnect:
                connectOrDisconnectUser();
                break;
            case R.id.btnViewInfo:
                displayInfoDialogView();
                break;
            case R.id.btnFollows:
                url="https://api.instagram.com/v1/users/" + userInfoHashmap.get(InstagramApp.TAG_ID) + "/follows?access_token=" + mApp.getTOken();
                startActivity(new Intent(InstagramActivity.this, RelationshipActivity.class)
                        .putExtra("userInfo", url));
                break;
            case R.id.btnFollowing:
                url="https://api.instagram.com/v1/users/" + userInfoHashmap.get(InstagramApp.TAG_ID) + "/follows-by?access_token=" + mApp.getTOken();
                startActivity(new Intent(InstagramActivity.this, RelationshipActivity.class)
                        .putExtra("userInfo", url));
                break;
        }
    }
    private void connectOrDisconnectUser() {
        if (mApp.hasAccessToken()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    InstagramActivity.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    mApp.resetAccessToken();
                                    llAfterLoginView.setVisibility(View.GONE);
                                    btnConnect.setText("Connect");
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            mApp.authorize();
        }
    }
    private void displayInfoDialogView() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                InstagramActivity.this);
        alertDialog.setTitle("Profile Info");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, null);
        alertDialog.setView(view);
        ImageView ivProfile = (ImageView) view
                .findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) view.findViewById(R.id.tvUserName);
        TextView tvNoOfFollwers = (TextView) view
                .findViewById(R.id.tvNoOfFollowers);
        TextView tvNoOfFollowing = (TextView) view
                .findViewById(R.id.tvNoOfFollowing);
        new ImageLoader(InstagramActivity.this).DisplayImage(
                userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE),
                ivProfile);
        tvName.setText(userInfoHashmap.get(InstagramApp.TAG_USERNAME));
        tvNoOfFollowing.setText(userInfoHashmap.get(InstagramApp.TAG_FOLLOWS));
        tvNoOfFollwers.setText(userInfoHashmap
                .get(InstagramApp.TAG_FOLLOWED_BY));
        alertDialog.create().show();
    }
}
