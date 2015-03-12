package views;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.androidproject.activities.Galaxy;

public class CustomLayout extends RelativeLayout {

	private final int INVALID_POINTER_ID = -1;

	private float mPosX;
	private float mPosY;

	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;
	private Map<Integer, Integer> childByLevels;
	private Map<Integer, Integer> maxHeightByLevels;
	private Map<Integer, Integer> maxWidthByLevels;
	private static final int WIDTH_MARGIN = 30;
	private static final int HEIGHT_MARGIN = 30;
	private Point screen = new Point();
	private Paint paintLine = new Paint();
	private View selectedObject = null;
	private ScaleGestureDetector sDectector;
	private float scaleFactor=1.f;

	
	public CustomLayout(Context context) {
		super(context);
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getSize(screen);
		childByLevels = new HashMap<Integer, Integer>();
		maxHeightByLevels = new HashMap<Integer, Integer>();
		maxWidthByLevels = new HashMap<Integer, Integer>();
		paintLine.setColor(Color.BLUE);
		sDectector = new ScaleGestureDetector(getContext(), new ScaleListener());
		

		
	}


	public void setSelectedObject(View selected) {
		if (selectedObject != null)
			((ObjectView) selectedObject).unselect();
		this.selectedObject = selected;
		Galaxy a = (Galaxy) getContext();
		if (selectedObject != null) {
			a.objectUnselected();
			a.objectSelected();
		} else {
			a.objectUnselected();
		}
	}

	public View getSelectedObject() {
		return selectedObject;
	}

	public void removeLevel(int level) {
		int i = 0;
		for (i = 0; i < getChildCount(); i++) {
			ObjectView v = (ObjectView) getChildAt(i);
			if (v.getLevel() == level)
				break;
		}
		removeViews(i, getChildCount() - i);
		requestLayout();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//Inspection de l'evenement courant
		sDectector.onTouchEvent(ev);
		
		
		final int action = ev.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();

			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = ev.getPointerId(0);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = ev.findPointerIndex(mActivePointerId);
			final float x = ev.getX(pointerIndex);
			final float y = ev.getY(pointerIndex);

//			if (Math.abs(mPosX) > getMeasuredWidth() / 2) {
//				if (mPosX > 0)
//					mPosX -= 20;
//				else
//					mPosX += 20;
//				return true;
//			}
//			if (Math.abs(mPosY) > getMeasuredHeight() / 2) {
//				if (mPosY > 0)
//					mPosY -= 20;
//				else
//					mPosY += 20;
//				return true;
//			}
			final float dx = x - mLastTouchX;
			final float dy = y - mLastTouchY;

			mPosX += dx;
			mPosY += dy;

			for(int i=0;i<getChildCount();i++) {
				getChildAt(i).setTranslationX(mPosX);
				getChildAt(i).setTranslationY(mPosY);
			}
			invalidate();
			mLastTouchX = x;
			mLastTouchY = y;

			break;
		}

		case MotionEvent.ACTION_UP: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_POINTER_UP: {
			final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = ev.getPointerId(pointerIndex);
			if (pointerId == mActivePointerId) {
				// This was our active pointer going up. Choose a new
				// active pointer and adjust accordingly.
				final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				mLastTouchX = ev.getX(newPointerIndex);
				mLastTouchY = ev.getY(newPointerIndex);
				mActivePointerId = ev.getPointerId(newPointerIndex);
			}
			break;
		}
		}

		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		for (int i = 0; i < getChildCount(); i++) {
			ObjectView v = (ObjectView) getChildAt(i);
			ObjectView ancester = v.getAncester();
			if (ancester != null) {
				canvas.drawLine(v.getX() + v.getMeasuredWidth() / 2, v.getY(),
						ancester.getX() + ancester.getMeasuredWidth() / 2,
						ancester.getY() + ancester.getMeasuredHeight(),
						paintLine);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int maxWidth = 0;
		int maxHeight = 0;
		childByLevels.clear();
		maxWidthByLevels.clear();
		maxHeightByLevels.clear();
		for (int i = 0; i < getChildCount(); i++) {
			final ObjectView v = (ObjectView) getChildAt(i);
				measureChild(v, widthMeasureSpec, heightMeasureSpec);

				if(childByLevels.get(v.getLevel())!=null)
					childByLevels.put(v.getLevel(), childByLevels.get(v.getLevel())+1);
				else 
					childByLevels.put(v.getLevel(), 1);
				
				if(maxWidthByLevels.get(v.getLevel())!=null) {
					if(maxWidthByLevels.get(v.getLevel()) < v.getMeasuredWidth()) {
						maxWidthByLevels.put(v.getLevel(), v.getMeasuredWidth());
					}
				}
				else 
					maxWidthByLevels.put(v.getLevel(), v.getMeasuredWidth());
				
				if(maxHeightByLevels.get(v.getLevel())!=null) {
					if(maxHeightByLevels.get(v.getLevel()) < v.getMeasuredHeight()) {
						maxHeightByLevels.put(v.getLevel(), v.getMeasuredHeight());
					}
				}
				else 
					maxHeightByLevels.put(v.getLevel(), v.getMeasuredHeight());
				
		
		}
		
		for(int i : maxHeightByLevels.values())
			maxHeight += i;
		maxHeight = maxHeight +(HEIGHT_MARGIN*maxHeightByLevels.size());
		
		
		for(int j=0;j<maxWidthByLevels.size();j++) {
			if(maxWidth< (maxWidthByLevels.get(j)*childByLevels.get(j)) + childByLevels.get(j)*WIDTH_MARGIN ) {
				maxWidth = (maxWidthByLevels.get(j)*childByLevels.get(j)) + childByLevels.get(j)*WIDTH_MARGIN;
			}
		}
				
		
		if (maxWidth > screen.x)
			setMeasuredDimension(maxWidth, screen.y);

		else if (maxHeight > screen.y)
			setMeasuredDimension(screen.x, maxHeight);

		else if (maxWidth > screen.x && maxHeight > screen.y)
			setMeasuredDimension(maxWidth, maxHeight);

		else
			setMeasuredDimension(screen.x, screen.y);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int currentLevel = 0;
		int renderedLevelChild = -1;
		for (int i = 0; i < getChildCount(); i++) {
			ObjectView child = (ObjectView) getChildAt(i);
			int numberOfChildInLevel = childByLevels.get(child.getLevel());

			if (child.getLevel() == currentLevel)
				renderedLevelChild++;
			else {
				currentLevel = child.getLevel();
				renderedLevelChild = 0;
			}

			int leftMargin =(int)( scaleFactor*(getMeasuredWidth() / (numberOfChildInLevel + 1) - WIDTH_MARGIN
					+ (maxWidthByLevels.get(child.getLevel())+ WIDTH_MARGIN) * renderedLevelChild)) ;
			
			int top = 40;
			if (child.getLevel() != 0) {
				int currentlevel = child.getLevel();
				while (currentlevel > 0) {
					top += maxHeightByLevels.get(currentlevel - 1);
					currentlevel--;
				}
				top += 50 * child.getLevel();

			}
			top=(int)(top*scaleFactor);
			child.layout(leftMargin, 
					top,
					leftMargin + child.getMeasuredWidth(),
					top + child.getMeasuredHeight());
			child.setScaleX(scaleFactor);
			child.setScaleY(scaleFactor);
			child.setTranslationX(mPosX);
			child.setTranslationY(mPosY);
		}
		
	}
	

	
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			 scaleFactor *= detector.getScaleFactor();

		     // Don't let the object get too small or too large.
			 scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
			 requestLayout();
	        return true;

		}
	}
}
