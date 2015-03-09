package views;

import java.util.ArrayList;
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
import com.example.androidproject.database.AndodabContentProvider;
import com.example.projectandroid2015.util.ContentProviderUtil;

public class ObjectView extends View {
	
	private Rect rectProperties;
	private Rect rectName;
	private Rect rectCanvas;
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
		this.utils = Galaxy.contentUtils;
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.BLACK);
		this.level=level;
		this.objectId=id;
		initProperties();
		initLongestString();
		rectProperties = new Rect();
		rectName = new Rect();
		rectCanvas = new Rect();
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
				ArrayList<String> list = utils.getChildren(objectId);
				if(list==null)
					return;
				for(String id : list)
					((CustomLayout)getParent()).addView(new ObjectView(getContext(), view.getLevel()+1, id, view));
			}
		});
	}
	
	public void unselect() {
		paint.setColor(Color.BLACK);
		invalidate();
	}
	
	private void initProperties() {
		//TODO recup�rer depuis la BDD
		properties=new HashMap<String, String>();
		if(!objectId.equals("root")) {
			HashMap<String, String> map = utils.getProperties(objectId);
			if (map != null) {
				for (Map.Entry<String, String> entry : map.entrySet())
					properties.put(entry.getKey(), entry.getValue());
			}
		}
		//properties.put("hello", "123");
	}
	
	
	
	private void initLongestString() {
		int l;
		for(Map.Entry<String,String> entry : properties.entrySet()) {
			if( (l=(entry.getKey().length()+entry.getValue().length()+1))>longestString) {
				longestString=l;
			}
		}
		if(objectId.length()>longestString)
			longestString=objectId.length();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = longestString * 8;
		int height = properties.size() * 20 + 20; //Propriétés + nom objet
		setMeasuredDimension(width, height);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
//		float scale = ((CustomLayout)getParent()).getScaleFactor();
//		canvas.scale(scale, scale);
		canvas.getClipBounds(rectCanvas);
		rectName.left=1;
		rectName.top=1;
		rectName.bottom=20;
		rectName.right=rectCanvas.right-1;
		rectProperties.left=1;
		rectProperties.top=20;
		rectProperties.bottom=rectCanvas.bottom-1;
		rectProperties.right=rectCanvas.right-1;
		canvas.drawRect(rectProperties, paint);
		canvas.drawRect(rectName, paint);
		canvas.drawText(objectId, 5, 15, paint);
		yToDraw=35;
		for(Map.Entry<String,String> entry : properties.entrySet()) {
			canvas.drawText(entry.getKey()+"="+entry.getValue(), 5, yToDraw, paint);
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
