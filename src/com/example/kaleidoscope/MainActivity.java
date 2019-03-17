package com.example.kaleidoscope;


import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint("ClickableViewAccessibility")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements SensorEventListener{
	
	static float Sbar=20;
	static boolean Cb = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 SeekBar Sbar1 = (SeekBar) findViewById(R.id.seekBar1);
		 Sbar1.setProgress(20);
		 
			Sbar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					Sbar=progress;
					
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
			});
			
		 Button Button1 = (Button) findViewById(R.id.button1);
		 
		 	Button1.setOnTouchListener(new OnTouchListener (){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
				    CanvasView CV = (CanvasView) findViewById(R.id.signature_canvas);
					CV.clearCanvas();
					return false;
				}

		 	});
		 	
		final CheckBox CBox1 = (CheckBox) findViewById(R.id.checkBox1);
		
			CBox1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					Cb=CBox1.isChecked();
					
				}
			});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	static int GetMirNum(){
		return (int) ((((int)(Sbar/6))+1)*8);
	}
	
	static int GetMir(){
		if (Cb) return -1;
		else return +1;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	
}
