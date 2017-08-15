package tv.shouyin.showing.open.demo.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import tv.shouyin.showing.open.demo.R;
import tv.shouyin.showing.open.sdk.open.SyLiveConfig;
import tv.shouyin.showing.open.sdk.open.SyLiveRoomAssist;
import tv.shouyin.showing.open.sdk.open.SyLiveSDK;
import tv.shouyin.showing.open.sdk.open.SyLiveSDKInterface;
import tv.shouyin.showing.open.sdk.widget.SyChatRoomView;
import tv.shouyin.showing.open.sdk.widget.SyPusherView;

public class PushActivity extends AppCompatActivity implements SyLiveSDKInterface.SyLiveRoomCallback {

    private SyPusherView pusherView;
    private SyChatRoomView chatRoomView;
    private SyLiveRoomAssist liveRoomAssist;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PushActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        pusherView = (SyPusherView) findViewById(R.id.sy_pusher_view);
        chatRoomView = (SyChatRoomView) findViewById(R.id.sy_chat_room_view);
        liveRoomAssist = new SyLiveRoomAssist();
        liveRoomAssist.setPusherView(pusherView);
        liveRoomAssist.setChatRoomView(chatRoomView);
        liveRoomAssist.setSyLiveRoomCallback(this);
        findViewById(R.id.start_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPush();
                v.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.switch_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveRoomAssist.switchCamera();
            }
        });
        final CheckBox switchBeauty = (CheckBox) findViewById(R.id.switch_beauty);
        switchBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PushActivity", "switchBeauty:" + switchBeauty.isChecked());
                liveRoomAssist.setBeauty(switchBeauty.isChecked());
            }
        });
        final CheckBox switchFlash = (CheckBox) findViewById(R.id.switch_flash);
        switchFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PushActivity", "switchFlash:" + switchFlash.isChecked());
                liveRoomAssist.setFlash(switchFlash.isChecked());
            }
        });
        switchBeauty.setChecked(liveRoomAssist.isBeauty());
        switchFlash.setChecked(liveRoomAssist.isFlash());
    }

    private void startPush() {
        SyLiveConfig.SyLivePushConfig config = new SyLiveConfig.SyLivePushConfig("杭州市", "测试", null);
        liveRoomAssist.startPush(config, new SyLiveSDKInterface.SyPushStatusCallback() {
            @Override
            public void onResult(@NonNull int code, @Nullable String message) {
                if (code == 0) {
                    // succeed
                } else {
                    //filed
                }
            }
        });
        liveRoomAssist.joinRoom(SyLiveSDK.getInstance().loginUserRoomId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preDestroy();
    }

    private void preDestroy() {
        if (liveRoomAssist != null) {
            liveRoomAssist.stopPush();
            liveRoomAssist.leaveRoom(SyLiveSDK.getInstance().loginUserRoomId());
        }
    }

    @Override
    public void onRoomKickOutReason(String message) {
       /*被踢出房间*/
    }

    @Override
    public void onRoomForbid() {
        /*被禁止进入房间*/
    }

    @Override
    public void onRoomLiveStreamClose() {
        /*流关闭(直播结束)*/
    }

    @Override
    public void onRoomAnchorStatus(int status) {
        /*主播状态变更 status:1主播暂时离开,status:2主播回来*/
    }

    @Override
    public void onRoomGiftNotify(SyLiveConfig.SyLiveGiftNotify giftNotify) {
        /*接受礼物消息*/
    }

    @Override
    public void onRoomInfoUpdate(SyLiveConfig.SyLiveRoomInfo roomInfo) {
        /*房间信息更新*/
    }

    @Override
    public void onRoomUserJoin(SyLiveConfig.SyLiveUser user) {

    }
}
