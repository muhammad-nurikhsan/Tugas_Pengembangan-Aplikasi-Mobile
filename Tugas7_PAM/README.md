# Notes App

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB 
**Minggu:** 7

---

## Deskripsi

Aplikasi catatan pribadi berbasis **Kotlin Multiplatform** dengan target Android. Data catatan disimpan secara lokal menggunakan **SQLDelight**, sehingga aplikasi berjalan sepenuhnya secara *offline-first*. Preferensi pengguna (tema dan urutan catatan) disimpan menggunakan **Multiplatform Settings**.

---

## Fitur

- Tambah, edit, dan hapus catatan
- Tandai catatan sebagai favorit
- Pencarian catatan secara real-time (by judul & isi)
- Halaman daftar favorit
- Pengaturan tema (System / Light / Dark) dan urutan catatan (Terbaru / Terlama / Judul A-Z)
- Halaman profil mahasiswa
- Bottom navigation bar dengan 4 tab: Notes, Starred, Profile, Settings

---

## Teknologi & Library

| Library | Versi | Kegunaan |
|---|---|---|
| Kotlin Multiplatform | 2.0.20 | Target Android |
| Compose Multiplatform | 1.6.11 | UI Framework |
| SQLDelight | 2.0.2 | Database lokal |
| Multiplatform Settings | 1.1.1 | Penyimpanan preferensi |
| Jetpack Navigation Compose | 2.7.0-alpha07 | Navigasi antar screen |
| Lifecycle ViewModel Compose | 2.8.3 | State management |
| kotlinx-datetime | 0.6.0 | Pengelolaan timestamp |

---

## Arsitektur

Aplikasi menggunakan pola **MVVM (Model-View-ViewModel)** dengan pemisahan layer yang jelas:

```
UI Layer (Screens)
      ↓
ViewModel Layer (NoteViewModel, SettingsViewModel)
      ↓
Repository Layer (NoteRepository, SettingsRepository)
      ↓
Data Layer (SQLDelight DB, Multiplatform Settings)
```

### Struktur Direktori

```
composeApp/src/
├── androidMain/kotlin/com/muhammadnurikhsan/tugas7_pam/
│   ├── MainActivity.kt
│   ├── Platform.kt
│   └── database/
│       └── DatabaseDriverFactory.kt
└── commonMain/kotlin/com/muhammadnurikhsan/tugas7_pam/
    ├── App.kt
    ├── data/
    │   ├── NoteRepository.kt
    │   ├── NotesUiState.kt
    │   └── SettingsRepository.kt
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

Query yang tersedia: `selectAll`, `selectById`, `selectByQuery`, `selectFavorites`, `insert`, `update`, `toggleFavorite`, `delete`, `countAll`.

---

## Navigasi

| Screen | Route | Deskripsi |
|---|---|---|
| NoteListScreen | `note_list` | Halaman utama daftar catatan |
| FavoritesScreen | `favorites` | Daftar catatan favorit |
| ProfileScreen | `profile` | Profil mahasiswa |
| SettingsScreen | `settings` | Pengaturan aplikasi |
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

**Requirement:** Android SDK 24+ (minSdk), target SDK 34.
