package ps.galaxy.streetfighter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends Activity {
	ImageView characters ;
	ImageView game ;
	ImageView exit;
	ImageView aboutMe;
	ImageView sound ;
	MediaPlayer click ; 
	boolean mute=false;
	public static final String PREFS_NAME = "StreetFighter";
	AudioManager manager ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		characters = (ImageView)this.findViewById(R.id.character);
		game = (ImageView)this.findViewById(R.id.start);
		aboutMe = (ImageView)this.findViewById(R.id.about_me);
		exit = (ImageView)this.findViewById(R.id.exit);	
		click = MediaPlayer.create(this, R.raw.click);
		sound = (ImageView)this.findViewById(R.id.sound);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mute = settings.getBoolean("mute", false);
		manager = (AudioManager)getSystemService(AUDIO_SERVICE);
		if(mute){
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
			 sound.setImageResource(R.drawable.mute);
		}
		else{
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
			 sound.setImageResource(R.drawable.sound);
		}
		
		characters.setOnClickListener( new View.OnClickListener() {
			
		@Override
		public void onClick(View arg0) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(StartActivity.this, Characters.class);
				StartActivity.this.startActivity(intent);
			}
		});
		game.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(StartActivity.this, SelectFighters.class);
				StartActivity.this.startActivity(intent);
			}
		});
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				click.start();
				finish();
			}
		});
		aboutMe.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click.start();
				Intent intent = new Intent(StartActivity.this, AboutMe.class);
				StartActivity.this.startActivity(intent);
			}
		});
		
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
	}
	
	public void onPuase(){
		super.onPause();
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("mute", mute);
		 editor.commit();
	}
}
