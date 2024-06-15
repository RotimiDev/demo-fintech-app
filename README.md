# Fintech Android App

## Project Overview

A demo Android app to demonstrate fintech services, showcasing key functionalities without backend integration. It simulates money transfers between different accounts within the app, serving as an assessment of authentication handling, data management, etc.

## Features

### 1. User Authentication

- A basic sign up and login screen with email/password authentication using Firebase.

### 2. Account Management

- A simulated list of user accounts with mock data.
- A display of these accounts in a lazy colunm with balance and bank details.

### 3. Transfer Interface

- A form for users to input transfer details (source account, destination account, and amount).
- Validate that the transfer amount doesn't exceed the source account balance.
- Show a summary of transfer details before confirming.
- Update account balances after a successful transfer.

### 4. Transaction History

- Maintain a local transaction history using Room database.
- Display past transactions in a list.

### 5. UI/UX

- No Figma design was used; everything was created based on familiarity with fintech apps.
- Feedback for actions (error/success messages).

## Technical Details

- **Architecture:** MVVM (Model-View-ViewModel)
- **Dependency management:** Version catalog
- **Programming Language:** Kotlin, Compose for UI
- **Libraries:**
  - Firebase Authentication
  - Material3
  - RecyclerView for Compose (Lazy list)
  - Room
  - LiveData
  - ViewModel
  - **IDE:** Android Studio Hedgehog | 2023.1.1 Patch 2

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Run the application on an Android device or emulator.
4. Create an account or if already have an account, just sign in and continue.
