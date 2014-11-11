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

        Button buttonUp = (Button) findViewById(R.id.button_up);
        buttonUp.setOnClickListener(new SpeakerButtonOnclick(this,"up"));

        Button buttonDown = (Button) findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new SpeakerButtonOnclick(this,"down"));

        Button buttonLeft = (Button) findViewById(R.id.button_left);
        buttonLeft.setOnClickListener(new SpeakerButtonOnclick(this,"left"));

        Button buttonRight = (Button) findViewById(R.id.button_right);
        buttonRight.setOnClickListener(new SpeakerButtonOnclick(this,"right"));
    }


}
