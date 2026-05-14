# Notes App - Platform-Specific Features
**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB  

---

## Deskripsi

Project ini merupakan pengembangan dari Tugas 7 (Notes App) dengan menambahkan Dependency Injection menggunakan Koin dan Platform-Specific APIs menggunakan pola expect/actual.

Fitur utama yang diterapkan meliputi:
- Dependency Injection dengan Koin — seluruh dependency di-inject secara otomatis
- DeviceInfo dengan expect/actual — menampilkan nama perangkat, versi OS, versi app, dan jenis layar
- NetworkMonitor dengan expect/actual — mendeteksi koneksi internet secara reaktif menggunakan Flow
- BatteryInfo dengan expect/actual — menampilkan level baterai dan status charging
- Network status indicator animasi di main screen saat koneksi terputus
- Device info, status internet, dan status baterai tampil di Settings screen
- Floating bottom navigation dengan desain pill dan shadow

---

## Teknologi & Library

| Library | Versi | Kegunaan |
|---|---|---|
| Kotlin Multiplatform | 2.0.20 | Target Android |
| Compose Multiplatform | 1.6.11 | UI Framework |
| SQLDelight | 2.0.2 | Database lokal |
| Multiplatform Settings | 1.1.1 | Penyimpanan preferensi |
| Koin Core | 3.5.3 | Dependency Injection (commonMain) |
| Koin Android | 3.5.3 | DI untuk Android |
| Koin Compose | 1.0.3 | `koinViewModel` & `koinInject` di Composable |
| Jetpack Navigation Compose | 2.7.0-alpha07 | Navigasi antar screen |
| Lifecycle ViewModel Compose | 2.8.3 | State management |
| kotlinx-datetime | 0.6.0 | Pengelolaan timestamp |

---

## Fitur Aplikasi

### 1. Dependency Injection (Koin)
Seluruh dependency (database, repository, ViewModel, platform services) dikelola Koin. ViewModel di-inject dengan `koinViewModel()` dan platform service dengan `koinInject()`.

### 2. DeviceInfo (expect/actual)
Menampilkan nama perangkat, versi OS, versi aplikasi, dan jenis layar (Smartphone/Tablet) di Settings screen. Implementasi menggunakan `Build.MODEL` dan `Build.VERSION` di Android.

### 3. NetworkMonitor (expect/actual)
Memonitor koneksi internet secara reaktif menggunakan `ConnectivityManager` di Android. Banner merah animasi muncul otomatis di main screen saat perangkat kehilangan koneksi internet.

### 4. BatteryInfo (expect/actual)
Menampilkan persentase baterai dan status charging di Settings screen, dilengkapi progress bar dengan warna dinamis (hijau > 50%, kuning 20–50%, merah < 20%).

### 5. Notes List dengan Network Indicator
Halaman utama catatan dilengkapi banner offline animasi (`AnimatedVisibility`) yang muncul dan menghilang secara otomatis sesuai status koneksi.

### 6. Settings Screen (Device & Status Info)
Menampilkan informasi perangkat, status internet, status baterai, serta pengaturan tema dan urutan catatan dalam tampilan card yang bersih.

---

## Platform API (expect/actual)

```kotlin
// commonMain
expect class DeviceInfo() {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getAppVersion(): String
    fun isTablet(): Boolean
}

expect class NetworkMonitor() {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
```

---

## Video Demo

[Link Video Demo](https://drive.google.com/file/d/xxx/view?usp=sharing)

---

## Screenshot

### Notes List (Online)
<!-- Tambahkan screenshot Notes List -->

### Notes List (Offline — Banner Merah)
<!-- Tambahkan screenshot offline banner -->

### Settings — Device Info & Battery
<!-- Tambahkan screenshot Settings -->

### Favorites Screen
<!-- Tambahkan screenshot Favorites -->

### Profile Screen
<!-- Tambahkan screenshot Profile -->

---

## Struktur Project

```
Tugas8_PAM/
└── composeApp/src/
    ├── androidMain/kotlin/com/muhammadnurikhsan/tugas8_pam/
    │   ├── MainActivity.kt
    │   ├── MyApplication.kt
    │   ├── di/
    │   │   └── AndroidModule.kt
    │   └── platform/
    │       ├── BatteryInfo.android.kt
    │       ├── DeviceInfo.android.kt
    │       └── NetworkMonitor.android.kt
    └── commonMain/kotlin/com/muhammadnurikhsan/tugas8_pam/
        ├── App.kt
        ├── data/
        │   ├── NoteRepository.kt
        │   ├── NotesUiState.kt
        │   └── SettingsRepository.kt
        ├── di/
        │   └── AppModule.kt
        ├── platform/
        │   ├── BatteryInfo.kt
        │   ├── DeviceInfo.kt
        │   └── NetworkMonitor.kt
        ├── viewmodel/
        │   ├── NoteViewModel.kt
        │   └── SettingsViewModel.kt
        ├── navigation/
        │   ├── AppNavigation.kt
        │   └── Screen.kt
        └── screens/
            ├── NoteListScreen.kt
            ├── NoteDetailScreen.kt
            ├── AddNoteScreen.kt
            ├── EditNoteScreen.kt
            ├── FavoritesScreen.kt
            ├── ProfileScreen.kt
            └── SettingsScreen.kt
```
