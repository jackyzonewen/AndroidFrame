package com.tiny.framework.ui.bottomlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by tiny on 2015/4/1.
 */
public abstract class BaseBottomLayout extends FrameLayout implements IBottomLayout {

    State mState;

    public enum State {
        Loading, Loaded, Over

    }


    protected BaseBottomLayout(Context context) {
        this(context, null);
    }

    protected BaseBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mState = State.Loaded;
    }


    public void setState(State state) {
        mState = state;
    }

    public void refreshBottomView() {
        switch (mState) {
            case Loading:
                showLoadingBottomView();
                break;
            case Loaded:
                showLoadedBottomView();
                break;
            case Over:
                showOverBottomView();
                break;
        }
    }

}
