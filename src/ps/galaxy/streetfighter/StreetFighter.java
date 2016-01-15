package ps.galaxy.streetfighter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StreetFighter extends Activity {
	static final int GAME_TIME = 30000;
	ProgressBar firstFighterLife;
	ProgressBar secondFighterLife;
	int prog1  , prog2 ;
	int gameTimer = 30;
	TextView timer;
	AsyncTimer time ;
	MediaPlayer click;
	RelativeLayout layout; 
	public static final String PREFS_NAME = "StreetFighter";
	boolean gamefinished = false;
	ImageAdapterPower adapter1;
	ImageAdapterPower adapter2;
	ImageView sound;
	GridView grid1;
	GridView grid2;
	Integer [] power1;
	Integer [] power2;
	boolean mute;
	int arenaIndex;
	int firstFighterId;
	int secondFighterId;
	AudioManager manager;
	ArrayList<Fighter> fighters;
	Power [] powers;
	Integer [] arenas;
	Integer [] fighterImg ;
	ImageView fighterImg1;
	ImageView fighterImg2;
	TextView fighterName1;
	TextView fighterName2;
	ImageView one ;
	private Handler firstHandler = new Handler();
	int usedPowerF1 ; 
	int usedPowerF2 ;
	ImageView pause ;
	ImageView exit;
	boolean [] usedPower1 = {false,false,false,false,false};
	boolean [] usedPower2 = {false,false,false,false,false};
	String winnerName;
	TextView winner ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.street_fighter);
		
		initialize();
		
		pause = (ImageView)this.findViewById(R.id.pause);
		exit = (ImageView)this.findViewById(R.id.exit);
		winner = (TextView)this.findViewById(R.id.winnerName);
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time.cancel(true);
				ExitDialog dialg = new ExitDialog();
				dialg.show(StreetFighter.this.getFragmentManager(), "hiii");
			}
		});
		/*pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//PauseDialog dialg = new PauseDialog();
				//dialg.show(StreetFighter.this.getFragmentManager(), "hiii");
			}
		});
		*/
		sound.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mute)
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
	
	@Override
	public void onPause(){
		super.onPause();
		time.cancel(true);
	}
	@Override
	public void onResume(){
		super.onResume();
		
	}
	
	@Override
	public void onStart(){
		super.onStart();
		startGame();
	}
	

	public void startGame(){
		
		
		adapter1.setThumbIds(power1);
		adapter1.isSelected(usedPower1);
		grid1.setAdapter(adapter1);
	
		adapter2.setThumbIds(power2);
		adapter2.isSelected(usedPower2);
		grid2.setAdapter(adapter2);
		
		firstFighterLife.setProgress(firstFighterLife.getMax());
		secondFighterLife.setProgress(secondFighterLife.getMax());
		prog1 = 50;
		prog2 = 50;
		//showRound1();
		showThree();
		showTwo();
		showOne();
		hide();
		
		
    	for(int i  = 0  ; i < powers.length ; i++)
		{
    		if ( gamefinished )
    			break;
			if(fighters.get(firstFighterId).getPowers()[i].getRange() >= fighters.get(secondFighterId).getPowers()[i].getRange() )
			{
				prog1 -= fighters.get(firstFighterId).getPowers()[i].getRange();
				fighterToast(fighters.get(secondFighterId).getName()+" hit "+fighters.get(firstFighterId).getName(), (i+1)*6000,prog1 , prog2 ,2);


			}
			else {
				prog2 -= fighters.get(secondFighterId).getPowers()[i].getRange();
				fighterToast(fighters.get(firstFighterId).getName()+" hit "+fighters.get(secondFighterId).getName(),(i+1)*6000, prog1 , prog2 , 1);

			}
		}
	}

	class AsyncTimer extends AsyncTask <Void , Void , Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			for (;;) {
				publishProgress();
				if ( gameTimer == 0 ) {
					gamefinished = true;
					time.cancel(true);
					
						
				}
				else 
					gameTimer--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}

		protected void onProgressUpdate(Void... arg0){
			timer.setText(""+gameTimer);
			if (gameTimer == 0 )
			{
				if(prog1>prog2)
				{
					winnerName = fighters.get(firstFighterId).getName();
				}
				else{
					winnerName = fighters.get(secondFighterId).getName();
				}
				winner.setText("The Winner is : "+winnerName);
				winner.setBackgroundResource(android.R.color.holo_red_dark);
				cancel(true);

			}
		}

		protected void onPostExecute( Void arg0){
			timer.setText(""+gameTimer);
			
		}
	}
	

	
	public void initialize(){
		gameTimer = 30;
		fighters = new ArrayList<Fighter>();
		arenas = new Integer[]{
		 		R.drawable.arena2,R.drawable.arena5,R.drawable.arena3,
		 		R.drawable.arena4, R.drawable.arena7
		 };
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Bison ", powers ,R.drawable.bison2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Blanka ",powers, R.drawable.blanka ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Cammy ",powers, R.drawable.cammy2));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Chun li ", powers,R.drawable.chun_li2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Guile ", powers,R.drawable.guile2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Ryu ",powers, R.drawable.ryu2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Vega ",powers, R.drawable.vega2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Sagat ",powers , R.drawable.sagat2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Ken ", powers, R.drawable.kan2 ));
		powers = new Power[5];
		for(int i =0 ; i < powers.length ; i++){
			powers[i] = new Power((int)(Math.random()*15)+1);
		}
		fighters.add(new Fighter("Juri ", powers, R.drawable.juri2));
		
		power1 = new Integer [] {R.drawable.power21 , R.drawable.power22 ,R.drawable.power23,R.drawable.power24,R.drawable.power25};
		power2 = new Integer [] {R.drawable.power23 , R.drawable.power25 ,R.drawable.power21,R.drawable.power24,R.drawable.power22};

		timer = (TextView)this.findViewById(R.id.timer);
		grid1 = (GridView)this.findViewById(R.id.gridf1);
		grid2 = (GridView)this.findViewById(R.id.gridf2);
		
		adapter1 = new ImageAdapterPower(this);
		adapter2 = new ImageAdapterPower(this);
		
		adapter1.setThumbIds(power1);
		adapter1.isSelected(usedPower1);
		grid1.setAdapter(adapter1);
	
		adapter2.setThumbIds(power2);
		adapter2.isSelected(usedPower2);
		grid2.setAdapter(adapter2);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mute = settings.getBoolean("mute", false);
		arenaIndex = settings.getInt("arenaId",2);
		firstFighterId = settings.getInt("firstFighterId",2);
		secondFighterId = settings.getInt("secondFighterId",6);
		manager = (AudioManager)getSystemService(AUDIO_SERVICE);
		sound = (ImageView)this.findViewById(R.id.sound);
		click = MediaPlayer.create(this, R.raw.click);
		
		layout = (RelativeLayout)this.findViewById(R.id.streetfighter);
		layout.setBackgroundResource(arenas[arenaIndex]);
		fighterImg1 = (ImageView)this.findViewById(R.id.fighter1);
		fighterImg2 = (ImageView)this.findViewById(R.id.fighter2);
		fighterName1 = (TextView)this.findViewById(R.id.fighter1_name);
		fighterName2 = (TextView)this.findViewById(R.id.fighter2_name);
		
		fighterImg1.setImageResource(fighters.get(firstFighterId).getImgURL());
		fighterImg2.setImageResource(fighters.get(secondFighterId).getImgURL());
		fighterName1.setText(fighters.get(firstFighterId).getName());
		fighterName2.setText(fighters.get(secondFighterId).getName());
		
		if(mute){
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
			 sound.setImageResource(R.drawable.mute);
		}
		else
		{
			 manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
			 sound.setImageResource(R.drawable.sound);
		}
		one = (ImageView)this.findViewById(R.id.one);
		firstFighterLife= (ProgressBar)this.findViewById(R.id.fighter1_progressBar);
		secondFighterLife= (ProgressBar)this.findViewById(R.id.fighter2_progressBar);

		}

		
	
	public void fighterToast(final String txt , int delay , final int prog1 , final int prog2 , final int fighter){
		// TODO Auto-generated method stub
		
		
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						new Thread(new Runnable() {
				            public void run() {
				               
				            	   if(prog1 > 0 && prog2 > 0)
				                   firstHandler.post(new Runnable() {
				                        public void run() {
				                           firstFighterLife.setProgress(prog1);
				                          secondFighterLife.setProgress(prog2);
				                        }
				                    });
				            	   else
				            	   {
				            		   if(prog1 < 0)
				            			   firstFighterLife.setProgress(0);
				            		   if(prog2 < 0)
				            			   secondFighterLife.setProgress(0);
				            		   gamefinished = true ;
				        
				            	   }
				               
				            }
				        }).start();
						if (fighter == 1){
							usedPower1[usedPowerF1] = true;
							usedPowerF1++;
							adapter1.setThumbIds(power1);
							adapter1.isSelected(usedPower1);
							grid1.setAdapter(adapter1);
						}
						if (fighter == 2){
							usedPower2[usedPowerF2] = true;
							usedPowerF2++;
							adapter2.setThumbIds(power2);
							adapter2.isSelected(usedPower2);
							grid2.setAdapter(adapter2);
						}
						Toast.makeText(StreetFighter.this, txt, 4000).show();
					}
				});
			}
			
		}, delay);
	

	}
	
	public void showOne(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setImageResource(R.drawable.one);
					}
				});
			}
			
		}, 4000);
	}
	public void showTwo(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setImageResource(R.drawable.two);
					}
				});
			}
			
		}, 3000);
	}
	public void showThree(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setImageResource(R.drawable.three);
					}
				});
			}
			
		}, 2000);
	}
	public void showRound1(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setImageResource(R.drawable.round1);
					}
				});
			}
			
		}, 1000);
	}
	public void showRound2(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setImageResource(R.drawable.round2);
					}
				});
			}
			
		}, 1000);
	}
	public void hide(){
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				StreetFighter.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						one.setVisibility(View.GONE);
						time = new AsyncTimer();
						time.execute();
					}
				});
			}
			
		}, 5000);
	}

	
	
	public class PauseDialog extends DialogFragment{
		
		public Dialog onCreateDialog(Bundle bundle){
			String [] items = {"Resume" , "New Game" , "Main Menu"};
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Street Fighter Game");

			builder.setItems(items, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   Intent intent ; 
		               switch(which){
		               case 0 : // resume game 
		            	   break;
		               case 1 : 
		            		intent = new Intent(PauseDialog.this.getActivity(),SelectFighters.class);
		            		PauseDialog.this.startActivity(intent);
		            	   break;
		               case 2:
		            	   intent = new Intent(PauseDialog.this.getActivity(),StartActivity.class);
		            	   PauseDialog.this.startActivity(intent);
		            	   break;
		               }
	           }
			});
			return builder.create();
		}
	}
	
	public class GameOverDialog extends DialogFragment{
		
		public Dialog onCreateDialog(Bundle bundle){
			String [] items = { "New Game" , "Main Menu"};
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Street Fighter Game");
			builder.setMessage("The Winner is");
			builder.setItems(items, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   Intent intent ; 
		               switch(which){
		               case 0 : // resume game 
		            	   break;
		               case 1 : 
		            		intent = new Intent(GameOverDialog.this.getActivity(),SelectFighters.class);
		            		GameOverDialog.this.startActivity(intent);
		            	   break;
		               case 2:
		            	   intent = new Intent(GameOverDialog.this.getActivity(),StartActivity.class);
		            	   GameOverDialog.this.startActivity(intent);
		            	   break;
		               }
	           }
			});
			return builder.create();
		}
	}
	
	public class ExitDialog extends DialogFragment{
		
		public Dialog onCreateDialog(Bundle bundle){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
	
			builder.setMessage("Are you sure you want to exit ?");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ExitDialog.this.getActivity(),StartActivity.class);
					ExitDialog.this.startActivity(intent);
					ExitDialog.this.getActivity().finish();
					System.exit(0);
					
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dismiss();
					
				}
			});
			return builder.create();
		}

	}
	
	@Override
	public void onBackPressed(){
		time.cancel(true);

		ExitDialog dialg = new ExitDialog();
		dialg.show(StreetFighter.this.getFragmentManager(), "hiii");

	}

}


