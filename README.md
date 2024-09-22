# Mobile App - Kuliahku

Kuliahku is a mobile-based application designed to help students manage their class schedules. The application allows users to easily create, update, and delete class schedules. Additionally, it features a page to display class schedules by day, making it easier for users to view their academic activities for the specific day directly. This mobile application is built using Android Studio with Java as the programming language and Firebase Firestore as the database.

## Tech Stack

- **Java** - The main programming language for this application.
- **Firebase Firestore** - The database used to store and retrieve data in real-time.
- **Firebase Authentication** - The database used to store user data on the Login and Register pages.

## Features

- Main features available in this application:
  - Splash screen.
  - Authentication using Firebase Authentication.
  - Real-time data sync using Firebase Firestore.

## Installation

Follow the steps below to clone and run the project in your local environment:

1. Clone repository:

    ```bash
    git clone https://github.com/Akbarwp/UAS_Mobile.git
    ```

2. Open project in Android Studio

3. Firebase Configuration:
    - Open [Firebase Console](https://console.firebase.google.com/).
    - Create new project and add Android application.
    - Download file `google-services.json` from Firebase Console and place it in the directory `app/` in your Android Studio project.

4. Add Firebase dependencies in the `build.gradle` file:

    ```gradle
    // Firebase Firestore
    implementation 'com.google.firebase:firebase-firestore:23.0.3'

    // Firebase Authentication
    implementation 'com.google.firebase:firebase-auth:21.0.1'
    ```

5. Synchronize the project with Gradle to download all dependencies.

6. Run the application on an emulator or your android device.

## Firebase Firestore Setup

1. Open Firebase Console and choose Firestore Database.
2. Click "Start Collection" and create collection:

   - **user**: for store user data. Columns used:
       - email
       - foto
       - github
       - nama
       - nim
   - **daftar_kuliah**: Untuk menyimpan data mata kuliah. Columns used:
       - hari
       - jamAkhir
       - JamAwal
       - kelasMK
       - namaMK

### Authentication

- **Login:** Firebase Authentication used for login.
  - Endpoint internal: `FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)`
  - Firebase handles authentication and session management.

## Screenshots

- Login Page

<img src="https://github.com/user-attachments/assets/942e772d-34c5-4121-85b0-834d948b21cf" alt="Halaman Login" width="300" />

- Register page

<img src="https://github.com/user-attachments/assets/959c72f4-cef5-4534-9325-9214ded952c9" alt="Halaman Register" width="300" />

- Today's class schedule page

<img src="https://github.com/user-attachments/assets/52099b7a-c084-41aa-bae8-5ff1175a4586" alt="Halaman Jadwal Kuliah Hari Ini" width="300" />

- Class schedule page

<img src="https://github.com/user-attachments/assets/3ab9b664-5e39-41d1-88e1-2a9cd532eaa3" alt="Halaman Daftar Jadwal Kuliah" width="300" />

- Create class schedule page

<img src="https://github.com/user-attachments/assets/c0bea87f-49a6-4af8-804e-ff736f988f9c" alt="Halaman Tambah Daftar Jadwal Kuliah" width="300" />

- Update class schedule page

<img src="https://github.com/user-attachments/assets/203f28c2-5ca7-42ae-8f16-8414ff07869b" alt="Halaman Ubah Daftar Jadwal Kuliah" width="300" />

- Delete class schedule page

<img src="https://github.com/user-attachments/assets/577307b2-f515-4c67-9a6d-6cdfd4b9822c" alt="Halaman Hapus Daftar Jadwal Kuliah" width="300" />

- About Me Page

<img src="https://github.com/user-attachments/assets/2ac17168-77af-4c8b-8169-ccd6c47eff09" alt="Halaman About Me" width="300" />
