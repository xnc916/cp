package com.chuck.wheelpackerview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.chuck.wheelpackerview.R;
import com.chuck.wheelpackerview.library.adapter.BaseWheelAdapter;
import com.chuck.wheelpackerview.library.listener.IFlingListener;
import com.chuck.wheelpackerview.library.listener.IWheelViewModel;
import com.chuck.wheelpackerview.library.listener.OnItemSelectedListener;
import com.chuck.wheelpackerview.library.listener.WheelItemGestureListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class WheelItemView extends View {
    private static final float TEXTSCALE = 0.8f;
    private static final float NORMALSCALE = 1f;
    /**
     * 画笔属性
     */
    private WheelItemStyleHelper styleOptions;
    /**
     * 测量
     */
    private WheelMeasureHelper measureHelper;
    /**
     * 手势
     */
    private GestureDetector gestureDetector;
    private IFlingListener flingListener;
    /**
     * 消息处理
     */
    private H mHandler;
    private int widthMeasureSpec;
    /**
     * 可见item
     */
    int itemVisibleCount = 11;
    protected int initPosition = -1;
    private int preCurrentPosition;
    protected int scrollY;
    private int selectedItem;

    private int drawSelectTextStart;
    private int drawUnselectTextStart;

    private BaseWheelAdapter adapter;

    long startTime = 0;
    private float previousY = 0;
    private int mOffset = 0;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    /**
     * 改变滑动速度
     */
    private static final int VELOCITYFLING = 5;
    OnItemSelectedListener onItemSelectedListener;

    public WheelItemView(Context context) {
        this(context, null);
    }

    public WheelItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        styleOptions = new WheelItemStyleHelper();
        measureHelper = new WheelMeasureHelper();
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WheelItemView, 0, 0);
            styleOptions.backgroundColor = array.getColor(R.styleable.WheelItemView_wheel_item_view_background
                    , getColor(styleOptions.COLOR_BACKGROUND_DEFAULT));
            styleOptions.selectTextColor = array.getColor(R.styleable.WheelItemView_wheel_item_view_select_textcolor
                    , getColor(styleOptions.COLOR_TEXT_SELECT_DEFAULT));
            styleOptions.unSelectTextColor = array.getColor(R.styleable.WheelItemView_wheel_item_view_unselect_textcolor
                    , getColor(styleOptions.COLOR_TEXT_UNSELECT_DEFAULT));
            styleOptions.selectTextSize = array.getColor(R.styleable.WheelItemView_wheel_item_view_select_textsize
                    , getDimensionPixel(styleOptions.TEXTSIZE_SELECT));
            styleOptions.unSelectTextSize = array.getColor(R.styleable.WheelItemView_wheel_item_view_unselect_textsize
                    , getDimensionPixel(styleOptions.TEXTSIZE_UNSELECT));
            styleOptions.mGravity = array.getInt(R.styleable.WheelItemView_wheel_item_view_gravity
                    , styleOptions.GRAVITY_DEFAULT);
            array.recycle();
        }
        initView(context);
    }

    private void initView(Context context) {
        mHandler = new H();
        flingListener = new IFlingListener() {
            @Override
            public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                scrollBy(velocityY);
            }
        };
        gestureDetector = new GestureDetector(context, new WheelItemGestureListener(flingListener));
        gestureDetector.setIsLongpressEnabled(false);
        styleOptions.initTextPaints();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        measureHelper.remeasure(adapter, styleOptions, widthMeasureSpec, itemVisibleCount);
        if (initPosition == -1) {
            initPosition = 0;
        }
        preCurrentPosition = initPosition;
        setMeasuredDimension(measureHelper.measuredWidth, measureHelper.measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (adapter == null) {
            return;
        }
        IWheelViewModel[] visibleModels = new IWheelViewModel[itemVisibleCount];
        preCurrentPosition = initPosition + ((int) (scrollY / measureHelper.itemHeight)) % adapter.getItemCount();
        if (preCurrentPosition < 0) {
            preCurrentPosition = 0;
        }
        if (preCurrentPosition > adapter.getItemCount() - 1) {
            preCurrentPosition = adapter.getItemCount() - 1;
        }
        int itemHeightOffset = (int) (scrollY % measureHelper.itemHeight);
        int countIndex = 0;
        while (countIndex < itemVisibleCount) {
            int index = preCurrentPosition - (itemVisibleCount / 2 - countIndex);
            if (index < 0) {
                visibleModels[countIndex] = null;
            } else if (index > adapter.getItemCount() - 1) {
                visibleModels[countIndex] = null;
            } else {
                visibleModels[countIndex] = adapter.getItem(index);
            }
            countIndex++;
        }

        canvas.drawLine(0.0f, measureHelper.topLineY, measureHelper.measuredWidth, measureHelper.topLineY, styleOptions.linePain);
        canvas.drawLine(0.0f, measureHelper.bottomLineY, measureHelper.measuredWidth, measureHelper.bottomLineY, styleOptions.linePain);
        countIndex = 0;
        while (countIndex < itemVisibleCount) {
            canvas.save();
            float itemHeight = measureHelper.maxTextHeight * measureHelper.lineSpacingMultiplier;
            double rl = ((itemHeight * countIndex - itemHeightOffset) * Math.PI) / measureHelper.halfCircumference;
            float angle = (float) (90d - (rl / Math.PI) * 180d);
            if (angle >= 90f || angle <= -90f) {
                canvas.restore();
            } else {
                String text = getContentString(visibleModels[countIndex]);
                measureTextStart(text);
                float translateY = (float) (measureHelper.radius - Math.cos(rl) * measureHelper.radius
                        - (Math.sin(rl) * measureHelper.maxTextHeight) / 2d);
                canvas.translate(0.0f, translateY);
                canvas.scale(1.0f, (float) Math.sin(rl));
                drawContent(text, translateY, rl, visibleModels, itemHeight, countIndex, canvas);
                canvas.restore();
            }
            countIndex++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean gdEvent = gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                previousY = event.getRawY();
                scrollY += dy;
                float top = -initPosition * measureHelper.itemHeight;
                float bottom = (adapter.getItemCount() - 1 - initPosition) * measureHelper.itemHeight;
                if (scrollY - measureHelper.itemHeight * 0.3 < top) {
                    top = scrollY - dy;
                } else if (scrollY + measureHelper.itemHeight * 0.3 > bottom) {
                    bottom = scrollY - dy;
                }

                if (scrollY < top) {
                    scrollY = (int) top;
                } else if (scrollY > bottom) {
                    scrollY = (int) bottom;
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!gdEvent) {
                    float y = event.getY();
                    double l = Math.acos((measureHelper.radius - y) / measureHelper.radius) * measureHelper.radius;
                    int circlePosition = (int) ((l + measureHelper.itemHeight / 2) / measureHelper.itemHeight);

                    float extraOffset = (scrollY % measureHelper.itemHeight + measureHelper.itemHeight) % measureHelper.itemHeight;
                    mOffset = (int) ((circlePosition - itemVisibleCount / 2) * measureHelper.itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 处理拖拽事件
                        smoothMoveCenterScroll(ACTION.DAGGLE);
                    } else {
                        // 处理item点击事件
                        smoothMoveCenterScroll(ACTION.CLICK);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    private void drawContent(String text, float translateY, double rl
            , IWheelViewModel[] visibleModels, float itemHeight, int countIndex, Canvas canvas) {
        if (text == null) {
            text = "";
        }
        if (translateY <= measureHelper.topLineY && measureHelper.maxTextHeight + translateY >= measureHelper.topLineY) {//穿越第一条线
            canvas.save();
            canvas.clipRect(0, 0, measureHelper.measuredWidth, measureHelper.topLineY - translateY);
            canvas.scale(NORMALSCALE, (float) (Math.sin(rl) * TEXTSCALE));
            canvas.drawText(text, drawUnselectTextStart, measureHelper.maxTextHeight, styleOptions.unSelectTextPain);
            canvas.restore();
            canvas.save();
            canvas.clipRect(0, measureHelper.topLineY - translateY, measureHelper.measuredWidth, itemHeight);
            canvas.scale(NORMALSCALE, (float) (Math.sin(rl) * NORMALSCALE));
            canvas.drawText(text, drawSelectTextStart, measureHelper.maxTextHeight - measureHelper.SELECTTEXTOFFSET, styleOptions.selectTextPaint);
            canvas.restore();
        } else if (translateY <= measureHelper.bottomLineY && measureHelper.maxTextHeight + translateY >= measureHelper.bottomLineY) {//穿越第二条线
            canvas.save();
            canvas.clipRect(0, 0, measureHelper.measuredWidth, measureHelper.bottomLineY - translateY);
            canvas.scale(NORMALSCALE, (float) (Math.sin(rl) * NORMALSCALE));
            canvas.drawText(text, drawSelectTextStart, measureHelper.maxTextHeight - measureHelper.SELECTTEXTOFFSET, styleOptions.selectTextPaint);
            canvas.restore();
            canvas.save();
            canvas.clipRect(0.0f, measureHelper.bottomLineY - translateY, measureHelper.measuredWidth, itemHeight);
            canvas.scale(NORMALSCALE, (float) (Math.sin(rl) * TEXTSCALE));
            canvas.drawText(text, drawUnselectTextStart, measureHelper.maxTextHeight, styleOptions.unSelectTextPain);
            canvas.restore();
        } else if (translateY >= measureHelper.topLineY && measureHelper.maxTextHeight + translateY <= measureHelper.bottomLineY) {//中间区域
            canvas.clipRect(0, 0, measureHelper.measuredWidth, itemHeight);
            canvas.drawText(text, drawSelectTextStart, measureHelper.maxTextHeight - measureHelper.SELECTTEXTOFFSET, styleOptions.selectTextPaint);
            int preSelectedItem = adapter.indexOf(visibleModels[countIndex]);
            if (preSelectedItem != -1) {
                selectedItem = preSelectedItem;
            }
        } else {
            canvas.save();
            canvas.clipRect(0, 0, measureHelper.measuredWidth, itemHeight);
            canvas.scale(1.0F, (float) Math.sin(rl) * TEXTSCALE);
            canvas.drawText(text, drawUnselectTextStart, measureHelper.maxTextHeight, styleOptions.unSelectTextPain);
            canvas.restore();
        }
    }

    protected void smoothMoveCenterScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            mOffset = (int) ((scrollY % measureHelper.itemHeight + measureHelper.itemHeight) % measureHelper.itemHeight);
            if ((float) mOffset > measureHelper.itemHeight / 2.0F) {
                mOffset = (int) (measureHelper.itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        //停止的时候，位置有偏移，不是全部都能正确停止到中间位置的，这里把文字位置挪回中间去
        mFuture = mExecutor.scheduleWithFixedDelay(new MoveCenterTimerTask(this, mOffset), 0, VELOCITYFLING, TimeUnit.MILLISECONDS);
    }

    public final void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    private void measureTextStart(String text) {
        Rect rect = new Rect();
        if (text == null) {
            text = "";
        }
        styleOptions.selectTextPaint.getTextBounds(text, 0, text.length(), rect);
        styleOptions.unSelectTextPain.getTextBounds(text, 0, text.length(), rect);
        switch (styleOptions.mGravity) {
            case Gravity.CENTER:
                drawUnselectTextStart = drawSelectTextStart = (measureHelper.measuredWidth - rect.width()) / 2;
                break;
            case Gravity.LEFT:
                drawUnselectTextStart = drawSelectTextStart = 0;
                break;
            case Gravity.RIGHT:
                drawUnselectTextStart = drawSelectTextStart = measureHelper.measuredWidth - rect.width();
                break;
        }
    }

    private String getContentString(IWheelViewModel o) {
        if (o == null) {
            return "";
        }
        return o.getValueString();
    }

    private final void scrollBy(float velocityY) {
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTask(this, velocityY), 0, VELOCITYFLING
                , TimeUnit.MILLISECONDS);
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    private int getDimensionPixel(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    protected int getItemCount() {
        return adapter == null ? 0 : adapter.getItemCount();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    protected final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }

    public final int getCurrentItem() {
        return selectedItem;
    }

    public final void setAdapter(BaseWheelAdapter adapter) {
        this.adapter = adapter;
        measureHelper.remeasure(adapter, styleOptions, widthMeasureSpec, itemVisibleCount);
        invalidate();
    }

    public final void setCurrentItem(int currentItem) {
        this.initPosition = currentItem;
        scrollY = 0;//回归顶部,不然会显示出错误位置的数据
        invalidate();
    }

    protected WheelMeasureHelper getMeasureHelper() {
        return measureHelper;
    }

    public final H getHandler() {
        if (mHandler == null) {
            mHandler = new H();
        }
        return mHandler;
    }

    /**
     * 处理各种消息的handler
     */
    public final class H extends Handler {
        public static final int WHAT_INVALIDATE = 0x01;
        public static final int WHAT_SMOOTH_SCROLL = 0x02;
        public static final int WHAT_ITEM_SELECTED = 0x03;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_INVALIDATE:
                    invalidate();
                    break;
                case WHAT_SMOOTH_SCROLL:
                    smoothMoveCenterScroll(ACTION.FLING);
                    break;
                case WHAT_ITEM_SELECTED:
                    onItemSelected();
                    break;
            }
        }
    }

    public enum ACTION {
        // 点击，滑翔，拖拽事件
        CLICK, FLING, DAGGLE
    }

    public void setSelectTextColor(int color) {
        if (styleOptions != null) {
            styleOptions.setSelectTextColor(getColor(color));
        }
    }

    public void setSelectTextSize(int dimen) {
        if (styleOptions != null) {
            styleOptions.setSelectTextSize(getDimensionPixel(dimen));
        }
    }

    public void setUnselectTextColor(int color) {
        if (styleOptions != null) {
            styleOptions.setUnselectTextColor(getColor(color));
        }
    }

    public void setUnselectTextSize(int dimen) {
        if (styleOptions != null) {
            styleOptions.setUnselectTextSize(getDimensionPixel(dimen));
        }
    }
}
