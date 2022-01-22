package com.tks.wearosheartbeatsample;

import static com.tks.wearosheartbeatsample.Constants.ACTION.FINALIZEFROMS;
import static com.tks.wearosheartbeatsample.Constants.NOTIFICATION_CHANNEL_STARTSTOP;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class UwsHeartBeatService extends Service {
	private SensorManager mSensorManager;
	private final SensorEventCallback mSensorEventCallback = new SensorEventCallback() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			super.onSensorChanged(event);

			if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
				int hb = (int)event.values[0];
				TLog.d("bbbbbbbbbb 脈拍取得 heartbeat = {0}", hb);
//				mViewModel.HeartBeat().postValue(hb);
				try { mOnHeartBeatListner.OnHeartBeatChanged(hb); }
				catch(RemoteException e) { e.printStackTrace(); }
			}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		switch (intent.getAction()) {
			case Constants.ACTION.INITIALIZE:
				TLog.d("xxxxx startForeground.");
				startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
				TLog.d("脈拍 開始");
				mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
				Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
				mSensorManager.registerListener(mSensorEventCallback, sensor, SensorManager.SENSOR_DELAY_NORMAL);
				break;
			case Constants.ACTION.FINALIZE:
				TLog.d("xxxxx stopForeground.");
				TLog.d("脈拍 停止");
				mSensorManager.unregisterListener(mSensorEventCallback);
				stopForeground(true);
				stopSelf();
				reqAppFinish();
				break;
		}
		return START_NOT_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	IOnHeartBeatListner mOnHeartBeatListner;
	private final Binder mBinder = new IHeartBeatService.Stub() {
		@Override
		public void setOnHeartBeatListner(IOnHeartBeatListner listner) {
			mOnHeartBeatListner = listner;
		}
	};


	/* ***********************/
	/* フォアグランドサービス機能 */
	/* ***********************/
	private Notification prepareNotification() {
		/* 通知のチャンネル生成 */
		NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_STARTSTOP, "startstop", NotificationManager.IMPORTANCE_DEFAULT);
		channel.enableVibration(false);
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(channel);

		/* 停止ボタン押下の処理実装 */
		Intent stopIntent = new Intent(this, UwsHeartBeatService.class)
				.setAction(Constants.ACTION.FINALIZE);
		PendingIntent pendingStopIntent = PendingIntent.getService(this, 2222, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
		remoteViews.setOnClickPendingIntent(R.id.btnStop, pendingStopIntent);

		/* Notification生成 */
		return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_STARTSTOP)
				.setContent(remoteViews)
				.setSmallIcon(R.mipmap.ic_launcher)
//				.setCategory(NotificationCompat.CATEGORY_SERVICE)
//				.setOnlyAlertOnce(true)
//				.setOngoing(true)
//				.setAutoCancel(true)
//				.setContentIntent(pendingIntent);
//				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.build();
	}

	/* アプリ終了要求 */
	private void reqAppFinish() {
		Intent intent = new Intent(FINALIZEFROMS);
		LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcastSync(intent);
	}

}
