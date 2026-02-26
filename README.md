# SauceDemo Selenium Automation

Proyek ini adalah *framework* otomatisasi pengujian *End-to-End* (E2E) untuk website [SauceDemo](https://www.saucedemo.com/). Dibangun menggunakan Java, Selenium WebDriver, dan TestNG dengan menerapkan desain pola **Page Object Model (POM)**.

## 🚀 Fitur Utama
* **Page Object Model (POM):** Pemisahan logika pengujian dengan elemen UI untuk memudahkan pemeliharaan kode.
* **Data-Driven Testing:** Menggunakan file `staging.properties` untuk manajemen kredensial dan data uji tanpa *hardcoding*.
* **Robust Interaction:** Implementasi *JavascriptExecutor* untuk menangani elemen yang sulit diklik (*Silent Click Failure*).
* **Automated Reporting:** Laporan pengujian interaktif menggunakan **ExtentReports** lengkap dengan *screenshot* otomatis saat terjadi kegagalan (*on failure*).
* **Synchronization:** Penggunaan *Explicit Waits* dan jeda strategis untuk menangani render halaman yang dinamis.

## 🛠️ Teknologi yang Digunakan
* **Bahasa:** Java 17
* **Build Tool:** Gradle
* **Testing Framework:** TestNG
* **Browser Driver:** Selenium WebDriver (Chrome)
* **Reporting:** ExtentReports

## 📂 Struktur Proyek
```text
src/
├── main/java/
│   ├── core/                 # Konfigurasi dasar (BasePage)
│   └── saucedemo/
│       ├── core/             # Driver Management (DriverManager)
│       ├── CartPage.java     # Page Object: Keranjang
│       ├── CheckoutPage.java # Page Object: Form Data Diri
│       ├── HomePage.java     # Page Object: Dashboard Produk
│       └── LoginPage.java    # Page Object: Login Akun
├── test/java/saucedemo/
│   ├── core/                 # Test Logic (BaseTest, TestListener)
│   └── home/                 # Test Scripts (SauceDemoE2ETest)
│   └── login/                # Test Login (SauceDemoE2ETest)
└── test/resources/
    ├── config/               # staging.properties (Data Uji)
    ├── data/                 # Data Login-Data-Tests.xlsx
    └── suites/               # smoke.xml, sanity.xml, log4j2 (Test Suite)
```
---
## ⚙️ Persiapan dan Instalasi
1. **Clone Repositori**:
   ```bash
   git clone https://github.com/diazaryanta/saucedemo-selenium-automation.git 
   ```
   
2. **Konfigurasi Environment**:
* Pastikan **Java 17** sudah terinstal.
* Gunakan **IntelliJ IDEA** untuk membuka proyek ini sebagai proyek Gradle.
* Lakukan **Reload Gradle Project** jika diperlukan.

3. **Setup Data Uji**:
* Buka file `src/test/resources/config/staging.properties`.
* Sesuaikan data kredensial sesuai kebutuhan (firstName, lastName, dan zip).
