# Nightzuku Stub

Minimal compatibility app for `moe.shizuku.privileged.api`.

It declares the legacy Shizuku permission and forwards binder/permission requests to the
installed Nightzuku manager. The manager is discovered through the fixed provider authority
`kerneldroid.nightzuku.manager`, so this works with default and stealth Nightzuku builds.

The stub is a separate APK. It is not embedded in the Nightzuku client.

## Build

```bash
./gradlew :app:assembleRelease
```

Signing uses `signing.properties` in the repository root when present; otherwise the debug key
is used.

## Notes

- Do not install the stub together with the official Shizuku app or another stub that declares
  the same package/permissions.
- The stub does not contain the privileged server or any Nightzuku code.
