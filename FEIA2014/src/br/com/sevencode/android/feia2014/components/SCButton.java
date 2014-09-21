package br.com.sevencode.android.feia2014.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class SCButton extends Button {
	public SCButton(Context context) {
        super(context);
        
        this.baseInit();
    }

    public SCButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        this.baseInit();
    }

    public SCButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        this.baseInit();
    }
    
	private void baseInit(){
		Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/GeosansLight.ttf");
		setTypeface(font);
	}
}
