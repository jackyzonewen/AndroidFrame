package com.tiny.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class SimpleInnerListView extends ListView {
	public SimpleInnerListView(Context context) {
		super(context);
	}
	public SimpleInnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
