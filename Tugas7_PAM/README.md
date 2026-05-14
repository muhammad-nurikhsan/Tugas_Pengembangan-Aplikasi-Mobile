# Notes App - Local Data Storage
**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB  

---

## Deskripsi

Project ini merupakan implementasi aplikasi Notes App menggunakan Kotlin Multiplatform dengan fokus pada penyimpanan data lokal menggunakan SQLDelight dan Multiplatform Settings.

Fitur utama yang diterapkan meliputi:
- SQLDelight database untuk menyimpan catatan secara lokal (offline-first)
- CRUD operations (Create, Read, Update, Delete) untuk catatan
- Fitur favorit dengan toggle `is_favorite`
- Search functionality real-time berdasarkan judul dan isi catatan
- Settings screen dengan Multiplatform Settings (tema & urutan catatan)
- UI states yang proper (Loading, Empty, Content, Error)
- Bottom navigation dengan 4 tab: Notes, Starred, Profile, Settings

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

## Fitur Aplikasi

### 1. Notes List
Menampilkan daftar catatan dari database lokal, dilengkapi search bar real-time dan tombol tambah catatan. Catatan pertama ditampilkan dengan warna aksen berbeda sebagai highlight.

### 2. Add / Edit Note
Form untuk membuat catatan baru atau mengedit catatan yang sudah ada, dengan validasi judul tidak boleh kosong sebelum disimpan.

### 3. Note Detail
Halaman detail menampilkan judul, tanggal terakhir diperbarui, dan isi catatan lengkap. Tersedia tombol toggle favorit dan edit di TopBar.

### 4. Favorites Screen
Menampilkan hanya catatan yang ditandai sebagai favorit. Menampilkan empty state dengan ikon dan teks panduan jika belum ada catatan favorit.

### 5. Settings Screen
Menyimpan preferensi tema (Ikuti Sistem / Terang / Gelap) dan urutan catatan (Terbaru Dulu / Terlama Dulu / Judul A-Z) menggunakan Multiplatform Settings yang persisten antar sesi.

### 6. Profile Screen
Menampilkan informasi mahasiswa dan informasi aplikasi secara statis.

---

## Database Schema

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

## Video Demo

[Link Video Demo](https://drive.google.com/file/d/xxx/view?usp=sharing)

---

## Screenshot

### Notes List
<!-- Tambahkan screenshot Notes List -->

### Add Note
<!-- Tambahkan screenshot Add Note -->

### Note Detail
<!-- Tambahkan screenshot Note Detail -->

### Favorites Screen
<!-- Tambahkan screenshot Favorites -->

### Settings Screen
<!-- Tambahkan screenshot Settings -->

---

## Struktur Project

```
Tugas7_PAM/
└── composeApp/src/
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
