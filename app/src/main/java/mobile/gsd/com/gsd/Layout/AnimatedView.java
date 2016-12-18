package mobile.gsd.com.gsd.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import mobile.gsd.com.gsd.R;

/**
 * Created by ry on 9/26/15.
 */
public class AnimatedView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayoutObserver();

    }

    public AnimatedView(Context context) {
        super(context);
        initLayoutObserver();
    }

    private void initLayoutObserver() {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeGlobalOnLayoutListener(this);

        final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

        boolean inversed = false;
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int[] location = new int[2];

            child.getLocationOnScreen(location);

            if (location[1] > heightPx) {
                break;
            }

            if (!inversed) {
                child.startAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.abc_slide_in_bottom));
            } else {
                child.startAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.abc_slide_in_bottom));
            }

            inversed = !inversed;
        }

    }

}
