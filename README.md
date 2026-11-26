# Student CRUD - Java Thread & Database

Latihan/case study materi **Thread dan Database pada Java** (5C - Java Coding Practice - Thread and Database - 2026).

**NIM:** (F1D02410100)
**Nama:** (Ahmad Yadan Ulya Munaji)

## Deskripsi

Aplikasi desktop berbasis Java Swing untuk mengelola data mahasiswa (NIM dan nama) dengan operasi CRUD (Create, Read, Update, Delete) ke database MySQL melalui JDBC. Semua operasi database dijalankan di thread terpisah agar tampilan (GUI) tidak freeze.

## Fitur

- CRUD data mahasiswa (Create, Read, Update, Delete)
- Pola **DAO** (`StudentDAO`) dengan `Connection`, `PreparedStatement`, `transaction` (commit/rollback), dan `ResultSet`
- Operasi database berjalan di **thread terpisah**
- Pembaruan GUI memakai **`SwingUtilities.invokeLater`**
- Saat tombol diklik: tombol **di-disable** dan label menampilkan **"Saving..."**
- Validasi input (NIM dan Name wajib diisi)

## Pola yang Digunakan

```
Klik tombol -> disable button + label "Saving..." -> DB diproses di thread lain -> invokeLater -> update UI/GUI
```

## Teknologi

- Java (JDK) + Java Swing
- MySQL (XAMPP)
- MySQL Connector/J 26.10.0
- Visual Studio Code

## Struktur Project

```
StudentCRUD/
├── src/
│   ├── DBConnection.java   # koneksi ke database
│   ├── Student.java        # model data
│   ├── StudentDAO.java     # operasi CRUD (DAO)
│   └── MainGUI.java        # tampilan GUI + thread
├── screenshot/             # gambar hasil program
├── mysql-connector-j-26.10.0.jar
├── schema.sql              # script pembuatan database & tabel
└── README.md
```

## Penjelasan Kode

| File | Fungsi |
|---|---|
| `DBConnection.java` | Membuat koneksi JDBC ke database `student_db` |
| `Student.java` | Class model dengan atribut id, nim, name |
| `StudentDAO.java` | Method `insert`, `findAll`, `update`, `delete` memakai `PreparedStatement`; insert/update/delete memakai transaction (`setAutoCommit(false)`, `commit`, `rollback`); `findAll` membaca data lewat `ResultSet` |
| `MainGUI.java` | Form input, tabel data, dan tombol Save/Update/Delete. Setiap aksi menjalankan DAO di `new Thread(...)`, lalu hasilnya ditampilkan ke UI lewat `SwingUtilities.invokeLater` |

## Cara Menjalankan

1. Jalankan **MySQL** (lewat XAMPP Control Panel).
2. Buat database dan tabel dengan menjalankan isi `schema.sql` di phpMyAdmin (tab SQL).
3. Buka folder project di VS Code, lalu tambahkan `mysql-connector-j-26.10.0.jar` ke **JAVA PROJECTS -> Referenced Libraries**.
4. Sesuaikan `USER` dan `PASS` di `DBConnection.java` jika berbeda (default XAMPP: user `root`, password kosong).
5. Jalankan `MainGUI.java`.

## Screenshot

### 1. Tampilan awal (status Ready)
![Tampilan awal](screenshot/01-ready.png)

### 2. Proses menyimpan (tombol disabled + label "Saving...")
![Saving](screenshot/02-saving.png)

### 3. Setelah tersimpan (UI diperbarui lewat invokeLater)
![Saved](screenshot/03-saved.png)

### 4. Data tersimpan di database (phpMyAdmin)
![Database](screenshot/04-database.png)

### 5. Update data
![Update](screenshot/05-update.png)

### 6. Delete data
![Delete](screenshot/06-delete.png)