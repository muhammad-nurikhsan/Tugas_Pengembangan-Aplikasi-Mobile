# News Feed Simulator

**Nama:** Muhammad Nurikhsan  
**NIM:** 123140057  
**Program Studi:** Teknik Informatika 

---

## Deskripsi
Aplikasi Kotlin + Jetpack Compose yang mensimulasikan feed berita real-time menggunakan:

- Flow (emit tiap 2 detik)
- Filter kategori
- Transform data
- StateFlow
- Coroutine async

Kategori berita:
- Teknologi
- Politik
- Cuaca

---

## Cara Menjalankan

1. Clone atau download repository
2. Buka di Android Studio
3. Tunggu Gradle Sync selesai
4. Klik Run (▶)

---

## Cara Menggunakan

- Tekan **Start** → berita muncul setiap 2 detik
- Klik berita → jumlah dibaca bertambah & detail tampil (async)
- Tekan **Ganti Kategori** → filter berubah
- Tekan **Stop** → reset data

---

## Implementasi Sesuai Ketentuan

✔ Flow dengan `delay(2000)`  
✔ Filter dengan `.filter {}`  
✔ Transform dengan `.map {}`  
✔ StateFlow untuk jumlah dibaca  
✔ Coroutine `launch + async/await` untuk detail berita  

---

## Screenshot

### Tampilan Awal
<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/fa6bb360-1b47-44ae-8dc1-126c920a6a3f" />


### Kategori Teknologi
<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/50b31062-bb33-4db3-9d96-5c1c7568bf8c" />


### Kategori Politik
<img width="1913" height="1073" alt="image" src="https://github.com/user-attachments/assets/9c585591-8305-4ad2-abd5-d10f267a362c" />


### Kategori Cuaca
<img width="1918" height="1076" alt="image" src="https://github.com/user-attachments/assets/79a27799-fcc2-4348-a051-e8f1dec094fb" />

---

Project berjalan normal dan seluruh fitur sesuai dengan deskripsi tugas.
