package de.ibs.app.speaker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import de.ibs.app.speaker.restmethod.SpeakerRequest;

/**
 * Created by johnyso on 11.11.14.
 */
public class SpeakerButtonOnclick implements View.OnClickListener {

    private final Context context;

    public SpeakerButtonOnclick(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Log.d("Buttonlcick","onStart");
        Intent intent =  new Intent(context, SpeakerRequest.class);
        context.startService(intent);
    }
}
