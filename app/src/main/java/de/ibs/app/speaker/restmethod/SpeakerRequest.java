package de.ibs.app.speaker.restmethod;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

/**
 * Created by johnyso on 11.11.14.
 */
public class SpeakerRequest extends Service {
    private HandlerThread executor;
    private Handler handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(this.executor == null || !this.executor.isAlive()) {
            this.executor = new HandlerThread("Speaker Executor");
            this.executor.start();
            this.handler = new Handler(this.executor.getLooper());
        }

        this.handler.post(new SpeakerTask(this, intent.getExtras()));

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
