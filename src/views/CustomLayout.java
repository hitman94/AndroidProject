package views;

import java.util.HashMap;
import java.util.Map;

import com.example.projetandroid2015.activities.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CustomLayout extends RelativeLayout {

	private final int INVALID_POINTER_ID = -1;

	private float mPosX;
	private float mPosY;

	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;
	private Map<Integer,Integer> childByLevels;
	private Map<Integer,Integer> maxHeightByLevels;
	private Point screen = new Point();
	private Paint paintLine = new Paint();
	private View selectedObject=null;
	public CustomLayout(Context context) {
		super(context);
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getSize(screen);
		childByLevels = new HashMap<Integer, Integer>();
		maxHeightByLevels= new HashMap<Integer, Integer>();
		paintLine.setColor(Color.BLUE);
	}

	public void setSelectedObject(View selected) {
		if(selectedObject!=null)
			((ObjectView)selectedObject).unselect();
		this.selectedObject = selected;
		MainActivity a = (MainActivity)getContext();
		if(selectedObject != null) {
			a.objectUnselected();
			a.objectSelected();
		}else {
			a.objectUnselected();
		}
	}
	
	public View getSelectedObject() {
		return selectedObject;
	}
	
	public void removeLevel(int level) {
		int i=0;
		for(i=0;i<getChildCount();i++) {
			ObjectView v = (ObjectView) getChildAt(i);
			if(v.getLevel()==level) 
				break;
		}
		removeViews(i, getChildCount()-i);
		requestLayout();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Let the ScaleGestureDetector inspect all events.
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

			if(Math.abs(mPosX)>getMeasuredWidth()/2) {
				if(mPosX>0)
					mPosX-=20;
				else
					mPosX+=20;
				return true;
			}
			if(Math.abs(mPosY)>getMeasuredHeight()/2) {
				if(mPosY>0)
					mPosY-=20;
				else
					mPosY+=20;
				return true;
			}
			final float dx = x - mLastTouchX;
			final float dy = y - mLastTouchY;

			mPosX += dx;
			mPosY += dy;

			requestLayout();
			

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
		for(int i=0;i<getChildCount();i++) {
			ObjectView v =(ObjectView) getChildAt(i);
			ObjectView ancester = v.getAncester();
			if(ancester!=null) {
				canvas.drawLine(v.getX()+v.getMeasuredWidth()/2, v.getY(), ancester.getX()+ancester.getMeasuredWidth()/2, ancester.getY()+ancester.getMeasuredHeight(), paintLine);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int maxWidth = 0;
		int maxHeight = 0;

		int currentLongestWidth = 0;
		int currentLongestHeight = 0;
		int currentLevel = 0;
		int childNumberLevel=0;
		for (int i = 0; i < getChildCount(); i++) {
			final ObjectView v = (ObjectView) getChildAt(i);
			if (v.getVisibility() != GONE) {
				measureChild(v, widthMeasureSpec, heightMeasureSpec);

				if (v.getLevel() == currentLevel) { // Si on est sur le meme
					childNumberLevel++;
					childByLevels.put(v.getLevel(), childNumberLevel);
					currentLongestWidth += v.getMeasuredWidth() + 10; 
					if (v.getMeasuredHeight() > currentLongestHeight)
						currentLongestHeight = v.getMeasuredHeight();
				}
				if (v.getLevel() != currentLevel) {// on change de niveau dans l'arbre
					maxHeightByLevels.put(currentLevel,currentLongestHeight);
					childNumberLevel=1;
					childByLevels.put(v.getLevel(), childNumberLevel);
					currentLevel = v.getLevel();
					if (currentLongestWidth > maxWidth) // Si la largeur du
														// niveau precedent est
														// plus grande que les
														// autres niveaux
						maxWidth = currentLongestWidth;
					currentLongestWidth += v.getMeasuredWidth() + 10;
					
					maxHeight += currentLongestHeight + 20;
					currentLongestHeight = v.getMeasuredHeight();
				}
			}
		}
		
		final ObjectView lastchild = (ObjectView) getChildAt(getChildCount() - 1);
		if (lastchild.getLevel() == currentLevel) {

			if (currentLongestWidth > maxWidth) // Si la largeur du dernier
												// niveau est plus grande que
												// les autres niveaux
				maxWidth = currentLongestWidth;
			maxHeightByLevels.put(currentLevel,currentLongestHeight);
			maxHeight += currentLongestHeight + 20;
		}
		
		
		if(maxWidth > screen .x)
			setMeasuredDimension(maxWidth , screen.y);
		
		else if(maxHeight>screen.y) 
			setMeasuredDimension(screen.x, maxHeight);
			
		else if(maxWidth > screen .x && maxHeight>screen.y) 
			setMeasuredDimension(maxWidth, maxHeight);
		
		else 
			setMeasuredDimension(screen.x, screen.y);
			
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int currentLevel=0;
		int renderedLevelChild = -1;
		for (int i = 0; i < getChildCount(); i++) {
			ObjectView child = (ObjectView)getChildAt(i);
			int numberOfChildInLevel = childByLevels.get(child.getLevel());
			
			if(child.getLevel() ==currentLevel)
				renderedLevelChild++;
			else {
				currentLevel=child.getLevel();
				renderedLevelChild=0;
			}
				
			int leftMargin = getMeasuredWidth()/(numberOfChildInLevel+1) + 150*renderedLevelChild;
			int top=40;
			if(child.getLevel() !=0) {
				int currentlevel = child.getLevel();
				while(currentlevel>0) {
					top+=maxHeightByLevels.get(currentlevel-1);
					currentlevel--;
				}
				top+=50*child.getLevel();
				
			}
			
			
			child.layout(Math.round(mPosX)+leftMargin,Math.round(mPosY)+ top, Math.round(mPosX)+leftMargin+child.getMeasuredWidth(),Math.round(mPosY)+ top+child.getMeasuredHeight());
		}
	}

}
