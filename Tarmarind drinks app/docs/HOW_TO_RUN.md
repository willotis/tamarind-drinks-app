# How to Run the Tamarind Drinks App

This guide will walk you through running the Tamarind Drinks Android app on your local machine.

## Prerequisites

Before running the app, ensure you have the following installed:

### Required Software
- **Android Studio** (Hedgehog 2023.1.1 or later recommended)
- **JDK 17** (comes bundled with Android Studio)
- **Android SDK** (API 21 minimum, API 34 for compilation)

### Download Android Studio
If you don't have Android Studio installed:
1. Download from [https://developer.android.com/studio](https://developer.android.com/studio)
2. Install following the setup wizard
3. During installation, ensure you install:
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device (for emulator)

## Step 1: Open Project in Android Studio

1. Launch Android Studio
2. Click **"Open"** from the welcome screen (or **File → Open** if another project is open)
3. Navigate to `c:\Users\Administrator\Desktop\Tarmarind drinks app`
4. Click **"OK"**

Android Studio will:
- Import the project
- Download necessary Gradle dependencies (this may take a few minutes)
- Index the project files

> **Note**: The first time you open the project, Gradle sync will automatically start. Wait for it to complete.

## Step 2: Generate Gradle Wrapper (If Missing)

If you see Gradle sync errors about missing wrapper, generate it:

1. Open **Terminal** in Android Studio (View → Tool Windows → Terminal)
2. Run:
   ```powershell
   gradle wrapper --gradle-version 8.2
   ```

## Step 3: Set Up Firebase (Required for Google Sign-In)

The app requires Firebase configuration. You have two options:

### Option A: Quick Setup (Mock Firebase - For Testing Only)

Create a placeholder `google-services.json`:

1. In Android Studio, right-click on the `app` folder
2. Select **New → File**
3. Name it `google-services.json`
4. Paste this minimal configuration:

```json
{
  "project_info": {
    "project_number": "123456789",
    "project_id": "tamarind-drinks-dev",
    "storage_bucket": "tamarind-drinks-dev.appspot.com"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:123456789:android:abcdef123456",
        "android_client_info": {
          "package_name": "com.tamarind.drinks"
        }
      },
      "oauth_client": [
        {
          "client_id": "123456789-abcdefghijklmnop.apps.googleusercontent.com",
          "client_type": 3
        }
      ],
      "api_key": [
        {
          "current_key": "AIzaSyDummyKeyForTesting123456789"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}
```

> **Warning**: This is for basic testing only. Google Sign-In won't work. For full functionality, use Option B.

### Option B: Full Firebase Setup (Recommended)

Follow the complete guide in [FIREBASE_SETUP.md](FIREBASE_SETUP.md) to:
- Create a Firebase project
- Download the real `google-services.json`
- Enable Google Sign-In, FCM, and other features

## Step 4: Choose a Build Variant

The app has different build variants:

1. In Android Studio, open **Build Variants** panel (View → Tool Windows → Build Variants)
2. Select one of:
   - **sandboxDebug** - Development build with test payment keys (recommended for first run)
   - **sandboxRelease** - Release build for sandbox testing
   - **productionDebug** - Development with production keys
   - **productionRelease** - Production release build

> **Recommendation**: Use **sandboxDebug** for your first run.

## Step 5: Set Up an Android Device

You need either a physical device or an emulator:

### Option A: Use Android Emulator (Easier)

1. In Android Studio, open **Device Manager** (View → Tool Windows → Device Manager)
2. Click **"Create Device"**
3. Select a phone model (e.g., Pixel 6)
4. Click **Next**
5. Download a system image if needed (recommended: API 34, Android 14)
6. Click **Next**, then **Finish**
7. Start the emulator by clicking the play button

### Option B: Use Physical Android Device

1. Enable **Developer Options** on your device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"

2. Connect your device via USB
3. Accept the USB debugging prompt on your device
4. Your device should appear in Android Studio's device toolbar

## Step 6: Run the App

### Method 1: Using Android Studio (Recommended)

1. Make sure your device/emulator is selected in the device dropdown (top toolbar)
2. Click the green **Run** button (▶) or press `Shift + F10`
3. Wait for the app to build and install
4. The app will automatically launch on your device

### Method 2: Using Gradle Command Line

If the Gradle wrapper is set up:

```powershell
# Navigate to project directory
cd "c:\Users\Administrator\Desktop\Tarmarind drinks app"

# Build and install (Windows)
.\gradlew installSandboxDebug

# Or on Mac/Linux
./gradlew installSandboxDebug
```

Then manually launch the app on your device.

## Step 7: Verify the App is Running

Once launched, you should see:
1. **Splash Screen** - Tamarind Drinks logo
2. **Login Screen** - Email/password login or Google Sign-In

You can:
- Login as guest to browse products
- Use mock data (the app has built-in mock products for testing)

## Troubleshooting

### Gradle Sync Failed

**Issue**: "Plugin [id: '...'] was not found"

**Solution**:
1. Open `settings.gradle.kts`
2. Ensure plugin repositories are configured
3. Click **File → Sync Project with Gradle Files**

### Missing google-services.json

**Issue**: "File google-services.json is missing"

**Solution**:
- Follow Step 3 above to create the file
- Make sure it's in the `app/` directory (not `app/src/`)

### Build Failed - JDK Version

**Issue**: "Unsupported Java version" or similar

**Solution**:
1. Go to **File → Project Structure**
2. Under **SDK Location**, verify JDK is version 17
3. If not, download JDK 17 and select it

### App Crashes on Launch

**Issue**: App installs but crashes immediately

**Possible Causes & Solutions**:
- **Firebase not configured**: Create `google-services.json` (see Step 3)
- **Missing dependencies**: Run **File → Invalidate Caches → Invalidate and Restart**
- **Check Logcat**: In Android Studio, open Logcat (View → Tool Windows → Logcat) to see crash details

### Emulator is Slow

**Solutions**:
- Enable hardware acceleration (HAXM on Windows/Mac, KVM on Linux)
- Allocate more RAM to emulator (Edit AVD → Show Advanced Settings → RAM)
- Use a physical device instead

### Google Sign-In Not Working

**Issue**: "Developer Error" or sign-in fails

**Solution**:
- This is expected with the mock `google-services.json`
- Follow [FIREBASE_SETUP.md](FIREBASE_SETUP.md) for proper Firebase setup
- Add your SHA-1 certificate fingerprint to Firebase
- Update Web Client ID in `app/src/main/res/values/google_services.xml`

## Testing Features

### Without Firebase Setup

With the basic setup, you can test:
- ✅ App navigation and UI
- ✅ Product browsing (mock data)
- ✅ Shopping cart functionality
- ✅ Checkout flow (UI only, payments won't process)
- ✅ Order management screens

### With Full Firebase Setup

With complete Firebase configuration, you can test:
- ✅ All of the above, plus:
- ✅ Google Sign-In
- ✅ Push notifications
- ✅ Crash reporting
- ✅ Analytics

## Build Outputs

After building, find the APK files:
- **Debug APK**: `app/build/outputs/apk/sandbox/debug/app-sandbox-debug.apk`
- **Release AAB**: `app/build/outputs/bundle/productionRelease/app-production-release.aab`

## Next Steps

Once the app is running:
1. ✅ Explore the UI and navigation
2. ✅ Test the shopping flow
3. ✅ Set up Firebase for full functionality (see [FIREBASE_SETUP.md](FIREBASE_SETUP.md))
4. ✅ Configure payment provider keys in `app/build.gradle.kts`
5. ✅ Add real product data (currently using mock data)

## Quick Reference Commands

```powershell
# Clean build
.\gradlew clean

# Build debug APK
.\gradlew assembleSandboxDebug

# Install on connected device
.\gradlew installSandboxDebug

# Run tests
.\gradlew test

# Build release bundle
.\gradlew bundleProductionRelease
```

## Getting Help

If you encounter issues:
1. Check the error message in Logcat
2. Review [README.md](../README.md) for project overview
3. See [FIREBASE_SETUP.md](FIREBASE_SETUP.md) for Firebase issues
4. Clean and rebuild: **Build → Clean Project**, then **Build → Rebuild Project**

---

**Happy Coding! 🎉**
