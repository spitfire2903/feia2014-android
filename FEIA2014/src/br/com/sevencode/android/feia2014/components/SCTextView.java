package br.com.sevencode.android.feia2014.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SCTextView extends TextView {

	public SCTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.baseInit();
	}

	public SCTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.baseInit();
	}

	public SCTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.baseInit();
	}
	
	public void baseInit(){
		Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/GeosansLight.ttf");
		setTypeface(font);
	}

}
