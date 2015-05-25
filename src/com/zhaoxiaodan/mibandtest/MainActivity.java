package com.zhaoxiaodan.mibandtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zhaoxiaodan.miband.ActionCallback;
import com.zhaoxiaodan.miband.BatteryInfo;
import com.zhaoxiaodan.miband.MiBand;

public class MainActivity extends Activity
{
	private static final String	TAG					= "==[mibandtest]==";
	private static final String	DATA_KEY_MSG		= "DATA_KEY_MSG";
	private MiBand				miband;

	final Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg)
		{
			((TextView) MainActivity.this.findViewById(R.id.statusTextView)).setText(msg.getData().getString(DATA_KEY_MSG));
			return true;
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		miband = new MiBand(this);

		

		((Button) findViewById(R.id.connectButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					
					 miband.connect(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG,"connect success");
							
							changeStatus("connected!");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG,"connect fail, code:"+errorCode+",mgs:"+msg);
							
							changeStatus("connect fail");
						}
					});
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});

		((Button) findViewById(R.id.pairButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					miband.pair(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							changeStatus("pair succ");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							changeStatus("pair fail");
						}
					});
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});
		
		((Button) findViewById(R.id.readRssiButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					miband.readRssi(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							changeStatus("rssi:"+(int)data);
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							changeStatus("readRssi fail");
						}
					});
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});
		
		((Button) findViewById(R.id.batteryInfoButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					miband.getBatteryInfo(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							BatteryInfo info = (BatteryInfo)data;
							Log.d(TAG, info.toString());
							changeStatus(info.toString());
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							changeStatus("readRssi fail");
						}
					});
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});

	}
	
	private void changeStatus(String status)
	{
		Bundle bundle = new Bundle();
		bundle.putString(DATA_KEY_MSG, status);
		Message msg = new Message();
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
}
