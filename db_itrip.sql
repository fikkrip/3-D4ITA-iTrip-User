-- phpMyAdmin SQL Dump
-- version 4.5.0.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 12, 2017 at 07:38 AM
-- Server version: 10.0.17-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_itrip`
--

-- --------------------------------------------------------

--
-- Table structure for table `detailplanning`
--

CREATE TABLE `detailplanning` (
  `idDetail` int(5) NOT NULL,
  `idPlanning` int(5) NOT NULL,
  `idWisata` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `idEvent` int(5) NOT NULL,
  `idProvinsi` int(5) NOT NULL,
  `namaEvent` varchar(50) NOT NULL,
  `tglEvent` date NOT NULL,
  `deskripsiEvent` text NOT NULL,
  `lokasiEvent` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `foto`
--

CREATE TABLE `foto` (
  `idFoto` int(5) NOT NULL,
  `idUser` int(5) NOT NULL,
  `judul` varchar(50) NOT NULL,
  `file` text NOT NULL,
  `tglUpload` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `migration`
--

CREATE TABLE `migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `migration`
--

INSERT INTO `migration` (`version`, `apply_time`) VALUES
('m000000_000000_base', 1506436508),
('m130524_201442_init', 1506436649);

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `idUser` int(5) NOT NULL,
  `username` varchar(30) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(15) NOT NULL,
  `fotoProfil` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`idUser`, `username`, `nama`, `email`, `password`, `fotoProfil`) VALUES
(1, 'Pinky', 'Pinky', 'pinky@gmail.com', 'pinky', '');

-- --------------------------------------------------------

--
-- Table structure for table `planning`
--

