-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Jul 2020 pada 17.56
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `final_project_pbo`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `nama_barang` varchar(20) NOT NULL,
  `jenis_barang` varchar(20) NOT NULL,
  `stok_barang` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `harga_beli` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id_barang`, `id_supplier`, `nama_barang`, `jenis_barang`, `stok_barang`, `harga_jual`, `harga_beli`) VALUES
(10011, 1001, 'citos', 'makanan', 5, 1000, 900),
(10012, 1001, 'komo', 'makanan', 10, 1100, 1000),
(10013, 1001, 'ohayo', 'makanan', 0, 1100, 1000),
(10014, 1001, 'potatoes', 'makanan', 7, 1100, 1000),
(10015, 1001, 'better', 'makanan', 22, 1100, 1000),
(10016, 1001, 'bang-bang', 'makanan', 3, 1100, 1000),
(10017, 1001, 'selamat', 'makanan', 10, 1100, 1000),
(10018, 1001, 'chupa', 'makanan', 8, 500, 400),
(10019, 1001, 'top', 'makanan', 10, 1100, 1000),
(10020, 1001, 'lays', 'makanan', 10, 2000, 1800),
(10021, 1001, 'chitos', 'makanan', 9, 2000, 1800),
(10022, 1001, 'twisko', 'makanan', 0, 1100, 1000),
(10111, 1002, 'teh gelas', 'minuman', 4, 1000, 900),
(10112, 1002, 'teh botol', 'minuman', 10, 2000, 1900),
(10113, 1002, 'teh pucuk', 'minuman', 6, 3500, 3000),
(10114, 1002, 'ale-ale', 'minuman', 2, 1000, 900),
(10115, 1002, 'kopiko', 'minuman', 10, 1000, 900),
(10116, 1002, 'torabika', 'minuman', 10, 1000, 900),
(10117, 1002, 'torpedo', 'minuman', 8, 1000, 900),
(10118, 1002, 'floridina', 'minuman', 5, 3000, 2000),
(10119, 1002, 'fruit tea', 'minuman', 10, 4000, 3000),
(10120, 1002, 'myzone', 'minuman', 10, 5000, 4000),
(10121, 1002, 'tebs', 'minuman', 10, 10000, 8000),
(10122, 1002, 'cimori', 'minuman', 10, 10000, 9000),
(10211, 1003, 'masako 5gram', 'bumbu', 5, 1000, 900),
(10212, 1003, 'masako 10gram', 'bumbu', 10, 2000, 1900),
(10213, 1003, 'masako 20gram', 'bumbu', 10, 5000, 4900),
(10214, 1003, 'ajinomoto 5gram', 'bumbu', 10, 1000, 900),
(10215, 1003, 'ajinomoto 10gram', 'bumbu', 10, 2000, 1900),
(10216, 1003, 'ajinomoto 20gram', 'bumbu', 10, 5000, 4900),
(10217, 1003, 'lada bubuk 5gram', 'bumbu', 10, 1000, 900),
(10218, 1003, 'lada bubuk 10gram', 'bumbu', 10, 2000, 1900),
(10219, 1003, 'lada bubuk 20gram', 'bumbu', 10, 5000, 4900),
(10220, 1003, 'sajiku ayam goreng', 'bumbu', 10, 1000, 900),
(10221, 1003, 'sajiku nasi goreng', 'bumbu', 10, 1000, 900),
(10222, 1003, 'sajiku rendang', 'bumbu', 10, 1000, 900),
(10311, 1004, 'apel 1kg', 'buah', 10, 20000, 19000),
(10312, 1004, 'anggur 1kg', 'buah', 10, 22000, 19000),
(10313, 1004, 'pisang 1kg', 'buah', 10, 23000, 19000),
(10314, 1004, 'jambu 1kg', 'buah', 10, 23000, 19000),
(10315, 1004, 'buah naga 1kg', 'buah', 10, 27000, 19000),
(10316, 1004, 'salak 1kg', 'buah', 10, 25000, 19000),
(10317, 1004, 'sotong 1kg', 'buah', 10, 23000, 19000),
(10318, 1004, 'melon 1kg', 'buah', 10, 21000, 19000),
(10319, 1004, 'semangka 1kg', 'buah', 10, 29000, 19000),
(10320, 1004, 'nanas 1kg', 'buah', 10, 27000, 19000),
(10321, 1004, 'mangga 1kg', 'buah', 10, 28000, 19000),
(10322, 1004, 'rambutan 1kg', 'buah', 10, 20000, 19000),
(10411, 1005, 'para cetamol', 'obat', 10, 20000, 19000),
(10412, 1005, 'panadol', 'obat', 10, 20000, 19000),
(10413, 1005, 'paramex', 'obat', 10, 20000, 19000),
(10414, 1005, 'OBH', 'obat', 10, 20000, 19000),
(10415, 1005, 'OBH combi', 'obat', 10, 20000, 19000),
(10416, 1005, 'Vitamin C', 'obat', 10, 20000, 19000),
(10417, 1005, 'adem sari', 'obat', 5, 20000, 19000),
(10418, 1005, 'oralit', 'obat', 7, 20000, 19000),
(10419, 1005, 'amoxilin', 'obat', 10, 20000, 19000),
(10420, 1005, 'antangin', 'obat', 10, 20000, 19000),
(10421, 1005, 'betadine', 'obat', 9, 20000, 19000),
(10511, 1005, 'paracetamol', 'obat', 8, 20000, 19000),
(19999, 2345, 'sari rotis', 'makanan', 6, 3500, 2500);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_log_kasir`
--

