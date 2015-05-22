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
	private static final String	TAG	= "==[mibandtest]==";
	private static final String DATA_KEY_RSSI = "RSSI";
	private static final String DATA_KEY_DEVICES = "DEVICES";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Handler handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg)
			{
				((TextView) MainActivity.this.findViewById(R.id.rssiTextView)).setText(msg.getData().getInt(DATA_KEY_RSSI)+"");
				BluetoothDevice device = (BluetoothDevice) msg.getData().getParcelable(DATA_KEY_DEVICES);
				((TextView) MainActivity.this.findViewById(R.id.nameTextView)).setText(device.getName());
				((TextView) MainActivity.this.findViewById(R.id.addressTextView)).setText(device.getAddress());
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
				data.putInt(DATA_KEY_RSSI, rssi);
				data.putParcelable(DATA_KEY_DEVICES, device);
				Message msg = new Message();
				msg.setData(data);
				handler.sendMessage(msg);
				
			}
		};
		
		Button button = (Button) findViewById(R.id.startButton);
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
		
		Button button2 = (Button) findViewById(R.id.stopButon);
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
