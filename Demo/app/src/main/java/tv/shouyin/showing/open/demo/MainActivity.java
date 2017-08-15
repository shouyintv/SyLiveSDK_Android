package tv.shouyin.showing.open.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;
import tv.shouyin.showing.open.demo.live.PushActivity;
import tv.shouyin.showing.open.demo.live.WatchActivity;
import tv.shouyin.showing.open.sdk.open.SyLiveSDK;
import tv.shouyin.showing.open.sdk.open.SyLiveSDKInterface;

public class MainActivity extends AppCompatActivity implements SyLiveSDKInterface.SyLoginCallback {

    //appId 替换为你的appId
    private static final String appId = "qm808089b28ef636f0";
    public SyLiveSDK syLiveSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sy_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syLiveSDK = SyLiveSDK.getInstance();
                syLiveSDK.registerApp(MainActivity.this.getApplicationContext(), appId);
            }
        });
        findViewById(R.id.sy_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    syLiveLogin();
                }
            }
        });

        findViewById(R.id.sy_hall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    syLiveSDK.lumpToLiveHall(MainActivity.this);
                }
            }
        });

//        findViewById(R.id.sy_follow).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (syLiveSDK != null) {
//                    syLiveSDK.lumpToListFollow(MainActivity.this);
//                }
//            }
//        });
//
//        findViewById(R.id.sy_hot).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (syLiveSDK != null) {
//                    syLiveSDK.lumpToListHot(MainActivity.this);
//                }
//            }
//        });
//
//        findViewById(R.id.sy_latest).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (syLiveSDK != null) {
//                    syLiveSDK.lumpToListLatest(MainActivity.this);
//                }
//            }
//        });

        findViewById(R.id.sy_watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    // 1851753316
                    syLiveSDK.lumpToLiveWatch(MainActivity.this, 17819664);
                }
            }
        });

        findViewById(R.id.sy_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    syLiveSDK.lumpToPushStream(MainActivity.this);
                }
            }
        });
        findViewById(R.id.sy_custom_watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    //"http://flvdh.quanmin.tv/live/44397_L4.flv"
                    WatchActivity.launch(MainActivity.this, 18771118, "http://flvdh.quanmin.tv/live/1128066164.flv");
                }
            }
        });
        findViewById(R.id.sy_custom_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (syLiveSDK != null) {
                    new RxPermissions(MainActivity.this)
                            .request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean granted) {
                                    if (granted) {
                                        PushActivity.launch(MainActivity.this);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void syLiveLogin() {
        // 线上
        syLiveSDK.login("f82687cfda6bb3ee87adcf4c5e44e81d", "1", this);
        //  测试
//        syLiveSDK.login("38cef392897b3c6250ac19e10e1d245d", "1", this);
    }

    @Override
    public void onLoginResult(int code) {

    }
}
