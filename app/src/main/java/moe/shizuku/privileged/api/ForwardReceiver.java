package moe.shizuku.privileged.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Forwards legacy Shizuku binder requests to the installed Nightzuku manager.
 *
 * Client apps broadcast to {@code moe.shizuku.privileged.api} or to this stub's package; the
 * request is re-delivered to the manager's exported receiver, which answers with the binder.
 */
public class ForwardReceiver extends BroadcastReceiver {

    private static final String ACTION_REQUEST_BINDER = "rikka.shizuku.intent.action.REQUEST_BINDER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !ACTION_REQUEST_BINDER.equals(intent.getAction())) {
            return;
        }
        String manager = ManagerLocator.resolve(context);
        if (manager == null) {
            return;
        }

        Intent forward = new Intent(ACTION_REQUEST_BINDER)
                .setPackage(manager)
                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            forward.putExtras(extras);
        }
        try {
            context.sendBroadcast(forward);
        } catch (Throwable ignored) {
        }
    }
}
