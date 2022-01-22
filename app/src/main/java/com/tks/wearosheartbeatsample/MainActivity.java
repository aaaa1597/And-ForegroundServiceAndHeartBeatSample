package com.tks.wearosheartbeatsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Pair;

import com.tks.wearosheartbeatsample.ui.FragMainViewModel;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
	private final static int	REQUEST_PERMISSIONS			= 2222;
	private	FragMainViewModel	mViewModel;
	private final ServiceConnection mCon = new ServiceConnection() {
		@Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			IHeartBeatService ServiceIf = IHeartBeatService.Stub.asInterface(iBinder);
			try {
				ServiceIf.setOnHeartBeatListner(new IOnHeartBeatListner.Stub() {
					@Override
					public void OnHeartBeatChanged(int heartbeat) {
						mViewModel.HeartBeat().postValue(heartbeat);
					}
				});
			}
			catch(RemoteException e) { e.printStackTrace(); }
		}
		@Override public void onServiceDisconnected(ComponentName componentName) {
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* ViewModelインスタンス取得 */
		mViewModel = new ViewModelProvider(this).get(FragMainViewModel.class);

		/* 権限(生体センサー)が許可されていない場合はリクエスト. */
		if (checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String[]{Manifest.permission.BODY_SENSORS}, REQUEST_PERMISSIONS);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		/* 対象外なので、無視 */
		if (requestCode != REQUEST_PERMISSIONS) return;

		/* 権限リクエストの結果を取得する. */
		long ngcnt = Arrays.stream(grantResults).filter(value -> value != PackageManager.PERMISSION_GRANTED).count();
		if (ngcnt > 0) {
			ErrDialog.create(MainActivity.this, "このアプリには必要な権限です。\n再起動後に許可してください。\n終了します。").show();
			return;
		}
	}

	private Intent mStartServiceintent = null;
	@Override
	protected void onStart() {
		super.onStart();
		startForeServ();
		bindService(new Intent(getApplicationContext(), UwsHeartBeatService.class), mCon, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(mCon);
//		stopForeServ();			通知からの終了だけをサポートする。
	}

	/* フォアグランドサービス起動 */
	private void startForeServ() {
		if(mStartServiceintent != null) {
			TLog.d("サービス起動済。処理不要.");
			return;
		}
		/* サービス起動 */
		mStartServiceintent = new Intent(MainActivity.this, UwsHeartBeatService.class);
		mStartServiceintent.setAction(Constants.ACTION.INITIALIZE);
		startForegroundService(mStartServiceintent);
	}

	/* フォアグランドサービス終了 */
	private void stopForeServ() {
		/* サービス起動済チェック */
		if(mStartServiceintent == null) {
			TLog.d("サービス起動してないので終了処理不要。");
			return;
		}
		/* サービス終了 */
		mStartServiceintent = null;
		Intent intent = new Intent(MainActivity.this, UwsHeartBeatService.class);
		intent.setAction(Constants.ACTION.FINALIZE);
		startService(intent);
	}


	/* Serviceからの終了要求 受信設定 */
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
//			unbindService(mCon);
			LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
			ErrDialog.create(MainActivity.this, "裏で動作している位置情報/BLEが終了しました。\nアプリも終了します。").show();
		}
	};
	IntentFilter mFilter = new IntentFilter();
}
