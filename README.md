# Fintech Android App

## Project Overview

A demo Android app to demonstrate fintech services, showcasing key functionalities without backend integration. It simulates money transfers between different accounts within the app, serving as an assessment of authentication handling, data management, etc.

## Features

### 1. User Authentication

- A basic sign-up and login screen with email/password authentication using Firebase.

### 2. Account Management

- A simulated list of user accounts with mock data.
- A display of these accounts in a lazy column with balance and bank details.

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
- **IDE:** Android Studio Hedgehog | 2023.1.1 Patch 2

- **Libraries:**
  - Firebase Authentication
  - Material3
  - RecyclerView for Compose (Lazy list)
  - Room
  - LiveData
  - ViewModel

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Run the application on an Android device or emulator.
4. Create an account or if you already have one, sign in and continue.

## Screenshots

![Screenshot_20240615_200256](https://github.com/RotimiDev/demo-fintech-app/assets/92338525/d8870b6a-e685-4a3d-8ec4-990c21f0bbe0)
![Screenshot_20240615_200416](https://github.com/RotimiDev/demo-fintech-app/assets/92338525/0ac3b3eb-9853-48ae-b8ae-62a3b196ca23)
<img width="207" alt="Screenshot 2024-06-15 at 6 38 05 PM" src="https://github.com/RotimiDev/demo-fintech-app/assets/92338525/b8324022-6b7a-4c55-bce4-105d837615a6">
![Screenshot_20240615_200440](https://github.com/RotimiDev/demo-fintech-app/assets/92338525/42d0828c-d5ed-4d3b-a365-8ae84821c15e)
![Screenshot_20240615_200510](https://github.com/RotimiDev/demo-fintech-app/assets/92338525/f034610f-32d8-4d07-9976-f853eaf26d06)
![Screenshot_20240615_200523](https://github.com/RotimiDev/demo-fintech-app/assets/92338525/16218d58-6978-434f-8664-911a32d5419f)

