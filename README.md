# GitHub User Search App

A cross-platform application developed using Kotlin Multiplatform with shared UI components via Compose Multiplatform.
This app enables users to search for GitHub users or organizations and view the results in a list format.
The app handles different states such as empty, loading, error, and offline mode.

---

## Features

### **Single Screen Search**
- A search bar at the top to input user or organization names.
- Initiates the search query when the user stops typing for more than 2 seconds.

### **Search Results**
- Displays results in a scrollable list format.
- Each card contains:
  - **Avatar Image**
  - **Name or Username**
  - **Number of Repositories**

### **States Handled**
- **Empty State**:
  - Displays a screen informing users that no results are available for the query.
- **Loading State**:
  - Displays a placeholder screen resembling the final data layout, but with shimmering placeholders 
  - where content (e.g., text, images) would normally appear.
- **Error State**:
  - Shows an error message when the app fails to fetch results.
- **Offline Mode**:
  - Fetches and displays previously searched results from a local database when no internet connection is available.
  - Notifies users that the displayed results are offline.
- **Search Results**:
  - Displays live results from the GitHub API when data is fetched successfully.

---

## Architecture

The app uses the **Model-View-Intent (MVI)** architectural pattern.

### **MVI Architecture Overview**
- **Model**: Represents the app's state.
- **View**: Renders the UI based on the model's state.
- **Intent**: Captures user actions and transforms them into state updates.

### **MVI Architecture Diagram**


        +------------------+     +------------------+     +------------------+
        |  User Actions    | --> |      Intent      | --> |      Model       |
        +------------------+     +------------------+     +------------------+
                 ^                                              |
                 |                                              |
                 +----------------------------------------------+
                                  Updates View


## Technologies Used

| **Component**            | **Framework/Library**      |
|--------------------------|----------------------------|
| **UI**                  | Compose Multiplatform      |
| **Network**             | Ktor                       |
| **API**                 | GitHub GraphQL             |
| **Dependency Injection**| Koin                       |
| **Image Loading**       | Coil                       |
| **Database**            | Room                       |
| **Testing**             | assertk                    |

---

## Setup Instructions

### **Prerequisites**
A **GitHub API Token** is required for the app to fetch data from the GitHub GraphQL API.

# Generating a GitHub Personal Access Token (Classic)

Follow these steps to generate a GitHub Personal Access Token (classic) for use with the GitHub GraphQL API.

## Steps to Generate a Token

1. **Log in to GitHub**:
  - Go to [GitHub](https://github.com) and log in to your account.

2. **Navigate to Developer Settings**:
  - Click on your profile picture in the top-right corner.
  - Select **Settings** from the dropdown menu.
  - Scroll down and click on **Developer settings** in the left-hand sidebar.

3. **Access Personal Access Tokens**:
  - Click on **Personal access tokens**.
  - Select **Tokens (classic)** from the options.

4. **Generate a New Token**:
  - Click the **Generate new token (classic)** button.

5. **Provide Token Details**:
  - **Note**: Add a descriptive note for your token (e.g., `GitHub GraphQL API Token`).
  - **Expiration**: Set the expiration period for your token. A shorter expiration time is recommended for security.
  - **Scopes**: Select the required scopes:
    - `public_repo`: Grants access to public repositories.
    - `read:org`: Grants access to organization data (if applicable).

6. **Generate the Token**:
  - Click **Generate token**.

7. **Copy the Token**:
  - Copy the token immediately. You will not be able to view it again after leaving the page.


### Configure the API Token

1. **For Android**:
  - Open the `local.properties` file.
  - Paste your github authentication token:
    ```properties
    GITHUB_API_TOKEN=<your-github-api-token>
    ```

2. **For iOS**:
  - Open the `Config.xcconfig` file.
- Paste your github authentication token:
    ```properties
    GITHUB_API_TOKEN=<your-github-api-token>
    ```
---

### Run the Project

1. **Android**:
  - Open the project in **Android Studio**.
  - Connect an emulator or a physical device.
  - Select run configuration: `composeApp`
  - Click the `Run` button to launch the app.

2. **iOS**:
  - Open the iOS project in **Xcode**.
  - Select a simulator or a physical device. 
  - Select run configuration: `iosApp`
  - Click the `Run` button to launch the app.
---

### Running Tests

This project includes a representative test suite that combines **UI tests** and **unit tests**

## Running the Tests

To execute the tests for this project, from you terminal (Android Studio's terminal) use the following command:

 ```./gradlew :composeApp:iosSimulatorArm64Test```


## Test Types

### Unit Tests
- Validate individual components, classes, and functions.

### UI Tests
- Ensure correct rendering of screen states for scenarios like loading, error, and offline states.

## How I Think Important Classes Could Be Tested using integration tests

- **SearchListViewModel**:
  - Simulate user interactions such as typing in the search bar and verify the state updates correctly.
  - Test the logic for handling loading, empty, and error states.

- **AccountRepository**:
  - Test its ability to fetch data from remote sources and fall back to local data in offline scenarios.
  - Validate that the correct data is cached for future use.

- **UI Components**:
  - Verify that the search bar reacts to user input and triggers the appropriate actions.
  - Ensure that the list of search results is displayed correctly, including avatar images, usernames, and repository counts.
  - Validate the correct rendering of special states such as loading, empty, and error screens.