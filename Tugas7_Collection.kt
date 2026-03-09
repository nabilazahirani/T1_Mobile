data class Mahasiswa(
    val nim: String,
    val nama: String,
    val mataKuliah: String,
    val nilai: Int
)

fun hitungGrade(nilai: Int): String {
    return when {
        nilai >= 85 -> "A"
        nilai >= 75 -> "B"
        nilai >= 65 -> "C"
        nilai >= 50 -> "D"
        else -> "E"
    }
}

fun main() {

    val daftarMahasiswa = mutableListOf<Mahasiswa>()

    println("Masukkan jumlah mahasiswa:")
    val jumlah = readLine()!!.toInt()

    for (i in 1..jumlah) {
        println("\nMahasiswa ke-$i")

        print("NIM : ")
        val nim = readLine()!!

        print("Nama : ")
        val nama = readLine()!!

        print("Nilai : ")
        val nilai = readLine()!!.toInt()

        val mataKuliah = "Pemrograman"

        daftarMahasiswa.add(Mahasiswa(nim, nama, mataKuliah, nilai))
    }

    println("\n===== DATA NILAI MAHASISWA =====")
    println("No  NIM       Nama              MataKuliah       Nilai")

    daftarMahasiswa.forEachIndexed { index, m ->
        println("${index + 1}   ${m.nim}   ${m.nama}      ${m.mataKuliah}      ${m.nilai}")
    }

    // Statistik
    val total = daftarMahasiswa.size
    val rata = daftarMahasiswa.map { it.nilai }.average()

    val tertinggi = daftarMahasiswa.maxByOrNull { it.nilai }
    val terendah = daftarMahasiswa.minByOrNull { it.nilai }

    println("\n===== STATISTIK =====")
    println("Total Mahasiswa : $total")
    println("Rata-rata Nilai : %.1f".format(rata))
    println("Nilai Tertinggi : ${tertinggi?.nilai} (${tertinggi?.nama})")
    println("Nilai Terendah  : ${terendah?.nilai} (${terendah?.nama})")

    // Mahasiswa Lulus
    println("\n===== MAHASISWA LULUS =====")

    var nomor = 1
    daftarMahasiswa.filter { it.nilai >= 65 }.forEach {
        val grade = hitungGrade(it.nilai)
        println("$nomor. ${it.nama} - ${it.nilai} ($grade)")
        nomor++
    }

    // Hitung Grade
    val gradeCount = mutableMapOf(
        "A" to 0,
        "B" to 0,
        "C" to 0,
        "D" to 0,
        "E" to 0
    )

    daftarMahasiswa.forEach {
        val grade = hitungGrade(it.nilai)
        gradeCount[grade] = gradeCount[grade]!! + 1
    }

    println("\n===== JUMLAH PER GRADE =====")
    gradeCount.forEach { (grade, jumlah) ->
        println("Grade $grade: $jumlah mahasiswa")
    }
}