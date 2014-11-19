package de.ibs.app.roomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import de.ibs.app.R;

import java.util.AbstractList;

/**
 * Created by johnyso on 13.11.14.
 */
public class RoomView extends View implements View.OnTouchListener {
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
    private Bitmap icon;

    private int roomLength;
    private int roomWidth;
    private Canvas canvas;
    private int x = 20;
    private int y = 20;

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
        this.setOnTouchListener(this);
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
        this.canvas = canvas;
        RectF drawRoundRect = new RectF();


        float windowHeight = getHeight();

        double pixelLength = (double) this.roomLength / (double) getHeight();
        double pixelWidth = (double) this.roomWidth / pixelLength ;

        float padding = (float) (getWidth() - pixelWidth)/2;
        drawRoundRect.set(padding,0,(int) pixelWidth+padding,(int)windowHeight);

        Paint innerPaint = new Paint();
        innerPaint.setARGB(0, 0, 0, 0);
        innerPaint.setAlpha(0);
        innerPaint.setStyle(Paint.Style.FILL);

        Paint borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 128, 0);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);
        this.icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(this.icon, this.x, this.y, null);

        canvas.drawRoundRect(drawRoundRect, 2, 2, innerPaint);
        canvas.drawRoundRect(drawRoundRect, 2, 2, borderPaint);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("RoomDetail","x: " + event.getX() + " y: " + event.getY() + "Top: "+ getTop());
        int eventaction = event.getAction();

        this.x = (int)event.getX()-(this.icon.getWidth()/2);
        this.y = (int)event.getY()-(this.icon.getHeight()/2);

        this.invalidate();
        switch (eventaction) {

        /*    case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
                    // check all the bounds of the ball
                    if (X > icon.getX() && X < ball.getX()+50 && Y > ball.getY() && Y < ball.getY()+50){
                        balID = ball.getID();
                        break;
                    }

                break;

*/
            case MotionEvent.ACTION_MOVE:   // touch drag with the ball
                // move the balls the same as the finger


                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping

                break;
        }
        // redraw the canvas

        return true;
    }
}
