# PrakJDBC

Proyek ini adalah implementasi dasar Java Database Connectivity (JDBC) menggunakan koneksi ke MySQL. Project ini bisa dibuka di **VS Code** atau **NetBeans**.

---

## ✅ Langkah-Langkah Menjalankan Project

### 1. Download MySQL Connector JAR
Download file `mysql-connector-j-8.3.0.jar` dari link berikut:

🔗 [Download mysql-connector-j-8.3.0.jar](https://drive.google.com/file/d/18yqW_8OzOznHHMDV0-MIvjQHfpXQFvKD/view?usp=sharing)

---

### 2. Clone / Buka Project
Clone atau buka folder project ini di **VS Code** atau **NetBeans** sesuai preferensimu.

---

### 3. Import Database SQL
Sebelum menjalankan project, kamu **wajib import database-nya** terlebih dahulu:

- Buka **phpMyAdmin**
- Buat database baru dengan nama: `biodata_db`
- Import file SQL berikut:  
  📂 `database/biodata_db.sql`

📌 Lokasi file: `database/biodata_db.sql`  

---

### 4. Import JAR ke Proyek

#### 📘 Jika Menggunakan VS Code:
- Buka tab **JAVA PROJECTS** di sidebar.
- Di bagian **Referenced Libraries**, klik ikon ➕.
- Pilih file `mysql-connector-j-8.3.0.jar` yang telah kamu download.
- Setelah berhasil, library MySQL akan muncul di daftar.

📷 Contoh:
![Import JAR VS Code](screenshots/vscode.png)

---

#### ☕ Jika Menggunakan NetBeans:
- Klik kanan pada folder **Libraries** dalam proyek.
- Pilih `Add JAR/Folder...`
- Arahkan ke file `mysql-connector-j-8.3.0.jar` dan klik Open.

📷 Contoh:
![Import JAR NetBeans](screenshots/netbeans.png)

---

### 5. Jalankan Proyek
- Pastikan database MySQL sedang aktif.
- Pastikan konfigurasi koneksi di `Main.java` sudah sesuai (host, user, password, nama DB).
- Jalankan `Main.java`.

---

