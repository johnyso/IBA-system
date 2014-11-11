package de.ibs.app.speaker.restmethod;

import android.app.Service;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by johnyso on 11.11.14.
 */
public class SpeakerTask implements Runnable {
    private final Service context;
    private final OkHttpClient client = new OkHttpClient();

    public SpeakerTask(Service context, Bundle extras) {
        this.context = context;
    }

    @Override
    public void run() {
        String response = null;
        try {
            response = run("http://141.62.110.99/arduino/down/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Task",""+response);
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }
}