CREATE TABLE `detail_log_kasir` (
  `id_detail` int(11) NOT NULL,
  `id_kasir` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `qty_barang` int(11) NOT NULL,
  `subtotal` int(11) NOT NULL,
  `profit_product` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_log_kasir`
--

INSERT INTO `detail_log_kasir` (`id_detail`, `id_kasir`, `id_barang`, `qty_barang`, `subtotal`, `profit_product`) VALUES
(2, 1, 10016, 7, 7700, 700),
(3, 1, 10417, 5, 100000, 5000),
(4, 1, 10511, 2, 40000, 2000),
(5, 2, 10013, 8, 8800, 800),
(6, 2, 10118, 5, 15000, 5000),
(7, 3, 10013, 2, 2200, 200),
(8, 3, 10114, 8, 8000, 800),
(9, 3, 10111, 6, 6000, 400),
(10, 3, 10418, 3, 60000, 3000),
(11, 4, 10022, 7, 7700, 700),
(12, 4, 10211, 5, 5000, 500),
(13, 4, 10117, 2, 2000, 200),
(14, 4, 10113, 4, 14000, 2000),
(15, 4, 10021, 1, 2000, 200),
(16, 5, 10022, 3, 3300, 300),
(19, 6, 10011, 5, 5000, 300),
(20, 6, 10014, 8, 8800, 600);

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_activity`
--

CREATE TABLE `jenis_activity` (
  `id_jenisactivity` int(11) NOT NULL,
  `nama_activity` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jenis_activity`
--

INSERT INTO `jenis_activity` (`id_jenisactivity`, `nama_activity`) VALUES
(1, 'Tambah Supplier'),
(2, 'Tambah Barang'),
(3, 'Edit Barang'),
(4, 'Hapus Barang');

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_karyawan` varchar(20) NOT NULL,
  `jabatan_karyawan` varchar(20) NOT NULL,
  `alamat_karyawan` varchar(50) NOT NULL,
  `notelp_karyawan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `username`, `password`, `nama_karyawan`, `jabatan_karyawan`, `alamat_karyawan`, `notelp_karyawan`) VALUES
(1, 'gustoc45', '32f96b3220c08d39fa4c595b4195776d', 'Gustoc Ginting', 'Gudang', 'Jalan Giri Kencana No. 25', '088976543213'),
(2, 'fahmi33', '1363a84d801daaa06ca826ff2ce2fa80', 'Muhammad Fahmi', 'Kasir', 'Jalan Jalan Mulu Jadian Kaga', '076589231221'),
(3, 'ymgnwn', '74f69ed0feb257150c4805bc914aaa1b', 'Yuma Gunawan', 'Kasir', 'Jalanin Aja Dulu Santuy Kemudian', '085765432109'),
(4, 'miracleboi', 'ba524794817067ecc677800372f64024', 'Kusuma Sandi', 'Manajer', 'Jalan Itu Ke Depan Bos', '099888777666'),
(5, 'prawira90', 'd66abfc4c2090771277b0ceec7176069', 'Ketut Prawira', 'Gudang', 'Jalan Palu Arit No. 13', '076545677890');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_activity`
--

CREATE TABLE `log_activity` (
  `id_activity` int(11) NOT NULL,
  `id_karyawan` int(11) NOT NULL,
  `id_jenisactivity` int(11) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `tanggal_activity` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_activity`
--

INSERT INTO `log_activity` (`id_activity`, `id_karyawan`, `id_jenisactivity`, `keterangan`, `tanggal_activity`) VALUES
(2, 1, 1, '1234', '2020-06-03'),
(3, 1, 2, '10015', '2020-06-03'),
(4, 1, 3, '10018', '2020-06-03'),
(5, 1, 2, '10014', '2020-06-03'),
(6, 1, 4, '10421', '2020-06-03'),
(7, 1, 2, '10421', '2020-06-03'),
(8, 1, 1, '2345', '2020-06-09'),
(9, 1, 2, '19999', '2020-06-09'),
(10, 1, 2, '19999', '2020-06-09'),
(11, 1, 3, '19999', '2020-06-09'),
(12, 1, 4, '19999', '2020-06-09');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_kasir`
--

CREATE TABLE `log_kasir` (
  `id_kasir` int(11) NOT NULL,
  `id_karyawan` int(11) NOT NULL,
  `total_transaksi` int(11) NOT NULL,
  `total_barang` int(11) NOT NULL,
  `total_profit` int(11) NOT NULL,
  `tanggal_transaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_kasir`
--

INSERT INTO `log_kasir` (`id_kasir`, `id_karyawan`, `total_transaksi`, `total_barang`, `total_profit`, `tanggal_transaksi`) VALUES
(1, 3, 147700, 14, 7700, '2020-06-03'),
(2, 3, 23800, 13, 5800, '2020-06-03'),
(3, 2, 76200, 19, 4400, '2020-06-03'),
(4, 3, 30700, 19, 3600, '2020-06-08'),
(5, 3, 0, 0, 0, '2020-06-09'),
(6, 3, 13800, 13, 900, '2020-06-09');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_pengeluaran`
--

CREATE TABLE `log_pengeluaran` (
  `id_tagihan` int(11) NOT NULL,
  `id_karyawan` int(11) NOT NULL,
  `nama_tagihan` varchar(30) NOT NULL,
  `total_tagihan` int(20) NOT NULL,
  `tanggal_tagihan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_pengeluaran`
--

INSERT INTO `log_pengeluaran` (`id_tagihan`, `id_karyawan`, `nama_tagihan`, `total_tagihan`, `tanggal_tagihan`) VALUES
(1, 4, 'Bayar Kuota Pegawai', 6000, '2020-06-03'),
(2, 4, 'Bayar Listrik', 3000, '2020-06-09');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `nama_supplier` varchar(20) NOT NULL,
  `alamat_supplier` varchar(50) NOT NULL,
  `notelp_supplier` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat_supplier`, `notelp_supplier`) VALUES
(1001, 'PT Maju Jaya', 'mayangsari No.2', '081232145918'),
(1002, 'PT Sinar Indah', 'dewata No.9', '081232145978'),
(1003, 'CV Subur', 'palapa No.11', '083132145978'),
(1004, 'CV Makmur', 'badung No.1', '081290145978'),
(1005, 'PT Sejahtera', 'bukit No.69', '089032145978'),
(1234, 'CV Pantang Bangkrut', 'jalan jalan ke kota bandung', '0998762134'),
(2345, 'PT PT PT', 'JALAN JALAN AJA', '909090');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `supplier_barang` (`id_supplier`);

--
-- Indeks untuk tabel `detail_log_kasir`
--
ALTER TABLE `detail_log_kasir`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `detail_kasir` (`id_kasir`),
  ADD KEY `barang_keluar` (`id_barang`);

--
-- Indeks untuk tabel `jenis_activity`
--
ALTER TABLE `jenis_activity`
  ADD PRIMARY KEY (`id_jenisactivity`);

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indeks untuk tabel `log_activity`
--
ALTER TABLE `log_activity`
  ADD PRIMARY KEY (`id_activity`),
  ADD KEY `gudang_activity` (`id_karyawan`),
  ADD KEY `nama_act` (`id_jenisactivity`);

--
-- Indeks untuk tabel `log_kasir`
--
ALTER TABLE `log_kasir`
  ADD PRIMARY KEY (`id_kasir`),
  ADD KEY `tj_kasir` (`id_karyawan`);

--
-- Indeks untuk tabel `log_pengeluaran`
--
ALTER TABLE `log_pengeluaran`
  ADD PRIMARY KEY (`id_tagihan`),
  ADD KEY `manajertagihan` (`id_karyawan`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_log_kasir`
--
ALTER TABLE `detail_log_kasir`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `jenis_activity`
--
ALTER TABLE `jenis_activity`
  MODIFY `id_jenisactivity` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  MODIFY `id_karyawan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `log_activity`
--
ALTER TABLE `log_activity`
  MODIFY `id_activity` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `log_kasir`
--
ALTER TABLE `log_kasir`
  MODIFY `id_kasir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `log_pengeluaran`
--
ALTER TABLE `log_pengeluaran`
  MODIFY `id_tagihan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2346;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `supplier_barang` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`);

--
-- Ketidakleluasaan untuk tabel `detail_log_kasir`
--
ALTER TABLE `detail_log_kasir`
  ADD CONSTRAINT `barang_keluar` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`),
  ADD CONSTRAINT `detail_kasir` FOREIGN KEY (`id_kasir`) REFERENCES `log_kasir` (`id_kasir`);

--
-- Ketidakleluasaan untuk tabel `log_activity`
--
ALTER TABLE `log_activity`
  ADD CONSTRAINT `gudang_activity` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`),
  ADD CONSTRAINT `nama_act` FOREIGN KEY (`id_jenisactivity`) REFERENCES `jenis_activity` (`id_jenisactivity`);

--
-- Ketidakleluasaan untuk tabel `log_kasir`
--
ALTER TABLE `log_kasir`
  ADD CONSTRAINT `tj_kasir` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`);

--
-- Ketidakleluasaan untuk tabel `log_pengeluaran`
--
ALTER TABLE `log_pengeluaran`
  ADD CONSTRAINT `manajertagihan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
