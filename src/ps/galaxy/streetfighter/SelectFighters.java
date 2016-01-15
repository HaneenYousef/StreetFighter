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

public class SelectFighters extends Activity implements OnItemClickListener {
	Integer[] imgs;
	GridView grid;
	ImageAdapter adapter ;
	boolean [] isSelected = {false,false,false,false,false,false,false,false,false,false};
	int firstFighter; 
	int secondFighter;
	boolean positionSelected=false; 
	ImageView firstFighterImage ;
	ImageView secondFighterImage ;
	ImageView next ;
	ImageView sound ;
	ImageView exit ;
	MediaPlayer click ; 
	boolean mute ;
	public static final String PREFS_NAME = "StreetFighter";
	AudioManager manager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_fighters);
		grid = (GridView)this.findViewById(R.id.gridView1);
		imgs = new Integer[]{
	 		R.drawable.bison,R.drawable.blanka,R.drawable.cammy,
	 		R.drawable.chun_li, R.drawable.guile ,R.drawable.ryu,
	 		R.drawable.vega,R.drawable.sagat,R.drawable.ken,
	 		R.drawable.juri
	 	};
		
		adapter = new ImageAdapter(this);
		adapter.setThumbIds(imgs);
		adapter.isSelected(isSelected);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(this);
		firstFighterImage = (ImageView)this.findViewById(R.id.imageView3);
		secondFighterImage = (ImageView)this.findViewById(R.id.imageView4);
		firstFighterImage.setImageResource(imgs[firstFighter]);
		secondFighterImage.setImageResource(imgs[secondFighter]);
		next = (ImageView)this.findViewById(R.id.imageView2);
		exit = (ImageView)this.findViewById(R.id.exit);
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(SelectFighters.this, SelectArena.class);
				SelectFighters.this.startActivity(intent);
			}
		});
		
		click = MediaPlayer.create(this, R.raw.click);
		sound = (ImageView)this.findViewById(R.id.sound);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mute = settings.getBoolean("mute", false);
		firstFighter = settings.getInt("firstFighterId",2);
		secondFighter = settings.getInt("secondFighterId",6);
		
		firstFighterImage.setImageResource(imgs[firstFighter]);
		secondFighterImage.setImageResource(imgs[secondFighter]);
		isSelected[firstFighter]=true;
		isSelected[secondFighter]=true;
		manager = (AudioManager)getSystemService(AUDIO_SERVICE);
		if(mute){
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
			 sound.setImageResource(R.drawable.mute);
		}
		else{
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
	
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click.start();
			
				
				Intent intent = new Intent(SelectFighters.this,StartActivity.class);
				SelectFighters.this.startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		if( position != firstFighter && position != secondFighter )
		{
			click.start();
			if(! positionSelected)
			{
				isSelected[firstFighter]=false;
				isSelected[position]=true;
				firstFighter=position;
				positionSelected=true;
				firstFighterImage.setImageResource(imgs[firstFighter]);
				adapter.setThumbIds(imgs);
				adapter.isSelected(isSelected);
				grid.setAdapter(adapter);
				
			}
			else {
				isSelected[secondFighter]=false;
				isSelected[position]=true;
				secondFighter=position;
				positionSelected=false;
				secondFighterImage.setImageResource(imgs[secondFighter]);
				adapter.setThumbIds(imgs);
				adapter.isSelected(isSelected);
				grid.setAdapter(adapter);

			}
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("firstFighterId", firstFighter);
		editor.putInt("secondFighterId", secondFighter);
		editor.putBoolean("mute", mute);
		editor.commit();
	}
	
		
}
	


