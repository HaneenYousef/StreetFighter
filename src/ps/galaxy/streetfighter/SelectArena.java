package ps.galaxy.streetfighter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class SelectArena extends Activity implements OnItemClickListener {
	Integer[] imgs;
	GridView grid;
	ImageAdapter adapter ;
	boolean [] isSelected = {false,false,false,false,false};
	int arenaIndex; 
	ImageView arena ;
	ImageView next ;
	ImageView sound ;
	ImageView exit ;
	MediaPlayer click ; 
	boolean mute=false;
	public static final String PREFS_NAME = "StreetFighter";
	AudioManager manager ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_arena);
		
		grid = (GridView)this.findViewById(R.id.gridView1);
		imgs = new Integer[]{
	 		R.drawable.arena2,R.drawable.arena5,R.drawable.arena3,
	 		R.drawable.arena4, R.drawable.arena7
	 	};
		arena = (ImageView)this.findViewById(R.id.imageView4);
		next = (ImageView)this.findViewById(R.id.imageView3);
		click = MediaPlayer.create(this, R.raw.click);
		sound = (ImageView)this.findViewById(R.id.sound);
		exit = (ImageView)this.findViewById(R.id.exit);
		adapter = new ImageAdapter(this);
		adapter.setThumbIds(imgs);
		adapter.isSelected(isSelected);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(this);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mute = settings.getBoolean("mute", false);
		arenaIndex = settings.getInt("arenaId",2);
		isSelected[arenaIndex]=true;
		arena.setImageResource(imgs[arenaIndex]);
		manager = (AudioManager)getSystemService(AUDIO_SERVICE);
		if(mute){
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
			 sound.setImageResource(R.drawable.mute);
		}
		else
		{
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
			 sound.setImageResource(R.drawable.sound);
		}
		
		sound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if( mute )
				{
					 click.start();
					 sound.setImageResource(R.drawable.sound);
					 manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
					 mute = false ; 
				}
				else {					 
					 sound.setImageResource(R.drawable.mute);
					 manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
					 mute = true ;
				}
				
				
			}
		});
		
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(SelectArena.this ,StreetFighter.class);
				SelectArena.this.startActivity(intent);

			}
		});
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(SelectArena.this,StartActivity.class);
				SelectArena.this.startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		if( position != arenaIndex)
		{
				isSelected[arenaIndex]=false;
				isSelected[position]=true;
				arenaIndex=position;
				arena.setImageResource(imgs[arenaIndex]);
				adapter.setThumbIds(imgs);
				adapter.isSelected(isSelected);
				grid.setAdapter(adapter);	
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("arenaId", arenaIndex);
		editor.putBoolean("mute", mute);
		editor.commit();
	}

}