CREATE TABLE `planning` (
  `idPlanning` int(5) NOT NULL,
  `idUser` int(5) NOT NULL,
  `judul` text NOT NULL,
  `status` varchar(10) NOT NULL,
  `note` text NOT NULL,
  `rating` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `planning`
--

INSERT INTO `planning` (`idPlanning`, `idUser`, `judul`, `status`, `note`, `rating`) VALUES
(1, 1, 'Perjalanan ke Danau Toba', '1', 'Pada hari Minggu 26 November 2017', 1);

-- --------------------------------------------------------

--
-- Table structure for table `popular`
--

CREATE TABLE `popular` (
  `idPopular` int(5) NOT NULL,
  `idWisata` int(5) NOT NULL,
  `idUser` int(5) NOT NULL,
  `rating` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `provinsi`
--

CREATE TABLE `provinsi` (
  `idProvinsi` int(5) NOT NULL,
  `namaProvinsi` varchar(50) NOT NULL,
  `idPulau` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `provinsi`
--

INSERT INTO `provinsi` (`idProvinsi`, `namaProvinsi`, `idPulau`) VALUES
(1, 'Nanggroe Aceh Darussalam', 1),
(2, 'Sumatera Utara', 1),
(3, 'Sumatera Barat', 1),
(4, 'Riau', 1),
(5, 'Kepulauan Riau', 1),
(6, 'Jambi', 1),
(7, 'Bengkulu', 1),
(8, 'Sumatera Selatan', 1),
(9, 'Kepulauan Bangka Belitung', 1),
(10, 'Lampung', 1),
(11, 'DKI Jakarta', 2),
(12, 'Jawa Barat', 2),
(13, 'Banten', 2),
(14, 'Jawa Tengah', 2),
(15, 'Daerah Istimewa Yogyakarta', 2),
(16, 'Jawa Timur', 2),
(17, 'Bali', 2),
(18, 'Nuga Tenggara Barat', 2),
(19, 'Nusa Tenggara Timur', 2),
(20, 'Kalimantan Barat', 3),
(21, 'Kalimantan Tengah', 3),
(22, 'Kalimantan Selatan', 3),
(23, 'Kalimantan Timur', 3),
(24, 'Kalimantan Utara', 3),
(25, 'Sulawesi Utara', 4),
(26, 'Sulawesi Barat', 4),
(27, 'Sulawesi Tengah', 4),
(28, 'Sulawesi Tenggara', 4),
(29, 'Sulawesi Selatan', 4),
(30, 'Gorontalo', 4),
(31, 'Maluku', 5),
(32, 'Maluku Utara', 5),
(33, 'Papua Barat', 5),
(34, 'Papua', 5);

-- --------------------------------------------------------

--
-- Table structure for table `pulau`
--

CREATE TABLE `pulau` (
  `idPulau` int(5) NOT NULL,
  `namaPulau` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pulau`
--

INSERT INTO `pulau` (`idPulau`, `namaPulau`) VALUES
(1, 'Sumatera'),
(2, 'Jawa'),
(3, 'Kalimantan'),
(4, 'Sulawesi'),
(5, 'Papua');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `auth_key` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password_reset_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` smallint(6) NOT NULL DEFAULT '10',
  `created_at` int(11) NOT NULL,
  `updated_at` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `auth_key`, `password_hash`, `password_reset_token`, `email`, `status`, `created_at`, `updated_at`) VALUES
(1, 'adminitrip', 'SaEt7mltRja1TOKETcuZzCwxYL_0r48y', '$2y$13$XHl06pq7pOic6Cpe2x3rr.JQQQkIzIMIk3Cc2dbvebzCqmMBjZ8Am', NULL, 'adminitrip@gmail.com', 10, 1506436803, 1506436803);

-- --------------------------------------------------------

--
-- Table structure for table `wisata`
--

CREATE TABLE `wisata` (
  `idWisata` int(11) NOT NULL,
  `idProvinsi` int(5) NOT NULL,
  `namaWisata` varchar(50) NOT NULL,
  `deskripsiWisata` text NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `biayaMasuk` int(15) NOT NULL,
  `lokasiWisata` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wisata`
--

INSERT INTO `wisata` (`idWisata`, `idProvinsi`, `namaWisata`, `deskripsiWisata`, `kategori`, `biayaMasuk`, `lokasiWisata`) VALUES
(1, 1, 'Mesjid Raya Baiturrahman', 'Mesjid dibangun oleh Sultan Iskandar Muda pada tahun 1612 merupakan ikon Aceh. Di depan mesjid mengingatkan pada Taj Mahal di India. Mesjid ini masuk ke dalam daftar 100 mesjid terindah di dunia. ', 'Wisata Alam', 3000, 'Aceh'),
(2, 1, 'Air Terjun Blang Kolam', 'Disini kita bisa melihat air terjun kembar dengan tinggi 75 meter yang dikelilingi oleh pepohonan rindang. Di Air Terjun Blang Kolam ini Anda juga bisa berkemah dan menikmati keindahan alam dengan bebas.', 'Wisata Alam', 5000, 'Desa SidoMulyo, Aceh Utara, 30 menit dari Lhokseumawe'),
(3, 1, 'Air Terjun Suhom', 'Untuk bisa mencapai tempat wisata ini, kita harus melewati jalan naik-turun dengan pemandangan pegunungan Paro dan Kulu. Banyak monyet berkeliaran di jalan untuk menuju Air terjun ini. Air terjun setinggi 50 meter dibagi dengan tiga tingkat. Selain itu terdapat gazebo yang telah disediakan sambil menikmati makanan yang dijual di sekitar air terjun.', 'Wisata Alam', 5000, 'Desa Suhom, Kecamatan Lhoong, Aceh Besar.'),
(4, 1, 'Pantai Lampuk', 'Pantai Lampuk juga disebut sebagai Pantai Kuta di Aceh. Di pantai ini Anda bisa berselancar, berjemur, berenag dan bermain Boat.Kemudian anda juga dapat melihat upaya pelestarian penyu. Selain itu Anda dapat memuaskan perut dengan aneka sajian ikan bakar di warung sekitar.', 'Wisata Alam', 3000, 'Desa Meunasah Masjid, Lhoknga, Aceh Besar');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detailplanning`
--
ALTER TABLE `detailplanning`
  ADD PRIMARY KEY (`idDetail`),
  ADD KEY `idPlanning` (`idPlanning`),
  ADD KEY `idWisata` (`idWisata`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`idEvent`),
  ADD KEY `idProvinsi` (`idProvinsi`);

--
-- Indexes for table `foto`
--
ALTER TABLE `foto`
  ADD PRIMARY KEY (`idFoto`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `migration`
--
ALTER TABLE `migration`
  ADD PRIMARY KEY (`version`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`idUser`);

--
-- Indexes for table `planning`
--
ALTER TABLE `planning`
  ADD PRIMARY KEY (`idPlanning`),
  ADD KEY `idPlanning` (`idPlanning`),
  ADD KEY `idPlanning_2` (`idPlanning`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `popular`
--
ALTER TABLE `popular`
  ADD PRIMARY KEY (`idPopular`),
  ADD KEY `idWisata` (`idWisata`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `provinsi`
--
ALTER TABLE `provinsi`
  ADD PRIMARY KEY (`idProvinsi`),
  ADD KEY `idPulau` (`idPulau`);

--
-- Indexes for table `pulau`
--
ALTER TABLE `pulau`
  ADD PRIMARY KEY (`idPulau`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `password_reset_token` (`password_reset_token`);

--
-- Indexes for table `wisata`
--
ALTER TABLE `wisata`
  ADD PRIMARY KEY (`idWisata`),
  ADD KEY `idProvinsi` (`idProvinsi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detailplanning`
--
ALTER TABLE `detailplanning`
  MODIFY `idDetail` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `idEvent` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `foto`
--
ALTER TABLE `foto`
  MODIFY `idFoto` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `idUser` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `planning`
--
ALTER TABLE `planning`
  MODIFY `idPlanning` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `provinsi`
--
ALTER TABLE `provinsi`
  MODIFY `idProvinsi` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT for table `pulau`
--
ALTER TABLE `pulau`
  MODIFY `idPulau` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `wisata`
--
ALTER TABLE `wisata`
  MODIFY `idWisata` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `detailplanning`
--
ALTER TABLE `detailplanning`
  ADD CONSTRAINT `detailplanning_ibfk_1` FOREIGN KEY (`idPlanning`) REFERENCES `planning` (`idPlanning`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detailplanning_ibfk_2` FOREIGN KEY (`idWisata`) REFERENCES `wisata` (`idWisata`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`idProvinsi`) REFERENCES `provinsi` (`idProvinsi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `foto`
--
ALTER TABLE `foto`
  ADD CONSTRAINT `foto_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `pengguna` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `planning`
--
ALTER TABLE `planning`
  ADD CONSTRAINT `planning_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `pengguna` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `popular`
--
ALTER TABLE `popular`
  ADD CONSTRAINT `popular_ibfk_1` FOREIGN KEY (`idWisata`) REFERENCES `wisata` (`idWisata`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `popular_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `pengguna` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `provinsi`
--
ALTER TABLE `provinsi`
  ADD CONSTRAINT `provinsi_ibfk_1` FOREIGN KEY (`idPulau`) REFERENCES `pulau` (`idPulau`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wisata`
--
ALTER TABLE `wisata`
  ADD CONSTRAINT `wisata_ibfk_1` FOREIGN KEY (`idProvinsi`) REFERENCES `provinsi` (`idProvinsi`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
