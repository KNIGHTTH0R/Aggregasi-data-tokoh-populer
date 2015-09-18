# Aggregasi Data

#### Deskripsi
Applikasi yang dibuat dalam bahasa pemrograman java untuk mencari tokoh yang dibicarakan pada media online (contoh : detik.com, kompas.com, tempo.com dll). 

Rss media online digunakan dalam program ini. Hasil yang didapatkan adalah list tokoh yang atau yang di bicarakan pada artikel media tersebut. Selanjutnya program akan mengambil data dari twitter berdasarkan keyword list tokoh tersebut.

Sebelum applikasi meng-*get* data dari twitter pengguna (user) menambahkan kemungkinan keyword-keyword yang yang mungkin orang tulisakan pada media sosial yang mereferesi pada tokoh-tokoh tersebut.

Contoh :

```
Jusuf Kalla : @Pak_JK , pak Kalla , JK
```

Library yang digunakan :

Twitter4j  <a href="twitter4j.org"> http://twitter4j.org</a>

#### Media Penyimpanan

Data yang didapatkan disimpan pada :

1. Csv File
2. MongoDb

