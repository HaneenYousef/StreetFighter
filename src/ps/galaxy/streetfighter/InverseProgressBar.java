package ps.galaxy.streetfighter;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ProgressBar;

public class InverseProgressBar extends ProgressBar {
	
	public InverseProgressBar(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
	   // TODO Auto-generated method stub
	       canvas.save(); 
		   float py = this.getHeight()/2.0f;
		   float px = this.getWidth()/2.0f;
		   canvas.rotate(180, px, py); 
		   super.onDraw(canvas); 
		   canvas.restore(); 
	}

}