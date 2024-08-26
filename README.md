# UserManager Android Application

This project is an Android application built to manage user data using the MVVM architecture pattern. The app fetches user data from the ReqRes API, stores it locally using Room, and displays it in a RecyclerView. It also provides full CRUD (Create, Read, Update, Delete) functionality.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the App](#running-the-app)
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
- Minimum SDK level: 30
- Internet access to fetch data from the ReqRes API

## Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/NissanYam/UserManager.git
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


## Known Issues
- The app currently does not support pagination for large datasets.
- Internet connectivity is required for the initial data fetch; offline functionality is limited to cached data.

## Future Enhancements
- Implement user authentication and registration.



## Assumptions and Challenges

### Assumptions

1. **API Availability**: It is assumed that the ReqRes API is available and functional throughout the development and use of this application. Any changes or downtime in the API may affect the functionality of the app.
   
2. **Java Version**: The application assumes that Java 11 is installed and properly configured in the development environment. This is required for building and running the application.

3. **Android SDK Version**: The app assumes that the Android SDK API level 30 or higher is installed. This is necessary to ensure compatibility with the features and dependencies used in the project.

4. **Device Compatibility**: It is assumed that users will be running the application on devices or emulators that support the minimum SDK version (API level 30) specified in the `build.gradle` file.

5. **Internet Connection**: The app assumes an active internet connection for fetching user data from the ReqRes API. Offline functionality is not supported in the current version.

### Challenges

1. **API Integration**: Integrating Retrofit with the ReqRes API involved handling various API responses and ensuring that the data is correctly mapped to the model classes. Dealing with different data formats and handling errors were significant challenges.

2. **Room Database Setup**: Setting up the Room database and ensuring smooth data persistence and retrieval required careful design of the entity and DAO interfaces. Ensuring that data integrity was maintained across different operations posed a challenge.

3. **RecyclerView Performance**: Implementing and optimizing the RecyclerView for smooth performance involved dealing with large datasets and ensuring that the UI remained responsive. Efficient view recycling and data binding were key considerations.

4. **MVVM Architecture**: Adhering to the MVVM architecture while ensuring proper communication between ViewModel, LiveData, and UI components was challenging. It required careful planning to ensure that the application remained maintainable and scalable.

5. **Image Loading**: Integrating Glide for image loading and caching required handling various image formats and ensuring that images were displayed correctly without impacting performance.

These assumptions and challenges reflect the considerations and obstacles encountered during the development of the UserManager application. Addressing these challenges required thoughtful design decisions and careful implementation to ensure a functional and user-friendly application.

## APK File

You can download the APK file from the following location:

- **[Download UserManager APK](https://github.com/NissanYam/UserManager/releases/latest/download/UserManager.apk)**
