package com.zhaoxiaodan.mibandtest;

import java.util.Arrays;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.zhaoxiaodan.miband.ActionCallback;
import com.zhaoxiaodan.miband.BatteryInfo;
import com.zhaoxiaodan.miband.MiBand;
import com.zhaoxiaodan.miband.NotifyListener;
import com.zhaoxiaodan.miband.RealtimeStepsNotifyListener;

public class MainActivity extends ListActivity
{
	private static final String	TAG				= "==[mibandtest]==";
	private static final String	DATA_KEY_MSG	= "DATA_KEY_MSG";
	private MiBand				miband;
	
	static final String[]		BUTTONS			= new String[] {
												"Connect",
												"pair",
												"read_rssi",
												"battery_info",
												"startVibration",
												"stopVibration",
												"setNormalNotifyListener",
												"setRealtimeStepsNotifyListener",
												"enableRealtimeStepsNotify",
												"setColorBlue",
												"setUserInfo",
												};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		miband = new MiBand(this);
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.item, BUTTONS));
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				int menuIndex = 0;
				if (position == menuIndex++)
				{
					miband.connect(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG, "connect success");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "connect fail, code:" + errorCode + ",mgs:" + msg);
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.pair(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG, "pair succ");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "pair fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.readRssi(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG, "rssi:" + (int) data);
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "readRssi fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.getBatteryInfo(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							BatteryInfo info = (BatteryInfo) data;
							Log.d(TAG, info.toString());
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "getBatteryInfo fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.startVibration();
				}
				else if (position == menuIndex++)
				{
					miband.stopVibration();
				}
				else if (position == menuIndex++)
				{
					miband.setNormalNotifyListener(new NotifyListener() {
						
						@Override
						public void onNotify(byte[] data)
						{
							Log.d(TAG, "NormalNotifyListener:" + Arrays.toString(data));
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.setRealtimeStepsNotifyListener(new RealtimeStepsNotifyListener() {
						
						@Override
						public void onNotify(int steps)
						{
							Log.d(TAG, "RealtimeStepsNotifyListener:" + steps);
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.enableRealtimeStepsNotify();
				}
				else if (position == menuIndex++)
				{
					miband.setColorBlue();
				}
				else if (position == menuIndex++)
				{
					miband.setUserInfo(new byte[]{25, 113, 53, 1, 1, 32, -83, 55, 1, -24, -125, -106, -26, -94, -127, 0, 0, 0, 0, 35});
				}
			}
		});
		
	}
}
