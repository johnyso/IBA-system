package de.ibs.app.roomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import de.ibs.app.R;

import java.util.AbstractList;

/**
 * Created by johnyso on 13.11.14.
 */
public class RoomView extends View {
    private boolean showText;
    private int textPos;
    private Paint textPaint;
    private int textColor;
    private float textHeight;
    private Paint roomPaint;
    private Paint shadowPaint;
    private RectF shadowBounds;
    private AbstractList data;
    private int currentItem;
    private Object label;
    private Object textX;
    private Object textY;

    private int roomLength;
    private int roomWidth;

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoomView,
                0, 0);

        try {
            this.showText = a.getBoolean(R.styleable.RoomView_showText, false);
            this.textPos = a.getInteger(R.styleable.RoomView_labelPosition, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(this.textColor);
        if (this.textHeight == 0) {
            this.textHeight = this.textPaint.getTextSize();
        } else {
            this.textPaint.setTextSize(this.textHeight);
        }

        this.roomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.roomPaint.setStyle(Paint.Style.FILL);
        this.roomPaint.setTextSize(this.textHeight);

        this.shadowPaint = new Paint(0);
        this.shadowPaint.setColor(0xff101010);
        this.shadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        invalidate();
        requestLayout();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.shadowBounds = new RectF(20,20,20,20);
        // Draw the shadow
        this.textPaint.setColor(Color.WHITE);
        canvas.drawLine(
                2, 2, 20, 20, this.textPaint
        );

        RectF drawRoundRect = new RectF();


        float pHeight;
        float windowHeight = getHeight();
        float windowWidth = getWidth();
        Log.d("RoomView","windowHeight: "+getHeight());
        Log.d("RoomView","LengtOrig: "+this.roomLength);
        Log.d("RoomView","window: "+(double) this.roomLength / (double) getHeight());
        double pixelLength = (double) this.roomLength / (double) getHeight();
        Log.d("RoomView","Length: "+pixelLength);
        double pixelWidth = (double) this.roomWidth / pixelLength ;
        Log.d("RoomView","Width: "+pixelWidth);
        drawRoundRect.set(0,0,(int) pixelWidth,(int)windowHeight);

        Paint innerPaint = new Paint();
        innerPaint.setARGB(0, 0, 0, 0);
        innerPaint.setAlpha(0);
        innerPaint.setStyle(Paint.Style.FILL);

        Paint borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 128, 0);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        canvas.drawRoundRect(drawRoundRect, 2, 2, innerPaint);
        canvas.drawRoundRect(drawRoundRect, 2, 2, borderPaint);

        // Draw the label text
       // canvas.drawText(this.data.get(this.currentItem).this.label, this.textX, this.textY, this.textPaint);

        // Draw the pie slices
    }

    public int getRoomLength() {
        return roomLength;
    }

    public void setRoomLength(int roomLength) {
        this.roomLength = roomLength;
    }

    public int getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(int roomWidth) {
        this.roomWidth = roomWidth;
    }
}
