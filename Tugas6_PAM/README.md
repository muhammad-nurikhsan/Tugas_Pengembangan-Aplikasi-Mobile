# News Reader App - Networking dan REST API

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB  

---

## Deskripsi

Project ini merupakan implementasi aplikasi News Reader menggunakan Kotlin Multiplatform dengan fokus pada integrasi networking dan REST API menggunakan Ktor Client.

Fitur utama yang diterapkan meliputi:

- Fetch data artikel dari public API (DummyJSON)
- Menampilkan list artikel dengan title, description, dan image
- Detail screen saat artikel di-klik
- Pull to refresh functionality
- Proper loading, success, dan error states
- Repository pattern untuk API calls
- Struktur kode yang terorganisir menggunakan folder:
  - `data/model/`
  - `data/repository/`
  - `network/`
  - `ui/screens/`

---

## API yang Digunakan

**DummyJSON** — `https://dummyjson.com`

| Endpoint | Method | Deskripsi |
|---|---|---|
| `/posts` | GET | Mengambil semua artikel |
| `/posts/{id}` | GET | Mengambil detail artikel |

---

## Fitur Aplikasi

### 1. Article List
Menampilkan daftar artikel yang di-fetch dari DummyJSON API, lengkap dengan image, title, tags, dan preview body.

### 2. Detail Screen
Setiap artikel dapat dibuka ke halaman detail dengan membawa `articleId` sebagai navigation argument.

### 3. Pull to Refresh
Pengguna dapat menarik layar dari atas ke bawah untuk me-refresh data dari API.

### 4. Loading State
Saat data sedang di-fetch, aplikasi menampilkan `CircularProgressIndicator` di tengah layar.

### 5. Error State
Jika terjadi kegagalan jaringan, aplikasi menampilkan pesan error beserta tombol "Coba Lagi" untuk retry.

### 6. Repository Pattern
API logic dipisahkan dari ViewModel menggunakan `ArticleRepository`, mengikuti prinsip clean architecture.

---

## Video Demo

[https://drive.google.com/file/d/19HU_-AJz-dN5s_njbgzTKJS5EvRrYTcT/view?usp=sharing](https://drive.google.com/file/d/1zTYVnN7LZu8_Bk71s3ho8CyHHF4MUR0E/view?usp=sharing)

---

## Screenshot

### Loading State
<img width="557" height="929" alt="Image" src="https://github.com/user-attachments/assets/5deaf821-06da-4cd6-89b8-f835bfb7e5f8" />

### Success State (Article List)
<img width="536" height="936" alt="Image" src="https://github.com/user-attachments/assets/b6301953-a9d2-4793-a446-b1029ca9b241" />

### Detail Screen
<img width="552" height="950" alt="Image" src="https://github.com/user-attachments/assets/aa26fcab-b4d3-4a96-bee8-24f70645b362" />

### Error State
<img width="541" height="935" alt="Image" src="https://github.com/user-attachments/assets/0d007930-fa67-4041-b877-e33273a4450d" />

### Pull to Refresh
<img width="564" height="934" alt="Image" src="https://github.com/user-attachments/assets/cca93458-bbf7-4baf-bd9d-54840a1dd065" />

---

## Struktur Project

```
Tugas6_PAM/
└── composeApp/src/androidMain/kotlin/
    └── com/muhammadnurikhsan/tugas6_pam/
        ├── data/
        │   ├── model/
        │   │   └── Article.kt
        │   └── repository/
        │       └── ArticleRepository.kt
        ├── network/
        │   └── HttpClientFactory.kt
        ├── ui/
        │   ├── screens/
        │   │   ├── ArticleListScreen.kt
        │   │   └── ArticleDetailScreen.kt
        │   └── ArticlesViewModel.kt
        └── App.kt
```
