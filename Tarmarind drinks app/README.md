# Tamarind Drinks Android App

A production-ready Android e-commerce application for Tamarind Drinks built with Kotlin and Jetpack Compose.

## Features

✅ **User Authentication**
- Email/Password authentication
- Google Sign-In
- Guest checkout
- Password reset

✅ **Product Browsing**
- Category-based navigation
- Product search with suggestions
- Detailed product information
- Reviews and ratings

✅ **Shopping Experience**
- Shopping cart with item management
- Coupon code support
- Multiple delivery options
- Address management

✅ **Payment Integration**
- Stripe (Credit/Debit cards)
- Google Pay
- PayPal
- M-PESA (Kenya)

✅ **Order Management**
- Order history
- Real-time order tracking
- Order status updates

✅ **Push Notifications**
- Firebase Cloud Messaging
- Order update notifications
- Promotional offers

✅ **Admin Features**
- Product management
- Order management
- Status updates

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material3
- **Architecture**: MVVM + Repository Pattern
- **DI**: Hilt
- **Database**: Room
- **Networking**: Retrofit + OkHttp
- **Image Loading**: Coil
- **Payments**: Stripe SDK, PayPal SDK, Google Pay
- **Push Notifications**: Firebase Cloud Messaging
- **Analytics & Crash Reporting**: Firebase Analytics & Crashlytics

## Project Structure

```
app/src/main/java/com/tamarind/drinks/
├── data/
│   ├── local/          # Room database, DAOs, entities
│   ├── remote/         # Retrofit services, DTOs
│   └── repository/     # Repository implementations
├── di/                 # Hilt modules
├── ui/                 # Compose screens and components
│   ├── theme/          # Material3 theme
│   ├── navigation/     # Navigation setup
│   ├── auth/           # Authentication screens
│   ├── home/           # Home screen
│   ├── products/       # Product screens
│   ├── cart/           # Cart screen
│   ├── checkout/       # Checkout flow
│   ├── orders/         # Order screens
│   ├── profile/        # Profile screens
│   └── admin/          # Admin screens
├── utils/              # Utility classes
└── service/            # Background services
```

## Setup Instructions

### Prerequisites

- Android Studio Otter (or later)
- JDK 17
- Android SDK (API 21+)

### Clone and Build

```bash
git clone <repository-url>
cd "Tarmarind drinks app"
```

Open the project in Android Studio and sync Gradle.

### Configuration

#### 1. Firebase Setup

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app with package name `com.tamarind.drinks`
3. Download `google-services.json` and place it in `app/` directory
4. Enable Firebase Cloud Messaging, Crashlytics, and Analytics

#### 2. Google Sign-In

1. In Firebase Console, enable Google Sign-In under Authentication
2. Get your Web Client ID from Firebase
3. Update the value in `app/src/main/res/values/google_services.xml`

#### 3. Payment Provider Keys

Update the following in `app/build.gradle.kts`:

**Stripe:**
- Sandbox: `pk_test_your_key_here`
- Production: `pk_live_your_key_here`

**PayPal:**
- Sandbox: Client ID from [PayPal Developer](https://developer.paypal.com/)
- Production: Production Client ID

**M-PESA:**
- Get credentials from [Safaricom Daraja](https://developer.safaricom.co.ke/)
- Update Consumer Key, Consumer Secret, Passkey, and Shortcode

### Build Variants

The app has two product flavors:

- **sandbox**: Development environment with test payment providers
- **production**: Production environment with live payment providers

Build commands:
```bash
# Debug build with sandbox
./gradlew assembleSandboxDebug

# Release build for production
./gradlew assembleProductionRelease
```

### Signing Configuration

For release builds, create a keystore:

```bash
keytool -genkey -v -keystore keystore/release.keystore -alias tamarind -keyalg RSA -keysize 2048 -validity 10000
```

Set environment variables:
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

## Running the App

1. Connect an Android device or start an emulator
2. Select the build variant in Android Studio
3. Click Run

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumentation Tests
```bash
./gradlew connectedAndroidTest
```

## Mock Server

The app includes a mock API server for development without a backend. To enable:

1. Set `USE_MOCK_SERVER = true` in BuildConfig (already set for debug builds)
2. The app will use local JSON responses

See [Mock Server Documentation](docs/MockServer.md) for details.

## API Documentation

API contracts are defined in the Postman collection: `docs/Tamarind_API.postman_collection.json`

OpenAPI specification: `mock-server/api-spec.yaml`

## Deployment

### Google Play Store

1. Generate a signed AAB:
   ```bash
   ./gradlew bundleProductionRelease
   ```

2. The AAB will be in `app/build/outputs/bundle/productionRelease/`

3. Upload to Play Console

### Requirements
- Privacy Policy URL
- App screenshots (phone & tablet)
- Feature graphic (1024x500)
- App description

## Security

- All API communication uses HTTPS
- JWT tokens stored in encrypted SharedPreferences
- No raw payment card data stored
- ProGuard enabled for release builds
- Certificate pinning recommended for production

## Troubleshooting

### Build Issues

**Gradle sync fails:**
- Ensure you're using Android Studio Otter or later
- Check that JDK 17 is set in Project Structure
- Sync project with Gradle files

**Missing google-services.json:**
- Download from Firebase Console
- Place in `app/` directory

### Runtime Issues

**Google Sign-In not working:**
- Check SHA-1 fingerprint is added to Firebase
- Verify Web Client ID is correct

**Payment errors:**
- Verify API keys are correct for the environment
- Check network connectivity
- Review logs for detailed error messages

## Contributing

This is a production application. For contributions, please:

1. Create a feature branch
2. Write tests for new features
3. Ensure all tests pass
4. Submit a pull request

## License

Proprietary - Tamarind Drinks © 2024

## Support

For issues or questions:
- Email: support@tamarinddrinks.com
- Documentation: docs/

---

**Status**: 🚧 In Development

**Version**: 1.0.0-alpha

**Last Updated**: November 28, 2024
