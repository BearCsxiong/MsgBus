package me.csxiong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import me.csxiong.msgbus.MsgBus;
import me.csxiong.msgbus.annotation.OnReceiveMsg;
import me.csxiong.msgbus.entity.ThreadMode;
import me.csxiong.msgbusTest.R;

public class TestMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_msg);
    }

    public void onRegister(View view) {
        MsgBus.get().register(this);
    }

    public void onUnRegister(View view) {
        MsgBus.get().unregister(this);
    }

    public void onFinish(View view) {
        finish();
    }

    public void sendMessage(View view) {
        MsgBus.get().post("success");
    }

    @OnReceiveMsg(target = ThreadMode.MAIN)
    public void onReceive(Object obj) {
        Toast.makeText(this, "I can receive msg", Toast.LENGTH_LONG).show();
    }
}
