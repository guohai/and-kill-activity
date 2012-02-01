package org.xkit.android.demo.quit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class HelloQuitActivity extends Activity {

	private static final String ACTION_XKIT_QUIT = "org.xkit.android.QUIT_IDENTIFIER";

	// 写一个广播的内部类，当收到动作时，结束activity
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			close();
			unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.button_quit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ACTION_XKIT_QUIT);
						sendBroadcast(intent);
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
		// 在当前的activity中注册广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_XKIT_QUIT);
		registerReceiver(this.broadcastReceiver, filter); // 注册
	}

	/**
	 * 关闭
	 */
	public void close() {
		Intent intent = new Intent();
		intent.setAction(ACTION_XKIT_QUIT); // 说明动作
		sendBroadcast(intent);// 该函数用于发送广播
		finish();
	}
}