package com.burning.click.burnheadphone.broadReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.burning.click.burnheadphone.util.SpkeyName;

/**
 * Created by click on 16-5-19.
 */
public class HeadsetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("state")) {
            if (0 == intent.getIntExtra("state", 0)) {
                Toast.makeText(context, "耳机未插入", Toast.LENGTH_SHORT).show();
                SpkeyName.HEADSETSTATE=0;
            } else if (1 == intent.getIntExtra("state", 0)) {
                Toast.makeText(context, "耳机已插入", Toast.LENGTH_SHORT).show();
                SpkeyName.HEADSETSTATE=1;
            }
        }

    }
}
