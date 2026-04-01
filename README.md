# LeetCoding

**Personal LeetCode Progress Tracker (Android App)**

LeetCoding is an Android application designed to help developers track their LeetCode journey efficiently. It allows users to store solved problems along with their code, descriptions, and difficulty levels — all in a clean and accessible interface.

---

## Key Features

### 📚 Problem Tracker
- Add solved LeetCode questions
- Store title, description, and code
- Maintain a personal problem archive

### Difficulty Management
- Easy 🟢
- Medium 🟠
- Hard 🔴
- Visual indicators using colored dots

### Clean UI Experience
- RecyclerView + CardView based UI
- Minimal and distraction-free design
- Smooth navigation between screens

### Detailed View
- Tap any question to:
  - View full description
  - View stored code
  - Review your approach anytime

### Theme Support
- Light Mode (White + Green)
- Dark Mode (Black + Green)

---

## Architecture
Android (Java)
├── UI Layer (Activities + XML Layouts)
├── Adapter Layer (RecyclerView)
├── Data Layer (Room Database)
└── Utility Classes


---

## Project Structure
app/
├── data/
│ ├── LeetCodeDao.java
│ ├── LeetCodeDatabase.java
│ ├── LeetCodeEntry.java
│ └── QuestionDifficulty.java
│
├── ui/
│ ├── MainActivity.java
│ ├── QuestionDetailActivity.java
│ ├── SplashActivity.java
│ ├── LeetCodeAdapter.java
│ ├── ThemePreferenceManager.java
│ └── UiUtils.java
│
├── res/
│ ├── layout/
│ ├── drawable/
│ └── values/


---

## Tech Stack

### Android
- Java
- Android SDK
- XML (UI Design)

### Architecture
- RecyclerView
- CardView
- Room Database

### Tools
- Android Studio
- Gradle

---

## How It Works

1. Solve a problem on LeetCode  
2. Open LeetCoding app  
3. Add:
   - Title  
   - Description  
   - Code  
   - Difficulty  
4. View all problems in a structured list  
5. Tap to revisit solutions anytime  

---

## Future Improvements

-  Search functionality  
-  Progress statistics (Easy/Medium/Hard count)  
-  Cloud sync (Firebase)  
-  Tags (DP, Graph, Arrays)  
-  Share progress feature  

---

##  Purpose

This project was built to:
- Track DSA progress consistently  
- Reduce dependency on LeetCode login  
- Showcase Android development skills  
- Maintain a personal coding journal  

---

## 📸 Screenshots

### 🏠 Home Screen
![Home](https://github.com/user-attachments/assets/107b0551-631a-4893-a975-739c952f2080)


### ➕ Add Question
![Add](https://github.com/user-attachments/assets/0421c858-b35a-4853-9097-772725788e8c)


### 📄 Detail Screen
![Detail](https://github.com/user-attachments/assets/d66861de-2cff-49b8-ad64-09534db626fb)


---

##  Note

This app stores data locally using Room Database and does not require internet access.

---



