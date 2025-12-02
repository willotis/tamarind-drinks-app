# Firebase Setup Guide

## Prerequisites

Before you can run the app with Firebase features (Google Sign-In, FCM, Crashlytics, Analytics), you need to set up a Firebase project.

## Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project"
3. Enter project name: `Tamarind Drinks` (or your preferred name)
4. Enable Google Analytics (recommended)
5. Click "Create project"

## Step 2: Add Android App to Firebase

1. In the Firebase Console, click the Android icon to add an Android app
2. Enter package name: `com.tamarind.drinks`
3. Enter app nickname: `Tamarind Drinks Android`
4. Leave the SHA-1 field empty for now (we'll add it later for Google Sign-In)
5. Click "Register app"

## Step 3: Download google-services.json

1. Download the `google-services.json` file from Firebase Console
2. Place it in the `app/` directory of your project:
   ```
   Tarmarind drinks app/
   └── app/
       └── google-services.json  ← Place file here
   ```

## Step 4: Enable Firebase Services

### Firebase Cloud Messaging (FCM)
1. In Firebase Console, go to "Build" → "Cloud Messaging"
2. No additional setup required - it's enabled by default

### Crashlytics
1. Go to "Build" → "Crashlytics"
2. Click "Enable Crashlytics"

### Analytics
1. Go to "Build" → "Analytics"
2. Already enabled if you selected it during project creation

## Step 5: Configure Google Sign-In

### Get SHA-1 Certificate Fingerprint

**For Debug Builds:**
```bash
# Windows
keytool -list -v -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore -storepass android -keypass android

# Mac/Linux
keytool -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore -storepass android -keypass android
```

**For Release Builds:**
```bash
keytool -list -v -keystore path/to/your/release.keystore -alias your_alias_name
```

### Add SHA-1 to Firebase

1. Copy the SHA-1 fingerprint from the keytool output
2. In Firebase Console, go to Project Settings
3. Under "Your apps" section, find your Android app
4. Click "Add fingerprint"
5. Paste the SHA-1 fingerprint
6. Click "Save"
7. Download the updated `google-services.json` and replace the old one

### Enable Google Sign-In

1. In Firebase Console, go to "Build" → "Authentication"
2. Click "Get started"
3. Click on "Google" in the Sign-in providers list
4. Toggle "Enable"
5. Enter project support email
6. Click "Save"

### Get Web Client ID

1. In Firebase Console, go to Project Settings
2. Click on your Android app
3. Copy the "Web client ID" value
4. Update the file `app/src/main/res/values/google_services.xml`:
   ```xml
   <string name="default_web_client_id">YOUR_WEB_CLIENT_ID_HERE</string>
   ```

## Step 6: Verify Setup

1. Clean and rebuild the project:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

2. Check for any errors in the build output

3. Run the app on a device or emulator:
   ```bash
   ./gradlew installSandboxDebug
   ```

## Troubleshooting

### Build Errors

**Error: "File google-services.json is missing"**
- Make sure `google-services.json` is in the `app/` directory
- Sync Gradle files

**Error: "Default FirebaseApp is not initialized"**
- Check that `google-services.json` is properly configured
- Verify the package name matches `com.tamarind.drinks`

### Google Sign-In Issues

**Error: "Developer Error" or Sign-In fails**
- Verify SHA-1 fingerprint is added to Firebase
- Ensure you're using the correct Web Client ID
- Check that Google Sign-In is enabled in Firebase Console

**Error: "API not enabled"**
- Go to Google Cloud Console
- Enable "Google Sign-In API"

### FCM Issues

**Notifications not received**
- Check that FCM is enabled in Firebase Console
- Verify the `google-services.json` is up to date
- Test on a real device (emulators may have issues with FCM)

## Testing Firebase Features

### Test Google Sign-In

1. Run the app
2. Click "Continue with Google" on login screen
3. Select a Google account
4. Verify successful login

### Test Push Notifications

1. In Firebase Console, go to "Build" → "Cloud Messaging"
2. Click "Send your first message"
3. Enter notification title and text
4. Select your app
5. Send the notification
6. Verify it appears on your device

## Production Setup

For production builds:

1. Generate a release keystore:
   ```bash
   keytool -genkey -v -keystore release.keystore -alias tamarind -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Get the SHA-1 for the release keystore and add it to Firebase

3. Download the updated `google-services.json`

4. Update production environment variables in `app/build.gradle.kts`

## Security Notes

- Never commit `google-services.json` to version control (it's already in `.gitignore`)
- Keep your API keys and secrets secure
- Use different Firebase projects for development and production
- Restrict API keys in Google Cloud Console

## Additional Resources

- [Firebase Android Documentation](https://firebase.google.com/docs/android/setup)
- [Google Sign-In Android](https://developers.google.com/identity/sign-in/android/start)
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging)
- [Crashlytics Setup](https://firebase.google.com/docs/crashlytics/get-started)
