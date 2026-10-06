# Greek Citizenship Quiz 🇬🇷

**Greek Citizenship Quiz** is an Android application designed to help users prepare for the Greek citizenship knowledge examination through practice questions and interactive quizzes.

The application is built with **Kotlin and Jetpack Compose**, with a **Django REST Framework** backend used for content management and synchronization.

## Features

* 📝 **750+ practice questions**
* 📚 **300 topics**
* ❓ **9 question types**
* 🎯 **20-question random tests**
* 📖 Practice by category
* 📖 Practice by type
* ❌ Review incorrect answers
* 🔊 Text-to-Speech support
* 📱 Offline functionality
* 🔄 Content synchronization with the backend
* 🗺️ Questions covering Geography, History, Culture, and Institutions of the Greek polity

## Technologies

### Android

* Kotlin
* Jetpack Compose
* Material 3
* Room
* Hilt
* Retrofit
* Moshi
* OkHttp
* Navigation Compose
* Coil
* Kotlin Symbol Processing (KSP)

### Backend

* Python
* Django
* Django REST Framework
* PostgreSQL
* Redis

The backend provides content management and synchronization with the Android application, while the application maintains offline functionality.

## Architecture

The project consists of two main components:

```text
Android Application
        │
        │ Django REST API
        ▼
Django REST Backend
        │
        ▼
PostgreSQL Database
```

The Android application uses a prepackaged local Room database and can operate offline. When an internet connection is available, the application can synchronize updated content from the backend.

## Disclaimer

This application is an **unofficial educational project** and is not affiliated with, connected to, or endorsed by any official Greek government authority.

It is intended solely for educational and preparation purposes.

## Google Play

The application is available on **Google Play**.

[Download Greek Citizenship Quiz on Google Play](https://play.google.com/store/apps/details?id=com.aris.greekcitizenshipquiz)

## License

Copyright (c) 2026 Aris Dulla.

All Rights Reserved.

See the [`LICENSE`](LICENSE) file for the full license terms.

## Author

**Aris Dulla**


.