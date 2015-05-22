package com.zhaoxiaodan.mibandtest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static final String	TAG	= "==[shouhuantest]==";
	private static final String RSSI_KEY = "RSSI";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TextView view = (TextView) MainActivity.this.findViewById(R.id.textView1);
		
		final Handler handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg)
			{
				view.setText(msg.getData().getInt(RSSI_KEY)+"");
				return true;
			}
		});
		
		final BluetoothAdapter.LeScanCallback	mLeScanCallback	= new BluetoothAdapter.LeScanCallback() {
			@Override
			public void onLeScan(final BluetoothDevice device, final int rssi,
					final byte[] scanRecord)
			{
				Log.i(TAG, "name:"+device.getName()
						+",add:"+device.getAddress()
						+",type:"+device.getType()
						+",bondState:"+device.getBondState()
						+",rssi:"+rssi);
				
				Bundle data = new Bundle();
				data.putInt(RSSI_KEY, rssi);
				Message msg = new Message();
				msg.setData(data);
				handler.sendMessage(msg);
				
			}
		};
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
					adapter.startLeScan(mLeScanCallback);
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try
				{
					BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
					adapter.stopLeScan(mLeScanCallback);
				} catch (Throwable e)
				{
					e.printStackTrace();
				}

			}
		});
		
		
	}
}
