# Notes App - Integrasi AI API
**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB  

---

## Deskripsi
Project ini merupakan pengembangan dari Tugas 8 (Notes App) dengan menambahkan integrasi AI menggunakan Google Gemini API. Aplikasi catatan pribadi ini kini dilengkapi fitur kecerdasan buatan yang membantu pengguna mengelola dan menganalisis catatan mereka.

Fitur AI yang diterapkan meliputi:
- AI Chat Assistant berbasis multi-turn conversation dengan Gemini 2.0 Flash
- AI Summarize Note untuk merangkum isi catatan secara otomatis
- Proper error handling dengan retry logic (exponential backoff)
- Loading states dan animasi typing indicator
- Service layer terpisah untuk AI logic menggunakan Ktor Client

---

## API yang Digunakan
**Google Gemini API** - `https://generativelanguage.googleapis.com/v1beta`

| Endpoint | Method | Deskripsi |
|---|---|---|
| `/models/gemini-2.5-flash:generateContent` | POST | Generate konten AI (chat & summarize) |

---

## Fitur Aplikasi

### 1. AI Chat Assistant
Pengguna dapat membuka AI Chat melalui FAB berlabel ✨ di halaman Notes. AI merespons dalam Bahasa Indonesia dan mendukung percakapan multi-turn — riwayat percakapan dipertahankan selama sesi berlangsung.

### 2. AI Summarize Note
Di halaman Detail Catatan, terdapat tombol ✨ di TopBar. Saat ditekan, AI merangkum isi catatan menjadi 2-3 kalimat padat yang ditampilkan dalam card hijau di bawah konten.

### 3. Multi-turn Conversation (Bonus +5%)
Riwayat percakapan dikirim ke Gemini API pada setiap request, sehingga AI dapat memahami konteks percakapan sebelumnya.

### 4. Loading State & Typing Indicator
Saat menunggu respons AI, ditampilkan animasi tiga titik bergerak (typing indicator) menyerupai pengalaman chat nyata.

### 5. Error Handling
Setiap error AI dikategorikan menggunakan sealed class `AIError`:
- `RateLimited` → retry otomatis setelah delay
- `ServerError` → retry 3x dengan exponential backoff (1s → 2s → 4s)
- `NetworkError` → ditampilkan via Snackbar
- `Unauthorized` → notifikasi API key tidak valid

### 6. Prompt Engineering
System prompt dirancang khusus untuk konteks aplikasi catatan:
- Chat: AI berperan sebagai asisten catatan pribadi yang ramah
- Summarize: AI hanya mengeluarkan ringkasan tanpa kalimat pembuka

---

## Arsitektur AI Layer
AIChatScreen / NoteDetailScreen
↓
AIViewModel
↓
GeminiService  ←  ApiConfig (BuildConfig)
↓
Gemini 2.5 Flash API

---

## Video Demo
[Link Video Demo]([https://drive.google.com/file/d/xxx/view?usp=sharing](https://drive.google.com/file/d/1ufC-G6ck4oAfW2hGCezex59kJHo8Wcy4/view?usp=sharing))

---

## Screenshot

### AI Chat Screen
<img width="558" height="1280" alt="Image" src="https://github.com/user-attachments/assets/352c2b0d-fe11-473f-8389-fd36607d669e" />

### Typing Indicator (Loading State)
<img width="720" height="1650" alt="Image" src="https://github.com/user-attachments/assets/4905a113-510a-488e-b38f-9b52c16abab7" />  

### AI Summarize Note
<img width="720" height="1650" alt="Image" src="https://github.com/user-attachments/assets/8ac0f948-d315-4fb8-90ef-8daa921c1586" />


---
## Struktur Project
```
Tugas9_PAM/
└── composeApp/src/
├── androidMain/kotlin/com/muhammadnurikhsan/tugas9_pam/
│   ├── config/
│   │   └── ApiConfig.android.kt
│   ├── di/
│   │   └── AndroidModule.kt
│   └── platform/
│       ├── BatteryInfo.android.kt
│       ├── DeviceInfo.android.kt
│       └── NetworkMonitor.android.kt
└── commonMain/kotlin/com/muhammadnurikhsan/tugas9_pam/
├── config/
│   └── ApiConfig.kt
├── network/
│   ├── GeminiModels.kt
│   ├── AIError.kt
│   └── GeminiService.kt
├── data/
│   ├── AIChatUiState.kt
│   ├── NoteRepository.kt
│   ├── NotesUiState.kt
│   └── SettingsRepository.kt
├── viewmodel/
│   ├── AIViewModel.kt
│   ├── NoteViewModel.kt
│   └── SettingsViewModel.kt
├── screens/
│   ├── AIChatScreen.kt
│   ├── NoteListScreen.kt
│   ├── NoteDetailScreen.kt
│   ├── AddNoteScreen.kt
│   ├── EditNoteScreen.kt
│   ├── FavoritesScreen.kt
│   ├── ProfileScreen.kt
│   └── SettingsScreen.kt
├── navigation/
│   ├── Screen.kt
│   └── AppNavigation.kt
└── di/
└── AppModule.kt

```
---

## Setup

1. Buka [aistudio.google.com](https://aistudio.google.com) → Get API key → Create API key
2. Tambahkan ke `local.properties`:
GEMINI_API_KEY=YOUR_KEY_HERE
3. Sync Gradle → Run

> **Keamanan:** `local.properties` sudah di-ignore oleh `.gitignore`. API key tidak akan ter-commit ke repository.
