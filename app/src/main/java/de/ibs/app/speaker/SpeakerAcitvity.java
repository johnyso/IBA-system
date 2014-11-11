package de.ibs.app.speaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import de.ibs.app.R;

public class SpeakerAcitvity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_acitvity);

        Button button = (Button) findViewById(R.id.button_up);
        button.setOnClickListener(new SpeakerButtonOnclick(this));
    }


}
