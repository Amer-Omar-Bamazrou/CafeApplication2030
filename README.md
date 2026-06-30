


## 📖 Overview ☕ Café Application 2030

The **Café Application 2030** is a premium mobile solution for café owners and customers. It delivers a **luxury-themed** user experience that streamlines menu browsing, order placement, and real‑time management. Built with Kotlin and Firebase, it bridges traditional café services with modern digital convenience.

### 🎯 Purpose
- **For Customers**: Explore a beautifully curated menu, customise your order, track status in real time, and leave feedback.
- **For Administrators**: Manage inventory, process incoming orders, and respond to customer reviews – all from a dedicated dashboard.

---

## 👥 Contributors

- **Amer Omar Bamazrua** – [GitHub](https://github.com/Amer-Omar-Bamazrou)
- **HH-WB** – [GitHub](https://github.com/HH-WB)



---

## ✨ Features

| **Customer Features**            | **Admin Features**                |
|----------------------------------|-----------------------------------|
| ✅ Secure sign‑up / login       | ✅ Admin dashboard with analytics |
| ✅ Browse & search menu items   | ✅ Full CRUD for menu products    |
| ✅ Add/remove items from cart   | ✅ View live customer orders      |
| ✅ Place orders & track status  | ✅ Manage order fulfilment        |
| ✅ View order history            | ✅ Monitor and reply to feedback  |
| ✅ Submit product ratings & reviews | ✅ User management (future)   |

- **Luxury UI/UX**: Custom brown‑and‑gold palette, rounded cards, and smooth animations.
- **Real‑time sync**: Firebase Firestore and Realtime Database keep data fresh across all devices.

---

## 🛠️ Tech Stack

| Layer              | Technology                             |
|--------------------|----------------------------------------|
| **Language**       | Kotlin                                 |
| **Architecture**   | MVVM (ViewModel + LiveData)            |
| **UI**             | Material Design 3, ViewBinding, XML    |
| **Backend**        | Firebase Authentication, Firestore, Realtime Database |
| **Image Loading**  | Glide                                  |
| **Build System**   | Gradle (Kotlin DSL)                    |

### Dependencies (highlights)
```kotlin
// Firebase
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
implementation("com.google.firebase:firebase-database-ktx")

// Lifecycle & MVVM
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
implementation("androidx.lifecycle:lifecycle-livedata-ktx")

// UI & Image
implementation("com.google.android.material:material")
implementation("com.github.bumptech.glide:glide")
Full list is available in build.gradle.kts.

📁 Project Structure
text
app/src/main/java/com/example/cafeshopapplication/
├── data/
│   ├── model/                # Data classes: Product, Order, User, Feedback
│   ├── repository/           # Firebase communication (Firestore, RTDB)
│   └── CartRepository.kt
├── ui/
│   ├── auth/                 # Welcome, Login, SignUp Activities/ViewModels
│   ├── menu/                 # Menu list, detail, and search
│   ├── cart/                 # Cart management and checkout
│   ├── order/                # Order placement, status, and history
│   ├── feedback/             # Submit feedback & view reviews
│   └── admin/                # Admin dashboard, product CRUD, orders overview
├── utils/                    # Helpers, extensions, constants
└── MainActivity.kt           # Entry point

res/
├── layout/                   # XML screens
├── drawable/                 # Custom luxury assets (gradients, rounded cards, logos)
├── values/                   # Colors, themes, strings (brown/gold palette)
└── ...
🚀 Getting Started
Prerequisites
Android Studio Hedgehog or newer

JDK 11 or higher

Android SDK with minimum API 24 (Android 7.0)

A Firebase project (free tier works)

1. Clone the Repository
bash
git clone https://github.com/Amer-Omar-Bamazrou/CafeApplication2030.git
2. Open in Android Studio
Launch Android Studio → Open an Existing Project.

Select the CafeApplication2030 folder (root of the cloned repo).

Wait for Gradle to sync (it will download dependencies automatically).

3. Configure Firebase
Go to the Firebase Console.

Create a new project (or use an existing one).

Add your Android app (package name: com.example.cafeshopapplication).

Download the google-services.json file.

Place it in the app/ directory (overwrite the placeholder if present).

4. Enable Firebase Services
Authentication: Enable Email/Password sign‑in method.

Firestore: Create a database in test mode (or set up security rules later).

Realtime Database: (optional) if used for live order updates, enable it.

5. Build & Run
Select an emulator (API 24+) or connect a physical device.

Click the green Run button (Shift+F10).

👨‍💻 Using the App
As a Customer
Sign up with email and password.

Browse the Menu – tap any item to view details.

Add items to your cart with desired quantity.

Proceed to checkout – review your order and place it.

Track order status in the Orders section.

After delivery, rate the items and leave feedback.

As an Admin
Use the admin login (if you haven't set up an admin account, create one and set isAdmin = true in Firestore manually, or use the sign‑up flow if implemented).

Once logged in, access the Dashboard to:

Add / Edit / Delete products (name, price, image, description).

View all incoming orders and update their status (e.g., Preparing, Ready, Completed).

Manage customer feedback – reply to reviews.

⚠️ Note: Admin accounts are not created by default. You can either:

Manually set isAdmin: true in the Firestore users collection for a user.

Extend the sign‑up screen to include an admin registration flag (if your implementation allows).

🖼️ Screenshots (Coming Soon)
We plan to add screenshots of the luxury UI – the gold‑accented welcome screen, menu cards, and the admin panel. For now, run the app to experience it firsthand!

🤝 Contributing
We welcome contributions! To get involved:

Fork the repository.

Create a feature branch:
git checkout -b feature/your-feature-name

Commit your changes:
git commit -m "Add some feature"

Push to your branch:
git push origin feature/your-feature-name

Open a Pull Request describing your changes.

Please ensure your code follows the existing style and includes appropriate tests.
