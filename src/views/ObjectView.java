package views;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

import com.example.androidproject.activities.Galaxy;
import com.example.projectandroid2015.util.ContentProviderUtil;

public class ObjectView extends View {
	
	private Rect rectangle;
	private Paint paint;
	private int level;
	private String objectId;
	private Map<String,String> properties;
	private int yToDraw=15;
	private int longestString=0;
	private ObjectView ancester;
	private ContentProviderUtil utils;
	public ObjectView(Context context,int level, String id,ObjectView ancester) {
		
		super(context);
		this.utils = ((Galaxy)context).contentUtils;
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.BLACK);
		this.level=level;
		this.objectId=id;
		initProperties();
		initLongestString();
		rectangle = new Rect();	
		this.ancester=ancester;
		setLongClickable(true);
		setClickable(true);
		setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if( v.equals(((CustomLayout)getParent()).getSelectedObject())) {
					paint.setColor(Color.BLACK);
					invalidate();
					((CustomLayout)getParent()).setSelectedObject(null);
				}else {
					paint.setColor(Color.GREEN);
					invalidate();
					((CustomLayout)getParent()).setSelectedObject(v);
				}
				return true;
			}
		});
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ObjectView view = (ObjectView)v;
				((CustomLayout)getParent()).removeLevel(view.getLevel()+1);
				
				//TODO get child from BDD
				
				((CustomLayout)getParent()).addView(new ObjectView(getContext(), view.getLevel()+1, "1", view));
			}
		});
	}
	
	public void unselect() {
		paint.setColor(Color.BLACK);
		invalidate();
	}
	
	private void initProperties() {
		//TODO recupérer depuis la BDD
		properties=new HashMap<String, String>();
		properties.put("hello", "blabla");
		properties.put("test", "123");
	}
	
	
	
	private void initLongestString() {
		int l;
		for(Map.Entry<String,String> entry : properties.entrySet()) {
			if( (l=(entry.getKey().length()+entry.getValue().length()+1))>longestString) {
				longestString=l;
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = longestString * 7;
		int height = properties.size() * 20;
		setMeasuredDimension(width, height);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		rectangle.left=0;
		rectangle.top=0;
		rectangle.bottom=getMeasuredHeight();
		rectangle.right=getMeasuredWidth();
		canvas.drawRect(rectangle, paint);
		yToDraw=15;
		for(Map.Entry<String,String> entry : properties.entrySet()) {
			canvas.drawText(entry.getKey()+"="+entry.getValue(), 10, yToDraw, paint);
			yToDraw+=15;
		}		
	}
	

	
	public int getLevel() {
		return level;
	}
	
	public ObjectView getAncester() {
		return ancester;
	}
	
	public String getObjectId() {
		return objectId;
	}
	

}
