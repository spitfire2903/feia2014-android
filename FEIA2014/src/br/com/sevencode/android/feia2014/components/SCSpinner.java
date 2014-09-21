package br.com.sevencode.android.feia2014.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SCSpinner extends Spinner {

	
	public SCSpinner(Context context) {
        super(context);
        
        this.baseInit();
    }

    public SCSpinner(Context context, int mode) {
        super(context, mode);
        
        this.baseInit();
    }

    public SCSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        this.baseInit();
    }

    public SCSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        this.baseInit();
    }

  
    public SCSpinner(Context context, AttributeSet attrs, int defStyle, int mode) {
        super(context, attrs, defStyle, mode);
        
        this.baseInit();
    }
	
	
	
	private void baseInit(){
		Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/GeosansLight.ttf");
		//setTypeface(font);
	}
}
