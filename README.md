# VisitFence – Android Geofencing & Visit Tracking App

## 📌 Project Overview

VisitFence is an **Android native application** built using **Jetpack Compose** and **Room Database** that allows users to create geofenced locations on a map and automatically track visits. Whenever a user **enters or exits** a geofenced area, the app records the visit duration and stores it locally for future reference.

The app is designed to work reliably on **Android 10 (API 29) and above**, following modern Android background location and notification guidelines.

---
## Video Link :-
 https://www.linkedin.com/posts/manash-khatowal-130bb4249_built-visitfence-android-geofencing-activity-7424487833840775168-ucA-?utm_source=share&utm_medium=member_desktop&rcm=ACoAAD2UarQBD2l7XKILTuFmo2kChxJ-oANXLtM

## 🎯 Key Features

* 📍 Create geofences on a Google Map using **long-press / double-tap**
* 📏 Adjustable geofence radius (**10m – 50m**)
* 🔔 Notifications on **entry** and **exit** of geofenced areas
* ⏱️ Automatic calculation of **time spent** inside a location
* 💾 Persistent storage using **Room DB** (survives app kill & reboot)
* 📜 View history of all visits with detailed timing

---

## 🧱 App Architecture

**Architecture Pattern:** MVVM (Model–View–ViewModel)

```
UI (Jetpack Compose)
 ↓
ViewModel
 ↓
Repository
 ↓
Room Database + Geofencing Client
```

---

## 📱 Screens

### Screen 1 – Map View (Geofence Creation)

* Google Maps (Compose)
* Add geofence via long-press or double-tap
* Select radius between 10m–50m
* Display all saved geofences with markers & circles
* Persist geofences across app restarts

### Screen 2 – Geofence List

Displays all created geofences with:

* Location name
* Latitude & longitude
* Radius
* Creation date & time

### Screen 3 – Visits History

Displays visit records with:

* Geofence location name
* Date
* Entry time
* Exit time
* Total duration spent

---

## 🗂️ Data Model

### GeofenceEntity

Stores static geofence data:

* ID
* Name
* Latitude
* Longitude
* Radius
* Created timestamp

### VisitEntity

Stores visit history:

* ID
* Geofence ID
* Entry time
* Exit time
* Duration
* Visit date

---

## 📡 Geofencing Flow

1. User adds a geofence on the map
2. Geofence is registered using `GeofencingClient`
3. Android system monitors transitions
4. On **ENTER**:

   * Notification shown
   * Entry time stored
5. On **EXIT**:

   * Notification shown
   * Duration calculated
   * Visit saved in DB

---

## 🔁 Persistence & Reliability

* All geofences are stored in Room DB
* On app relaunch, geofences are reloaded and displayed
* On device reboot:

  * `BOOT_COMPLETED` receiver re-registers all geofences

---

## 🔔 Permissions Used

```xml
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
ACCESS_BACKGROUND_LOCATION
POST_NOTIFICATIONS
```

> Background location permission is required for geofencing on Android 10+.

---

## 🛠️ Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose + Material 3
* **Maps:** Google Maps SDK + Maps Compose
* **Location:** Play Services Location (Geofencing)
* **Database:** Room (with KSP)
* **Architecture:** MVVM
* **Navigation:** Navigation Compose

---

## ⚙️ Build Configuration

* **Min SDK:** 26
* **Target SDK:** 36
* **Compile SDK:** 36
* **Java Version:** 11

---

## 🧪 Testing Considerations

* Android 10, 11, 12+ background behavior
* Permission denial scenarios
* Location services disabled
* App killed / device reboot

---

## 🚀 Future Improvements

* Foreground service for enhanced reliability
* Editable / removable geofences
* Export visit history
* UI enhancements (filters, search)

---

## ✅ Expected Outcome

* Reliable geofence monitoring
* Accurate visit duration tracking
* Persistent data storage
* Clean, modern UI with Compose

---

## 👨‍💻 Author

**Manash Khatowal**

---

## 📄 License

This project is created for evaluation and learning purposes.
