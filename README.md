# Karunada-Kala

Karunada-Kala is a cultural discovery and preservation platform for Karnataka traditional arts.

## Features
- **Splash Screen**: Animated entry with cultural visuals.
- **Home Dashboard**: Quick access to Art Forms, Artisans, Events, and Products.
- **Art Form Explorer**: Discover Karnataka's rich heritage with offline support (Room).
- **Art Form Detail**: Detailed history, region info, and video demonstrations using ExoPlayer.
- **Artisan Map**: Interactive Google Maps integration showing workshops and performances.
- **Artisan Profile**: Connect with artisans via "Tap to Call" and profile sharing.
- **Workshop Sign-up**: Register for traditional art workshops.
- **Event Feed**: Real-time updates on upcoming cultural events.
- **Marketplace**: Browse authentic Karnataka products like Bidriware and Ilkal Sarees.

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM with Repository Pattern
- **Database**: Room (Local Caching), Firebase Firestore (Cloud)
- **Maps**: Google Maps SDK
- **UI**: Material Design 3, ViewBinding, Navigation Component
- **Media**: Media3 ExoPlayer for video playback
- **Image Loading**: Coil
- **Concurrency**: Coroutines & Flow

## Setup Instructions
1. **Firebase Setup**:
   - Create a project in [Firebase Console](https://console.firebase.google.com/).
   - Add an Android app with package name `com.example.karunada_kala`.
   - Download `google-services.json` and place it in the `app/` directory.
   - Enable **Firestore Database**.
2. **Google Maps API**:
   - Get an API Key from [Google Cloud Console](https://console.cloud.google.com/).
   - Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` in `AndroidManifest.xml`.
3. **Firestore Collections**:
   - `artforms`: {id, name, description, region, videoUrl, imageUrl}
   - `artisans`: {id, name, artForm, phone, latitude, longitude, type, description, photoUrl}
   - `events`: {id, title, artForm, date, location, latitude, longitude}
   - `signups`: (Created automatically on form submission)

## Sample Data (Seed Firestore)
Add these documents to `artforms`:
- `yakshagana`: { name: "Yakshagana", region: "Coastal Karnataka", ... }
- `dollu_kunitha`: { name: "Dollu Kunitha", region: "North Karnataka", ... }
- `channapatna`: { name: "Channapatna Toys", region: "Ramanagara", ... }
