package com.burning.click.burnheadphone.broadReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScanMediaBroadReceiver extends BroadcastReceiver {
    public ScanMediaBroadReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (Intent.ACTION_MEDIA_SCANNER_STARTED == intent.getAction()) {
        } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED == intent.getAction()) {

        }

//        throw new UnsupportedOperationException("Not yet implemented");


    }
}
