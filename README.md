# Project UTS: Deteksi Tanaman Herbal

## Anggota Kelompok
1. Dian Violin Kapisa 202304560051
2. Debora Septina Siska Mendrofa 202304560043
3. Vonny Tansil Wijaya 202304560050
4. Felisia Wulandari Sanadi 202304560012

## Deskripsi
Proyek ini menggunakan YOLOv8 untuk mendeteksi berbagai jenis tanaman herbal dari gambar.

## Struktur Folder
- dataset/: dataset gambar dan label
- models/: menyimpan model hasil training
- outputs/: hasil deteksi
- train.py: script training
- detect.py: script deteksi

## Cara Setup
1. Buat virtual environment Python
2. Install dependencies:
pip install -r requirements.txt
3. Jalankan training:
python train.py
4. Jalankan deteksi:
python detect.py --source dataset/images/img1.jpg
