package me.csxiong.msgbusTest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import me.csxiong.TestMsgActivity;
import me.csxiong.msgbus.MsgBus;
import me.csxiong.msgbus.annotation.OnReceiveMsg;
import me.csxiong.msgbus.annotation.Tag;
import me.csxiong.msgbus.entity.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MsgBus.get().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MsgBus.get().unregister(this);
    }

    public void sendMessage(View view) {
        MsgBus.get().post("i'm no tag");
    }

    public void sendMessageWithTag(View view) {
        MsgBus.get().post("i'm tag", "tag");
    }

    public void startNext(View view) {
        Intent intent = new Intent(this, TestMsgActivity.class);
        startActivity(intent);
    }

    @OnReceiveMsg(target = ThreadMode.MAIN)
    public void onSave(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    @OnReceiveMsg(target = ThreadMode.BACKGROUND)
    public void onBackground(Object obj) {
        Log.e("onBackground", obj.getClass().getName());
    }

    @OnReceiveMsg(target = ThreadMode.MAIN, tags = {
            @Tag("tag")
    })
    public void onTag(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

}
