package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

public class SurfaceObjectView extends SurfaceView {
	
	private SurfaceHolder surface;

	public SurfaceObjectView(Context context) {
		super(context);
		RelativeLayout rl = (RelativeLayout)getParent();
		System.err.println(rl.getId());
		surface=getHolder();
		surface.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				
				
				Canvas canvas = holder.lockCanvas();
				Rect rectangle = new Rect(25,25,50,50);
				Paint paint = new Paint();
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.BLUE);
				canvas.drawRect(rectangle, paint);
				holder.unlockCanvasAndPost(canvas);
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}
