
/**
 * @author ahmadluky
 */

/**
 * Rule System
 * ===========
 * [1] Tedapat 2 file konfigurasi uri Rss media online (rss.conf) dan uri (list_uri.conf) 
 * online (web resmi partai) yang kemungkinan ada nama tokoh yang akan digunakan
 * [2] Sistem membaca rss.cof yang kemudian xml file disimpan di dir /xml (done)
 * [3] Sistem membaca xml file dan mengambil uri dari setiap judul berita yang selanjutnya di <append> ke file list_uri.conf
 * [4] Sistem membaca file list_uri dan mendapatkan html code dari server (internet) disimpan di /html
 * [5] lakukan proses DOM dan REGEX untuk mendapatkan nama pada berita.
 */
