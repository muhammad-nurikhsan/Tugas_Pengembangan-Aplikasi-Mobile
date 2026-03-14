# My Profile App

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika  
**Kelas:** Pengembangan Aplikasi Mobile RB

---

## Deskripsi

Aplikasi Compose Multiplatform yang menampilkan halaman profil pengguna menggunakan paradigma UI deklaratif, mencakup:

- Composable Functions yang reusable
- Basic Layouts (Column, Row, Box)
- Modifiers untuk styling dan positioning
- Komponen UI (Card, Button, Icon, Text)
- AnimatedVisibility untuk transisi konten

Komponen yang diimplementasikan:
- ProfileHeader
- InfoItem
- SocialButton

---

## Cara Menjalankan

1. Clone atau download repository
2. Buka folder `Tugas3_PAM` di Android Studio
3. Tunggu Gradle Sync selesai
4. Jalankan emulator (Pixel API 28 atau lebih tinggi)
5. Klik Run

---

## Cara Menggunakan

- Buka aplikasi → tampil halaman profil dengan foto, nama, dan title
- Scroll ke bawah → lihat informasi Email, Phone, dan Location
- Tekan **Sembunyikan Info** → card informasi hilang dengan animasi
- Tekan **Tampilkan Info** → card informasi muncul kembali dengan animasi
- Tombol **Share Profile** dan **Edit Profile** tersedia di bagian bawah

---

## Implementasi Sesuai Ketentuan

✔ `ProfileHeader` : Box + Column + Image circular + Text nama & title  
✔ `InfoItem` : Row + Icon + Column (label & value), dipakai 3x  
✔ `SocialButton` : OutlinedButton + Icon, reusable dengan parameter  
✔ Layout: `Column`, `Row`, `Box` digunakan di seluruh komponen  
✔ `Card` dengan elevation untuk container informasi  
✔ `Modifier` : padding, fillMaxWidth, size, clip, border, background  
✔ `AnimatedVisibility` untuk toggle tampilan info (**Bonus +10%**)  

---

## Screenshot

### Tampilan Awal
`[Screenshot tampilan awal aplikasi]`

### Info Ditampilkan
`[Screenshot saat card info terlihat]`

### Info Disembunyikan
`[Screenshot saat card info tersembunyi]`

---

Project berjalan normal dan seluruh fitur sesuai dengan deskripsi tugas Pertemuan 3.
