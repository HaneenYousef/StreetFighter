package ps.galaxy.streetfighter;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class AboutMe extends Activity {
	
	ImageView facebook;
	ImageView tweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_me);
		facebook = (ImageView)this.findViewById(R.id.facebook);
		tweet = (ImageView)this.findViewById(R.id.tweet);
		
		facebook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/haneen.yousef77"));
				startActivity(browserIntent);
			}
		});
		
		tweet.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Haneen_Yousef"));
					startActivity(browserIntent);
				}
		});
	}


}
