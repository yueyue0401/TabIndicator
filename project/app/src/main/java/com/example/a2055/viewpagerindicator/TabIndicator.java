package com.example.a2055.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabIndicator extends LinearLayout {
    private final int DEFAULT_TAB_COUNT = 4;
    private int triangleWidth;
    private int indicatorOffset;
    private int initialIndicator;
    private int count;
    private int tabCount;

    private Context context;
    private Paint paint;
    private Path path;
    private TabtouchListener listener;

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);
        tabCount = a.getInt(R.styleable.TabIndicator_tab_count, DEFAULT_TAB_COUNT);

        this.context = context;
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.background));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();

        LinearGradient linearGradient;
        linearGradient = new LinearGradient(getX(), getHeight() - 10, getX(), getHeight(),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.shadowBottom), Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(getChildAt(0).getX(), getHeight() - 10, getChildAt(count-1).getRight(), getHeight(), paint);

        paint.setShader(null);

        canvas.translate(initialIndicator + indicatorOffset, getHeight());
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initTriangle(w);
    }

    private void initTriangle(int w) {
        triangleWidth = w / tabCount / 6;
        int triangleHeight = triangleWidth / 2;

        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(triangleWidth, 0);
        path.lineTo(triangleWidth / 2, -triangleHeight);
        path.close();

        initialIndicator = ((int) getChildAt(0).getX() - getChildAt(0).getWidth()) / 2 - triangleWidth / 2;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        count = getChildCount();
        if (count == 0) return;

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.weight = 0;
            params.width = getScreenWidth() / tabCount;
            view.setLayoutParams(params);

            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTabTouch(position);
                }
            });
        }


    }

    public int getScreenWidth() {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public void setTabTitle(String[] mDatas) {
        if (mDatas == null || mDatas.length == 0) return;

        removeAllViews();

        for (String title : mDatas) {
            addView(generateTextView(title));
        }
        onFinishInflate();
    }

    private TextView generateTextView(String title) {
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextColor(getResources().getColor(R.color.textDisable));
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void scroll(int position, int offset) {
        int outViewCount = count - tabCount < 0 ? 0 : count - tabCount;
        if (position < tabCount - 2) {
            scrollTo(-getWidth() / tabCount / 2 * outViewCount, 0);

        } else if (position >= count - 2) {
            scrollTo(-getWidth() / tabCount / 2 * outViewCount + (count - tabCount) * getWidth() / tabCount, 0);

        } else {
            scrollTo(-getWidth() / tabCount / 2 * outViewCount + (position - tabCount + 2) * getWidth() / tabCount + offset / tabCount, 0);
        }

        initialIndicator = (getChildAt(0).getLeft() - getChildAt(0).getRight()) * (outViewCount - 1) / 2 - triangleWidth / 2;

        indicatorOffset = (getWidth() / tabCount * position) + offset / tabCount;

        highLightText(offset > getScreenWidth() / 2 ? position + 1 : position);
        invalidate();
    }

    private void highLightText(int position) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) getChildAt(i);
            view.setTextColor(getResources().getColor(R.color.textDisable));
        }

        TextView view = (TextView) getChildAt(position);
        view.setTextColor(Color.WHITE);
    }

    public void registerListener(TabtouchListener listener) {
        this.listener = listener;
    }

    public interface TabtouchListener {
        void onTabTouch(int position);
    }
}
