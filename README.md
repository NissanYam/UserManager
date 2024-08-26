# UserManager Android Application

This project is an Android application built to manage user data using the MVVM architecture pattern. The app fetches user data from the ReqRes API, stores it locally using Room, and displays it in a RecyclerView. It also provides full CRUD (Create, Read, Update, Delete) functionality.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the App](#running-the-app)
- [Project Structure](#project-structure)
- [Known Issues](#known-issues)
- [Future Enhancements](#future-enhancements)

## Features
- Fetch user data from the ReqRes API
- Display user data in a RecyclerView
- Local data storage using Room Database
- CRUD operations on user data
- MVVM architecture
- UI/UX enhancements

## Technologies Used
- **Java 11**
- **MVVM Architecture**
- **Retrofit** for API calls
- **Room** for local database
- **RecyclerView** for displaying data
- **LiveData** and **ViewModel** for data handling
- **AndroidX** Libraries

## Requirements
- Android Studio Flamingo | 2022.2.1 Patch 2 or higher
- Java 11
- Gradle 8.0.0 or higher
- Minimum SDK level: 21 (Android 5.0)
- Internet access to fetch data from the ReqRes API

## Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/UserManager.git
    ```

2. **Open the project in Android Studio:**
    - Launch Android Studio.
    - Select `Open an Existing Project`.
    - Navigate to the directory where you cloned the repository and select the `UserManager` folder.

3. **Sync Gradle:**
    - Once the project is loaded, Android Studio will automatically sync the Gradle files.
    - If it does not, click on `File > Sync Project with Gradle Files`.

## Running the App

1. **Build the project:**
    - In Android Studio, go to `Build > Make Project`, or use the shortcut `Ctrl+F9`.

2. **Run the app on an emulator or physical device:**
    - Select an emulator or a connected physical device from the device dropdown.
    - Click on the `Run` button, or use the shortcut `Shift+F10`.

3. **Testing CRUD operations:**
    - The app will fetch user data from the ReqRes API and display it in the RecyclerView.
    - You can add, update, or delete user data using the UI.
## Project Structure

The project is organized into the following packages:

```plaintext
com.yourusername.usermanager
│
├── data
│   ├── api                # Retrofit setup and API service
│   ├── db                 # Room database setup and DAO interfaces
│   ├── model              # Data models (e.g., User class)
│   └── repository         # Repositories for data handling
│
├── ui
│   ├── adapter            # RecyclerView adapters
│   ├── viewmodel          # ViewModels for data management
│   └── activity           # UI activities
│
└── utils                  # Utility classes



## Known Issues
- The app currently does not support pagination for large datasets.
- Internet connectivity is required for the initial data fetch; offline functionality is limited to cached data.

## Future Enhancements
- Add pagination support for handling large datasets efficiently.
- Improve error handling for network issues.
- Implement user authentication and registration.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
