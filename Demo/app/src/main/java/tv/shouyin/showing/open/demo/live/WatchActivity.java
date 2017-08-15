package tv.shouyin.showing.open.demo.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tv.shouyin.showing.open.demo.R;
import tv.shouyin.showing.open.sdk.open.SyLiveConfig;
import tv.shouyin.showing.open.sdk.open.SyLiveRoomAssist;
import tv.shouyin.showing.open.sdk.open.SyLiveSDKInterface;
import tv.shouyin.showing.open.sdk.widget.SyChatRoomView;
import tv.shouyin.showing.open.sdk.widget.SyPlayerView;

public class WatchActivity extends AppCompatActivity implements SyLiveSDKInterface.SyLiveRoomCallback {

    private static final String ROOM_ID_KEY = "ROOM_ID_KEY";
    private static final String PLAYER_URL_KEY = "PLAYER_URL_KEY";
    private SyPlayerView playerView;
    private SyChatRoomView chatRoomView;
    private SyLiveRoomAssist liveRoomAssist;
    private int roomId;
    private String url;

    public static void launch(Context context, int roomId, String stream) {
        Intent intent = new Intent(context, WatchActivity.class);
        intent.putExtra(ROOM_ID_KEY, roomId);
        intent.putExtra(PLAYER_URL_KEY, stream);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        roomId = getIntent().getIntExtra(ROOM_ID_KEY, 0);
        url = getIntent().getStringExtra(PLAYER_URL_KEY);
        playerView = (SyPlayerView) findViewById(R.id.sy_player_view);
        chatRoomView = (SyChatRoomView) findViewById(R.id.sy_chat_room_view);
        liveRoomAssist = new SyLiveRoomAssist();
        liveRoomAssist.joinRoom(roomId);
        liveRoomAssist.setSyLiveRoomCallback(this);
        liveRoomAssist.setChatRoomView(chatRoomView);
        liveRoomAssist.setPlayerView(playerView);
        liveRoomAssist.playWithURL(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveRoomAssist.leaveRoom(roomId);
        liveRoomAssist.playerRelease();
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
        /*用户进入房间*/
    }
}
