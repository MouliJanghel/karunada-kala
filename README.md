# Karunada-Kala 🎭
### Karnataka Cultural Discovery & Preservation App

Karunada-Kala is a modern Android mobile application built using **Kotlin** and **Android Studio** that helps users discover, explore, and preserve Karnataka’s traditional art forms and cultural heritage.

The app connects users with local artisans, workshops, cultural events, and authentic handmade products through an interactive and visually rich platform powered by Firebase, Google Maps, and modern Android technologies.

---

# 📱 Features

## 🎨 Art Form Explorer
- Browse Karnataka traditional art forms
- View history, descriptions, images, and videos
- Offline access using Room Database caching

## 🗺️ Artisan Map
- Locate artisans and workshops using Google Maps
- Custom markers for:
  - Workshops
  - Performances
  - Product Sellers

## 👤 Artisan Profiles
- Detailed artisan information
- Tap-to-call support using `Intent.ACTION_DIAL`
- View artisan photos and locations

## 📅 Event Feed
- Real-time cultural events from Firebase Firestore
- Upcoming Yakshagana, Dollu Kunitha, and workshop events

## 📝 Workshop Registration
- Register interest in workshops
- Data stored securely in Firebase Firestore

## 🛍️ Product Marketplace
- Explore authentic Karnataka handmade products
- Contact artisans directly

## 🎥 Video Playback
- Embedded cultural performance videos
- ExoPlayer/WebView integration

## 📡 Offline Support
- Art forms cached locally using Room Database

---

# 🏗️ Tech Stack

| Technology | Usage |
|------------|-------|
| Kotlin | Main Programming Language |
| Android Studio | Development Environment |
| Firebase Firestore | Cloud Database |
| Google Maps SDK | Maps & Location |
| Room Database | Offline Storage |
| MVVM Architecture | Clean Architecture |
| Coroutines | Async Operations |
| Navigation Component | Screen Navigation |
| Coil | Image Loading |
| ExoPlayer | Video Playback |
| Material Design 3 | Modern UI |

---

# 📂 Project Architecture

```plaintext
com.karunadakala
│
├── data
│   ├── local
│   ├── remote
│   ├── repository
│
├── domain
│
├── model
│
├── ui
│   ├── splash
│   ├── home
│   ├── artforms
│   ├── artisans
│   ├── maps
│   ├── events
│   ├── products
│   └── signup
│
├── adapters
│
├── utils
│
├── viewmodel
│
└── MainActivity.kt
```

---

# 🎨 UI Theme

The app uses Karnataka cultural colors:

| Color | Hex |
|------|------|
| Karnataka Red | `#C62828` |
| Karnataka Yellow | `#FFCA28` |

Features:
- Material Design 3
- Responsive Mobile UI
- Dark Mode Support
- Smooth Animations
- Modern Card Layouts

---

# 🔥 Firebase Collections

## artforms

```json
{
  "id": "",
  "name": "",
  "description": "",
  "region": "",
  "videoUrl": "",
  "imageUrl": ""
}
```

## artisans

```json
{
  "id": "",
  "name": "",
  "artForm": "",
  "phone": "",
  "latitude": 0.0,
  "longitude": 0.0,
  "type": "WORKSHOP",
  "photoUrl": ""
}
```

## events

```json
{
  "id": "",
  "title": "",
  "artForm": "",
  "date": "",
  "location": "",
  "latitude": 0.0,
  "longitude": 0.0
}
```

---

# 🛠️ Installation

## 1️⃣ Clone Repository

```bash
git clone https://github.com/yourusername/Karunada-Kala.git
```

---

## 2️⃣ Open in Android Studio

- Open Android Studio
- Click **Open Project**
- Select project folder

---

## 3️⃣ Setup Firebase

1. Go to Firebase Console
2. Create a new project
3. Add Android App
4. Download `google-services.json`
5. Paste it inside:

```plaintext
app/google-services.json
```

---

## 4️⃣ Enable Firebase Services

Enable:
- Firestore Database
- Firebase Authentication

---

## 5️⃣ Setup Google Maps API

1. Go to Google Cloud Console
2. Enable Maps SDK for Android
3. Generate API Key
4. Add inside `AndroidManifest.xml`

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY"/>
```

---

# 📦 Required Dependencies

```gradle
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0"
implementation "androidx.navigation:navigation-fragment-ktx:2.7.7"
implementation "androidx.navigation:navigation-ui-ktx:2.7.7"

implementation platform('com.google.firebase:firebase-bom:33.1.0')
implementation 'com.google.firebase:firebase-firestore-ktx'
implementation 'com.google.firebase:firebase-auth-ktx'

implementation 'com.google.android.gms:play-services-maps:19.0.0'

implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"

implementation "io.coil-kt:coil:2.6.0"

implementation "androidx.room:room-runtime:2.6.1"
kapt "androidx.room:room-compiler:2.6.1"

implementation "androidx.media3:media3-exoplayer:1.3.1"
implementation "androidx.media3:media3-ui:1.3.1"
```

---

# 📱 Main Screens

| Screen | Description |
|--------|-------------|
| Splash Screen | App launch screen |
| Home Dashboard | Main navigation hub |
| Art Form Explorer | Browse Karnataka arts |
| Art Form Detail | Full art details & videos |
| Artisan Map | Discover artisans on map |
| Artisan Profile | Detailed artisan info |
| Event Feed | Real-time event updates |
| Workshop Sign-Up | Register for workshops |
| Product Marketplace | Handmade product listings |

---

# 🌟 Key Features

- MVVM Clean Architecture
- Firebase Firestore Integration
- Google Maps Integration
- Offline Caching
- Real-time Event Updates
- Video Playback Support
- Material Design UI
- Responsive Mobile Layout
- Beginner-Friendly Codebase

---

# 🚀 Future Improvements

- Kannada Language Support
- AI-based Cultural Recommendations
- In-App Booking System
- Community Contributions
- Admin Dashboard
- Push Notifications

---

# 👨‍💻 Developed Using

- Android Studio
- Kotlin
- Firebase
- Google Maps SDK
- Material Design 3

---

# 📄 License

This project is created for educational and cultural preservation purposes.

---

# ❤️ Vision

Karunada-Kala aims to preserve and promote Karnataka’s rich cultural heritage digitally by connecting artisans, performers, students, tourists, and art enthusiasts on one unified platform.

---

# 📷 Sample Art Forms

- Yakshagana
- Dollu Kunitha
- Bidriware
- Channapatna Toys
- Ilkal Sarees

---

# 📞 Contact

For collaboration or improvements:

- Developer Name: Your Name
- Email: your@email.com

---

## ⭐ If you like this project, give it a star!
