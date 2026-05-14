# Notes App — Tugas 8 PAM

**Nama:** Muhammad Nurikhsan
**NIM:** 123140057
**Program Studi:** Teknik Informatika
**Kelas:** Pengembangan Aplikasi Mobile RB
**Minggu:** 8

---

## Deskripsi

Pengembangan dari Tugas 7 dengan penambahan fitur **Dependency Injection (Koin)**, **Platform API** (device info, network monitor, battery info), dan peningkatan tampilan UI. Arsitektur lebih modular karena seluruh dependency dikelola oleh Koin, bukan diinisialisasi manual.

---

## Fitur

Semua fitur Tugas 7, ditambah:

- **Dependency Injection** menggunakan Koin — ViewModel dan semua dependency di-inject otomatis
- **Network Monitor** — banner otomatis muncul ketika koneksi internet terputus
- **Device Info** — menampilkan nama perangkat, versi OS, versi aplikasi, dan jenis layar (tablet/smartphone)
- **Battery Info** — menampilkan persentase baterai, status charging, dan progress bar dengan warna dinamis
- **Floating Bottom Navigation** — bottom nav berbentuk pill dengan rounded corner dan shadow
- Desain UI diperbarui ke tema terang (*light*) yang lebih bersih

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
| Koin Compose | 1.0.3 | DI di Composable (`koinViewModel`, `koinInject`) |
| Jetpack Navigation Compose | 2.7.0-alpha07 | Navigasi antar screen |
| Lifecycle ViewModel Compose | 2.8.3 | State management |
| kotlinx-datetime | 0.6.0 | Pengelolaan timestamp |

---

## Arsitektur

```
UI Layer (Screens)
      ↓
ViewModel Layer          ←  Koin inject
      ↓
Repository Layer         ←  Koin inject
      ↓
Data Layer (SQLDelight, Settings)
      ↓
Platform Layer (DeviceInfo, NetworkMonitor, BatteryInfo)  ←  expect/actual
```

Koin diinisialisasi di `MyApplication.onCreate()` dengan dua modul:

- **`appModule`** (commonMain) — repository, ViewModel, service platform, settings
- **`androidModule`** (androidMain) — `SqlDriver` untuk Android

### Struktur Direktori

```
composeApp/src/
├── androidMain/kotlin/com/muhammadnurikhsan/tugas8_pam/
│   ├── MainActivity.kt
│   ├── MyApplication.kt
│   ├── database/
│   │   └── DatabaseDriverFactory.kt
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
    │   ├── BatteryInfo.kt        ← expect class
    │   ├── DeviceInfo.kt         ← expect class
    │   └── NetworkMonitor.kt     ← expect class
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

---

## Platform API (expect/actual)

### DeviceInfo
```kotlin
expect class DeviceInfo() {
    fun getDeviceName(): String   // e.g. "Samsung Galaxy S23"
    fun getOsVersion(): String    // e.g. "Android 14 (API 34)"
    fun getAppVersion(): String   // e.g. "1.0"
    fun isTablet(): Boolean
}
```

### NetworkMonitor
```kotlin
expect class NetworkMonitor() {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>  // reactive stream
}
```

### BatteryInfo
```kotlin
expect class BatteryInfo() {
    fun getBatteryLevel(): Int   // 0–100
    fun isCharging(): Boolean
}
```

---

## Dependency Injection (Koin)

### AppModule (commonMain)
```kotlin
val appModule = module {
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
    single { Settings() }
    single { SettingsRepository(get()) }
    single { NotesDatabase(get()) }
    single { NoteRepository(get()) }
    factory { NoteViewModel(get()) }
    factory { SettingsViewModel(get()) }
}
```

### AndroidModule (androidMain)
```kotlin
val androidModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(NotesDatabase.Schema, appContext, "notes.db")
    }
}
```

---

## Skema Database

```sql
CREATE TABLE NoteEntity (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    title       TEXT    NOT NULL,
    content     TEXT    NOT NULL,
    is_favorite INTEGER NOT NULL DEFAULT 0,
    created_at  INTEGER NOT NULL,
    updated_at  INTEGER NOT NULL
);
```

---

## Navigasi

| Screen | Route | Deskripsi |
|---|---|---|
| NoteListScreen | `note_list` | Halaman utama daftar catatan |
| FavoritesScreen | `favorites` | Daftar catatan favorit |
| ProfileScreen | `profile` | Profil mahasiswa |
| SettingsScreen | `settings` | Pengaturan + info perangkat & baterai |
| NoteDetailScreen | `note_detail/{noteId}` | Detail catatan |
| AddNoteScreen | `add_note` | Tambah catatan baru |
| EditNoteScreen | `edit_note/{noteId}` | Edit catatan |

---

## Build & Run

**macOS / Linux:**
```shell
./gradlew :composeApp:assembleDebug
```

**Windows:**
```shell
.\gradlew.bat :composeApp:assembleDebug
```

**Requirement:** Android SDK 24+ (minSdk), target SDK 34. Tidak ada konfigurasi tambahan — langsung build and run.

---

## Perbedaan dari Tugas 7

| Aspek | Tugas 7 | Tugas 8 |
|---|---|---|
| DI | Manual (`remember { }`) | Koin (`koinViewModel`, `koinInject`) |
| Driver injection | Lewat parameter `App(driver)` | Koin `androidModule` |
| Platform API | Tidak ada | DeviceInfo, NetworkMonitor, BatteryInfo |
| Network indicator | Tidak ada | Banner merah otomatis saat offline |
| Bottom nav | Flat dark nav bar | Floating pill nav dengan shadow |
| Tema | Dark theme | Light theme |
| Application class | Tidak ada | `MyApplication` untuk init Koin |
