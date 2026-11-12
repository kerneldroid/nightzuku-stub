package moe.shizuku.privileged.api;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Opens the Nightzuku manager when a legacy client asks the Shizuku package for permission.
 */
public class ForwardActivity extends Activity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        String manager = ManagerLocator.resolve(this);
        if (manager == null) {
            Toast.makeText(this, R.string.manager_missing, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Intent request = new Intent(manager + ".intent.action.REQUEST_PERMISSION")
                .setPackage(manager)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(request);
            finish();
            return;
        } catch (ActivityNotFoundException ignored) {
        }

        Intent launch = getPackageManager().getLaunchIntentForPackage(manager);
        if (launch != null) {
            try {
                launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launch);
                finish();
                return;
            } catch (Throwable ignored) {
            }
        }

        Toast.makeText(this, R.string.manager_missing, Toast.LENGTH_LONG).show();
        finish();
    }
}
