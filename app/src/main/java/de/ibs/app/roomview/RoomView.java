package de.ibs.app.roomview;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import de.ibs.app.R;
import de.ibs.app.room.RoomContract;
import de.ibs.app.room.processor.Room;
import de.ibs.app.speaker.processor.Speaker;

import java.util.List;

import static de.ibs.app.room.RoomContract.CONTENT_URI;
import static de.ibs.app.room.RoomContract.ROOMS;

/**
 * Created by johnyso on 13.11.14.
 */
public class RoomView extends View implements View.OnTouchListener {
    private final Context context;
    private boolean showText;
    private int textPos;
    private Paint textPaint;
    private int textColor;
    private float textHeight;
    private Paint roomPaint;
    private Paint shadowPaint;
    private RectF shadowBounds;

    private Bitmap icon;

    private Canvas canvas;
    private int widthInPixel;
    private double pixelFactor;
    private double lengthInPixel;
    private double heightInPixel;
    private float paddingLeft;
    private float iconLeftPosition;
    private float iconRightPosition;
    private Room room;
    private Speaker[] speakers;

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
        this.context = context;
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
        this.shadowBounds = new RectF(20, 20, 20, 20);
        // Draw the shadow
        this.textPaint.setColor(Color.WHITE);
        this.canvas = canvas;
        RectF drawRoundRect = new RectF();


        this.heightInPixel = getHeight();

        this.pixelFactor = (double) this.room.getLength() / (double) getHeight();
        this.lengthInPixel = (double) this.room.getWidth() / this.pixelFactor;

        this.paddingLeft = (float) (getWidth() - this.lengthInPixel) / 2;
        drawRoundRect.set(this.paddingLeft, 0, (int) this.lengthInPixel + this.paddingLeft, (float) this.heightInPixel);

        Paint innerPaint = new Paint();
        innerPaint.setARGB(0, 0, 0, 0);
        innerPaint.setAlpha(0);
        innerPaint.setStyle(Paint.Style.FILL);

        Paint borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 128, 0);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);
        this.icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(this.icon, this.room.getPersonX(), this.room.getPersonY(), null);

        canvas.drawRoundRect(drawRoundRect, 2, 2, innerPaint);
        canvas.drawRoundRect(drawRoundRect, 2, 2, borderPaint);

        //TODO: remove this code an put the speakers in

        Bitmap speakerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.speaker_icon);
        speakerIcon = Bitmap.createScaledBitmap(speakerIcon,20,20,true);

        for (Speaker speaker : this.speakers){
            canvas.drawBitmap(speakerIcon, this.paddingLeft + speaker.getPositionX(),speaker.getPositionY(),null);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventaction = event.getAction();

        this.iconLeftPosition = this.paddingLeft + (this.icon.getWidth() / 2);
        this.iconRightPosition = (float) this.lengthInPixel + this.paddingLeft - (this.icon.getWidth() / 2);



        if(event.getX() > this.iconLeftPosition && event.getX() < (int) this.iconRightPosition) {
            this.room.setPersonX((int) event.getX() - (this.icon.getWidth() / 2));
        } else if(event.getX() < this.iconLeftPosition){
            this.room.setPersonX((int) this.paddingLeft);
        } else if(event.getX() > (int) this.iconRightPosition){
            this.room.setPersonX((int) (this.iconRightPosition - this.icon.getHeight() / 2));
        }

        if(event.getY() >= (getTop() - (this.icon.getHeight() / 2)) && event.getY() <= (this.heightInPixel - (this.icon.getHeight() / 2))){
            this.room.setPersonY((int) event.getY() - (this.icon.getHeight() / 2));
        }
/*        else if (event.getY() < getTop()) {
            this.personY = (int) getTop() - (this.icon.getHeight() / 2);
        } else if (event.getY() > this.heightInPixel) {
            this.personY = (int) this.heightInPixel - (this.icon.getHeight() / 2);
        }
*/

        this.invalidate();
        switch (eventaction) {

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                Log.d("RoomView","Mouse Up");
                ContentValues values = new ContentValues();
                values.put(RoomContract.Rooms.PERSON_X,this.room.getPersonX());
                values.put(RoomContract.Rooms.PERSON_Y,this.room.getPersonY());
                values.put(RoomContract.Rooms.PERSON_HEIGHT,this.room.getPersonHeight());
                context.getContentResolver().update(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + this.room.getId()), values, null, null);
                float x = this.room.getPersonX() - this.paddingLeft - (this.icon.getWidth() / 2) ;
                float y = this.room.getPersonY() - (this.icon.getHeight() / 2);
                double deg = Math.toDegrees(Math.atan(y/x));
                Log.d("RoomView","Value degree: " + deg + " From x: " + x + " and y: " + y);
/*
                Intent intent =  new Intent(context, SpeakerRequest.class);
                intent.putExtra(SpeakerConstants.REST_ID, "horizontal/"+(int) deg);
                this.context.startService(intent);
*/
                break;
        }

        return true;
    }

    public void setRoom(Room room) {
        this.room = room;
        this.invalidate();
    }

    public Room getRoom() {
        return room;
    }

    public void setSpeaker(Speaker[] speakers) {
        this.speakers = speakers;
    }
}
