package moe.shizuku.privileged.api;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;

/**
 * Resolves the installed Nightzuku manager without assuming a fixed package id.
 *
 * Nightzuku exposes the fixed provider authority {@code kerneldroid.nightzuku.manager} from
 * every build, including stealth builds with a randomized application id.
 */
final class ManagerLocator {

    static final String DISCOVERY_AUTHORITY = "kerneldroid.nightzuku.manager";
    static final String DEFAULT_PACKAGE = "kerneldroid.nightzuku";

    private static volatile String cachedPackage;

    private ManagerLocator() {
    }

    static String resolve(Context context) {
        String cached = cachedPackage;
        if (cached != null) {
            return cached;
        }

        PackageManager pm = context.getPackageManager();
        try {
            ProviderInfo provider = pm.resolveContentProvider(DISCOVERY_AUTHORITY, 0);
            if (provider != null && provider.packageName != null) {
                cachedPackage = provider.packageName;
                return provider.packageName;
            }
        } catch (Throwable ignored) {
        }

        try {
            pm.getPackageInfo(DEFAULT_PACKAGE, 0);
            cachedPackage = DEFAULT_PACKAGE;
            return DEFAULT_PACKAGE;
        } catch (Throwable ignored) {
            return null;
        }
    }
}
