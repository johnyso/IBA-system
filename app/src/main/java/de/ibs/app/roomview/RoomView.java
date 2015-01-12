package de.ibs.app.roomview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.room.processor.Room;
import de.ibs.app.speaker.SpeakerConstants;
import de.ibs.app.speaker.processor.Speaker;
import de.ibs.app.speaker.restmethod.SpeakerRequest;

import static de.ibs.app.room.utils.RoomContract.CONTENT_URI;
import static de.ibs.app.room.utils.RoomContract.ROOMS;

/**
 * Created by johnyso on 13.11.14.
 */
public class RoomView extends View implements View.OnTouchListener {
    private final Context context;
    private Bitmap icon;
    private float iconLeftPosition;
    private float iconRightPosition;
    private Room room;
    private Speaker[] speakers;
    private Paint borderColor;
    private Paint transparent;

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        this.setOnTouchListener(this);
    }

    private void init() {
        this.borderColor = new Paint();
        borderColor.setARGB(255, 255, 255, 255);
        borderColor.setStyle(Paint.Style.STROKE);
        borderColor.setStrokeWidth(4);

        this.transparent = new Paint();
        transparent.setARGB(0, 0, 0, 0);
        transparent.setAlpha(0);
        transparent.setStyle(Paint.Style.FILL);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = this.room.getRectInPixel(getHeight(), getWidth());

        this.icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(this.icon, this.room.getPersonX(), this.room.getPersonY(), null);

        canvas.drawRoundRect(rect, 2, 2, this.transparent);
        canvas.drawRoundRect(rect, 2, 2, this.borderColor);

        //TODO: remove this code an put the speakers in
        Bitmap speakerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.speaker_icon);
        speakerIcon = Bitmap.createScaledBitmap(speakerIcon, 40, 40, true);
        Bitmap rot;
        Matrix matrix = new Matrix();
        if (speakers != null) {
            for (Speaker speaker : this.speakers) {
                switch (speaker.getAlignment()){
                    case RoomContract.Speakers.ALIGNMENT_LEFT:
                        matrix.setRotate(Math.abs(speaker.getHorizontal()+270), 0, 0);
                        break;
                    case RoomContract.Speakers.ALIGNMENT_RIGHT:
                        matrix.setRotate(speaker.getHorizontal()+90, 0, 0);
                        break;
                    case RoomContract.Speakers.ALIGNMENT_BOTTOM:
                        matrix.setRotate(Math.abs(speaker.getHorizontal()-360), 0, 0);
                        break;
                    default:
                        matrix.setRotate(speaker.getHorizontal(), 0, 0);
                        break;
                }


                rot = speakerIcon.createBitmap(speakerIcon, 0, 0, speakerIcon.getWidth(), speakerIcon.getHeight(), matrix, true);

                canvas.drawBitmap(rot, this.room.getPaddingLeft() + speaker.getPositionX(), speaker.getPositionY(), null);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventaction = event.getAction();

        this.iconLeftPosition = this.room.getPaddingLeft() + (this.icon.getWidth() / 2);
        this.iconRightPosition = (float) this.room.getLengthInPixel() + this.room.getPaddingLeft() - (this.icon.getWidth() / 2);

        // x-Richtung
        if (event.getX() > this.iconLeftPosition && event.getX() < (int) this.iconRightPosition) {
            this.room.setPersonX((int) event.getX() - (this.icon.getWidth() / 2));
        } else if (event.getX() < this.iconLeftPosition) {
            this.room.setPersonX((int) this.room.getPaddingLeft());
        } else if (event.getX() > (int) this.iconRightPosition) {
            this.room.setPersonX((int) (this.iconRightPosition - this.icon.getHeight() / 2));
        }

        // Y-Richtung
        if (event.getY() >= ((this.icon.getHeight() / 2)) && event.getY() <= (this.getHeight() - (this.icon.getHeight() / 2))) {
            this.room.setPersonY((int) event.getY() - (this.icon.getHeight() / 2));
        }
        else if (event.getY() < this.getTop()) {
            this.room.setPersonY(0);
        }
        else if (event.getY() > this.getHeight() - (this.icon.getHeight() / 2)) {
            this.room.setPersonY(this.getHeight() - this.icon.getHeight());
        }


        if (speakers != null) {
            for (Speaker speaker : this.speakers) {
                // x
                float x = this.room.getPersonX() - this.room.getPaddingLeft() - speaker.getPositionX();
                float y = this.room.getPersonY() - speaker.getPositionY();
                double deg = Math.toDegrees(Math.atan(y / x));
                int align = speaker.getAlignment();
                int test = RoomContract.Speakers.ALIGNMENT_TOP;

                switch (align) {
                    case RoomContract.Speakers.ALIGNMENT_TOP:
                        if(deg < 0 || Double.toString(deg).equals("-0.0")){
                            deg = 180 + deg;
                        } else {
                            deg = Math.abs(deg);
                        }
                        break;
                    case RoomContract.Speakers.ALIGNMENT_RIGHT:
                        if(deg>=0) {
                            deg = 90 + deg;
                        } else {
                            deg = Math.abs(90 + deg);
                        }
                        break;
                    case RoomContract.Speakers.ALIGNMENT_BOTTOM:
                        if(deg >= 0) {
                            deg = Math.abs(-180 + deg);
                        } else {
                            deg = Math.abs(deg);
                        }
                        break;
                    case RoomContract.Speakers.ALIGNMENT_LEFT:
                        if(deg<0) {
                            deg = 90 + deg;
                        } else {
                            deg = Math.abs(90 + deg);
                        }
                        break;
                    default:
                        break;
                }

                speaker.setHorizontal((int) deg);
            }
        }
        this.invalidate();
        switch (eventaction) {

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                Log.d("RoomView", "Mouse Up");

                ContentValues values = new ContentValues();

                values.put(RoomContract.Rooms.PERSON_X, this.room.getPersonX());

                values.put(RoomContract.Rooms.PERSON_Y, this.room.getPersonY());

                values.put(RoomContract.Rooms.PERSON_HEIGHT, this.room.getPersonHeight());

                context.getContentResolver().update(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + this.room.getId()), values, null, null);

                this.invalidate();

                if (speakers != null) {
                    for (Speaker speaker : this.speakers) {
                        Intent intent =  new Intent(context, SpeakerRequest.class);

                        intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.HORIZONTAL, speaker.getHorizontal(), speaker.getIp()));

                        this.context.startService(intent);
                    }
                }



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
