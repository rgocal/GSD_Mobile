package mobile.gsd.com.gsd.Widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ry on 11/16/16.
 */

public class IntrusiveTextView extends TextView {
    public IntrusiveTextView(Context context) {
        super(context);
    }
    public IntrusiveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public IntrusiveTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}