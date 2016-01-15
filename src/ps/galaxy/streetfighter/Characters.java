package ps.galaxy.streetfighter;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class Characters extends Activity implements OnItemClickListener {
	ImageAdapter adapter ;
	GridView grid;
	Integer [] imgs ;
	String [] names ;
	ArrayList<Fighter> fighters;
	Fighter selected; 
	boolean [] isSelected = {true,false,false,false,false,false,false,false,false,false};
	TextView name;
	TextView eye;
	TextView height;
	TextView nationality;
	TextView weight;
	ImageView fighterImg;
	int findex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_characters);
		grid = (GridView)this.findViewById(R.id.gridView1);
		
		
		fighters = new ArrayList<Fighter>();
		fighters.add(new Fighter("Bison ",182 , 82 , "White" , "UK" , "Psycho Power" , R.drawable.bison2 ));
		fighters.add(new Fighter("Blanka ",192 , 98 , "Green" , "Barzil" , "Self-taught savage fighting" , R.drawable.blanka ));
		fighters.add(new Fighter("Cammy ",164 , 46 , "Blue" , "UK" , "Delta Red Auto Defense " , R.drawable.cammy2));
		fighters.add(new Fighter("Chun li ",167 , 50 , "Black" , "China" , "Psycho Power" , R.drawable.chun_li2 ));
		fighters.add(new Fighter("Guile ",182 , 82 , "Black" , "USA" , "Psycho Power" , R.drawable.guile2 ));
		fighters.add(new Fighter("Ryu ",182 , 82 , "Blue" , "Japan" , "Psycho Power" , R.drawable.ryu2 ));
		fighters.add(new Fighter("Vega ",182 , 82 , "Black" , "spain" , "Psycho Power" , R.drawable.vega2 ));
		fighters.add(new Fighter("Sagat ",182 , 82 , "Blue" , "Thailand" , "Psycho Power" , R.drawable.sagat2 ));
		fighters.add(new Fighter("Ken ",182 , 82 , "Black" , "USA" , "Psycho Power" , R.drawable.kan2 ));
		fighters.add(new Fighter("Juri ",182 , 82 , "Black" , "Korea" , "Psycho Power" , R.drawable.juri2));
		
		
			imgs = new Integer[]{
	 		R.drawable.bison,R.drawable.blanka,R.drawable.cammy,
	 		R.drawable.chun_li, R.drawable.guile ,R.drawable.ryu,
	 		R.drawable.vega,R.drawable.sagat,R.drawable.ken,
	 		R.drawable.juri
	 	};
		names = new String [] { "Bison","Blanka","Cammy","Chun li", "Guile" ,"Ryu",
		 		"Vega","Sagat","Ken","Juri"};
		
	    fighterImg = (ImageView)this.findViewById(R.id.imageView1);
		name = (TextView)this.findViewById(R.id.name);
		eye = (TextView)this.findViewById(R.id.eye);
		nationality = (TextView)this.findViewById(R.id.country);
		height = (TextView)this.findViewById(R.id.height);
		weight = (TextView)this.findViewById(R.id.weight);
		
		selected = fighters.get(0);
		name.setText("Name: "+selected.getName());
		eye.setText("Eye Color: "+selected.getEyeColor());
		nationality.setText("Nationality: "+selected.getBirthPlace());
		height.setText("Height: "+selected.getHeight()+" cm");
		weight.setText("Weight: "+selected.getWeight()+" kg");
		fighterImg.setImageResource(selected.getImgURL());
		
		adapter = new ImageAdapter(this);
		adapter.setThumbIds(imgs);
		adapter.isSelected(isSelected);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_characters, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		if(position != findex){
			selected = fighters.get(position);
			name.setText("Name: "+selected.getName());
			eye.setText("Eye Color: "+selected.getEyeColor());
			nationality.setText("Nationality: "+selected.getBirthPlace());
			height.setText("Height: "+selected.getHeight()+" cm");
			weight.setText("Weight: "+selected.getWeight()+" kg");
			fighterImg.setImageResource(selected.getImgURL());
			isSelected[position]=true;
			isSelected[findex]=false;
			findex = position;
			adapter.isSelected(isSelected);
			grid.setAdapter(adapter);
		}
	}

}
