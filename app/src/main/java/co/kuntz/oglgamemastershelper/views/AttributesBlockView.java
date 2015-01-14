package co.kuntz.oglgamemastershelper.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AttributesBlockView extends FrameLayout {
    public static final int NUM_ATTRIBUTES = 6;

    public AttributesBlockView(Context context) {
        super(context);
    }

    public AttributesBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AttributesBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * TODO make this determine if they should appear in 3x2 or 6x1 based on the size
     *      of the screen.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);

        int majorDimension = Math.min(width, height);

        int blockDimension = majorDimension / (NUM_ATTRIBUTES / 2);
        int blockSpec = MeasureSpec.makeMeasureSpec(blockDimension, MeasureSpec.EXACTLY);

        // TODO: this makes them square. NOT WANTED!
        measureChildren(blockSpec, blockSpec);
    }
}
