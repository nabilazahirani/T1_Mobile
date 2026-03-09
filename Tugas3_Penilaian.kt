fun main() {

    println("===== SISTEM PENILAIAN =====\n")

    print("Masukkan Nama Mahasiswa: ")
    val nama = readLine() ?: ""

    val uts = inputNilai("UTS")
    val uas = inputNilai("UAS")
    val tugas = inputNilai("Tugas")

    val nilaiAkhir = (uts * 0.3) + (uas * 0.4) + (tugas * 0.3)

    val grade = when (nilaiAkhir.toInt()) {
        in 85..100 -> "A"
        in 70..84 -> "B"
        in 60..69 -> "C"
        in 50..59 -> "D"
        else -> "E"
    }

    val keterangan = when (grade) {
        "A" -> "Sangat Baik"
        "B" -> "Baik"
        "C" -> "Cukup"
        "D" -> "Kurang"
        else -> "Sangat Kurang"
    }

    val status = if (nilaiAkhir >= 60) "LULUS" else "TIDAK LULUS"

    println("\n===== HASIL PENILAIAN =====")
    println("Nama        : $nama")
    println("Nilai UTS   : $uts (Bobot 30%)")
    println("Nilai UAS   : $uas (Bobot 40%)")
    println("Nilai Tugas : $tugas (Bobot 30%)")
    println("-----------------------------")
    println("Nilai Akhir : %.1f".format(nilaiAkhir))
    println("Grade       : $grade")
    println("Keterangan  : $keterangan")
    println("Status      : $status")

    if (status == "LULUS") {
        println("\nSelamat! Anda dinyatakan LULUS.")
    } else {
        println("\nMaaf, Anda dinyatakan TIDAK LULUS.")
    }
}

fun inputNilai(jenis: String): Double {

    while (true) {

        print("Masukkan Nilai $jenis (0-100): ")
        val input = readLine()

        val nilai = input?.toDoubleOrNull()

        if (nilai != null && nilai in 0.0..100.0) {
            return nilai
        } else {
            println("Input tidak valid! Masukkan angka 0 - 100.")
        }
    }
}