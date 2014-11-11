package de.ibs.app.speaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import de.ibs.app.speaker.restmethod.SpeakerRequest;

/**
 * Created by johnyso on 11.11.14.
 */
public class SpeakerButtonOnclick implements View.OnClickListener {
    private final Context context;
    private String restPath;

    public SpeakerButtonOnclick(Context context, String restPath) {
        this.context = context;
        this.restPath = restPath;
    }

    @Override
    public void onClick(View v) {
        Intent intent =  new Intent(context, SpeakerRequest.class);
        intent.putExtra(SpeakerConstants.REST_ID, this.restPath);
        context.startService(intent);
    }
}
