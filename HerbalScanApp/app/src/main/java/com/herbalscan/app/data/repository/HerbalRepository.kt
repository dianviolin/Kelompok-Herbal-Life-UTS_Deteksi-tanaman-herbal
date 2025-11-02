package com.herbalscan.app.data.repository

import com.herbalscan.app.data.model.HerbalPlant

class HerbalRepository {
    
    fun getAllHerbalPlants(): List<HerbalPlant> {
        return listOf(
            HerbalPlant(
                id = "1",
                name = "Jahe",
                latinName = "Zingiber officinale",
                description = "Jahe adalah tanaman rimpang yang sangat populer sebagai rempah-rempah dan bahan obat. Rimpangnya berbentuk jemari yang menggembung di ruas-ruas tengah.",
                benefits = listOf(
                    "Mengatasi masalah pencernaan",
                    "Meredakan mual dan muntah",
                    "Mengurangi peradangan",
                    "Meningkatkan sistem kekebalan tubuh",
                    "Meredakan nyeri otot dan sendi"
                ),
                usage = "Dapat dikonsumsi sebagai minuman hangat (wedang jahe), ditambahkan dalam masakan, atau dibuat menjadi jamu tradisional.",
                imageUrl = "jahe",
                category = "Rimpang"
            ),
            HerbalPlant(
                id = "2",
                name = "Kunyit",
                latinName = "Curcuma longa",
                description = "Kunyit adalah tanaman herbal yang memiliki rimpang berwarna kuning cerah. Telah digunakan selama ribuan tahun dalam pengobatan tradisional dan sebagai bumbu masakan.",
                benefits = listOf(
                    "Anti-inflamasi alami",
                    "Meningkatkan fungsi hati",
                    "Membantu pencernaan",
                    "Antioksidan kuat",
                    "Menjaga kesehatan kulit"
                ),
                usage = "Dapat dikonsumsi sebagai jamu, ditambahkan dalam masakan, atau dibuat menjadi minuman kunyit asam.",
                imageUrl = "kunyit",
                category = "Rimpang"
            ),
            HerbalPlant(
                id = "3",
                name = "Temulawak",
                latinName = "Curcuma xanthorrhiza",
                description = "Temulawak adalah tanaman obat yang berasal dari Indonesia. Rimpangnya berwarna kuning kecoklatan dan memiliki banyak khasiat untuk kesehatan.",
                benefits = listOf(
                    "Meningkatkan nafsu makan",
                    "Menjaga kesehatan liver",
                    "Menurunkan kolesterol",
                    "Anti-inflamasi",
                    "Meningkatkan stamina"
                ),
                usage = "Biasanya dikonsumsi dalam bentuk jamu, ekstrak, atau suplemen herbal.",
                imageUrl = "temulawak",
                category = "Rimpang"
            ),
            HerbalPlant(
                id = "4",
                name = "Lengkuas",
                latinName = "Alpinia galanga",
                description = "Lengkuas atau laos adalah tanaman rimpang yang sering digunakan sebagai bumbu masakan dan obat tradisional. Memiliki aroma khas yang harum.",
                benefits = listOf(
                    "Mengatasi masalah pernapasan",
                    "Anti-bakteri dan anti-jamur",
                    "Meredakan nyeri",
                    "Meningkatkan sirkulasi darah",
                    "Mengatasi masalah kulit"
                ),
                usage = "Digunakan sebagai bumbu masakan, dibuat menjadi minuman herbal, atau dioleskan pada kulit.",
                imageUrl = "lengkuas",
                category = "Rimpang"
            ),
            HerbalPlant(
                id = "5",
                name = "Kencur",
                latinName = "Kaempferia galanga",
                description = "Kencur adalah tanaman rimpang yang populer dalam jamu tradisional Indonesia. Memiliki aroma yang khas dan rasa yang sedikit pedas.",
                benefits = listOf(
                    "Meredakan batuk",
                    "Mengatasi masuk angin",
                    "Meningkatkan stamina",
                    "Melancarkan haid",
                    "Mengatasi sakit kepala"
                ),
                usage = "Sering dibuat menjadi jamu beras kencur, ditambahkan dalam masakan, atau dikonsumsi langsung.",
                imageUrl = "kencur",
                category = "Rimpang"
            )
        )
    }
    
    fun getHerbalPlantById(id: String): HerbalPlant? {
        return getAllHerbalPlants().find { it.id == id }
    }
    
    fun searchHerbalPlants(query: String): List<HerbalPlant> {
        return getAllHerbalPlants().filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.latinName.contains(query, ignoreCase = true)
        }
    }
}
