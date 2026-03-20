# Profile App - State Management & MVVM

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  

---

## Deskripsi

Pengembangan Profile App dari Pertemuan 3 menggunakan arsitektur MVVM dan State Management dengan Compose Multiplatform, mencakup:

- MVVM Pattern (Model - View - ViewModel)
- StateFlow untuk reactive UI state
- State Hoisting pada TextField
- Recomposition otomatis saat state berubah
- Animated dark mode toggle

Struktur folder sesuai ketentuan:
- `data/` : ProfileUiState data class
- `viewmodel/` : ProfileViewModel dengan StateFlow
- `ui/` : ProfileScreen dan EditProfileForm

---

## Cara Menjalankan

1. Clone atau download repository
2. Buka folder `Tugas4_PAM` di Android Studio
3. Tunggu Gradle Sync selesai
4. Jalankan emulator (Pixel API 28 atau lebih tinggi)
5. Klik Run 

---

## Cara Menggunakan

- Buka aplikasi → tampil halaman profil lengkap
- Tekan **Edit Profile** → form edit muncul dengan animasi
- Ubah nama atau bio → tekan **Simpan** → profil terupdate langsung
- Tekan **Batal** → form tertutup tanpa menyimpan perubahan
- Toggle switch di atas → tema berganti antara gelap dan terang secara smooth

---

## Implementasi Sesuai Ketentuan

✔ `ProfileViewModel` dengan `MutableStateFlow` dan `StateFlow`  
✔ `ProfileUiState` data class dengan semua state UI  
✔ `_uiState.update { it.copy(...) }` untuk update state immutable  
✔ `collectAsState()` untuk observe StateFlow di Composable  
✔ `LabeledTextField` stateless - state hoisting dengan `value` + `onValueChange`  
✔ `saveProfile(name, bio)` di ViewModel untuk update dari UI  
✔ `isDarkMode` disimpan di ViewModel, tidak di Composable  
✔ `animateColorAsState` untuk transisi dark mode yang smooth (**Bonus +10%**)  
✔ `AnimatedVisibility` untuk form edit yang muncul/sembunyi  
✔ Struktur folder: `ui/`, `viewmodel/`, `data/`  

---

## Screenshot

### Tampilan Profile (Light Mode)
<img width="505" height="963" alt="Image" src="https://github.com/user-attachments/assets/ab997b69-2049-4076-8560-7cb275fcce64" />

### Form Edit Profile
<img width="520" height="968" alt="Image" src="https://github.com/user-attachments/assets/bb82db93-0842-4ab8-8062-9146524b1e3e" />
<img width="544" height="953" alt="Image" src="https://github.com/user-attachments/assets/cdcadcdc-ec82-4923-99d2-8fa28664a41d" />
<img width="504" height="960" alt="Image" src="https://github.com/user-attachments/assets/a0faaa8b-181d-4295-adc9-e6604359594e" />

### Tampilan Dark Mode
<img width="525" height="952" alt="Image" src="https://github.com/user-attachments/assets/4e9009a4-32ed-4bd8-add5-b50e5d0a7656" />

---

Project berjalan normal dan seluruh fitur sesuai dengan deskripsi tugas Pertemuan 4.
