# Notes App - Navigasi Antar Layar

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB  

---

## Deskripsi

Project ini merupakan pengembangan **Notes App** dari praktikum sebelumnya dengan fokus pada implementasi **navigasi antar layar** menggunakan **Compose Multiplatform**.

Fitur utama yang diterapkan meliputi:

- Bottom Navigation dengan 3 tab: **Notes**, **Favorites**, dan **Profile**
- Navigasi dari **Note List → Note Detail** dengan passing `noteId`
- Floating Action Button (FAB) untuk menuju **Add Note**
- Back navigation yang proper
- Navigasi ke **Edit Note** dengan passing `noteId`
- Struktur kode yang terorganisir menggunakan folder:
  - `navigation/`
  - `screens/`
  - `components/`
  - `viewmodel/`
  - `data/`

---

## Fitur Aplikasi

### 1. Bottom Navigation
Navigasi utama aplikasi menggunakan 3 tab:
- **Notes**
- **Favorites**
- **Profile**

### 2. Note List → Note Detail
Setiap note dapat dibuka ke halaman detail dengan membawa `noteId` sebagai argument.

### 3. Add Note
Tombol **Floating Action Button** digunakan untuk menuju halaman penambahan note baru.

### 4. Edit Note
Pengguna dapat menuju halaman edit note dengan passing `noteId`.

### 5. Back Navigation
Setiap screen mendukung navigasi kembali secara proper menggunakan `popBackStack()`.

---

## Screenshot

### Hasil Ketika di Run
<img width="522" height="944" alt="Image" src="https://github.com/user-attachments/assets/432a97fc-290e-4002-814d-754a96131c84" />
<img width="566" height="939" alt="Image" src="https://github.com/user-attachments/assets/63c9838c-18b2-4af8-8093-ec1cd3d3e144" />
<img width="515" height="945" alt="Image" src="https://github.com/user-attachments/assets/2ee70ebb-0199-4067-99d6-9e46279df781" />
<img width="536" height="949" alt="Image" src="https://github.com/user-attachments/assets/7d2e1a82-eee6-4b19-b6b0-3cafb0949b1f" />
<img width="525" height="944" alt="Image" src="https://github.com/user-attachments/assets/2fdafc12-1db6-4689-8ab0-6a45b592bfb4" />
<img width="542" height="946" alt="Image" src="https://github.com/user-attachments/assets/23383ab5-9188-477a-aa37-ac6367b101f8" />
<img width="500" height="945" alt="Image" src="https://github.com/user-attachments/assets/04c0c284-43c8-4428-a0e0-a569d749dd6c" />

---

## Struktur Folder

```bash
Tugas5_PAM/
├── composeApp/
│   └── src/
│       ├── commonMain/
│       │   └── kotlin/com/muhammadnurikhsan/myprofileapp/
│       │       ├── components/
│       │       ├── data/
│       │       ├── navigation/
│       │       ├── screens/
│       │       └── viewmodel/
│       └── androidMain/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
