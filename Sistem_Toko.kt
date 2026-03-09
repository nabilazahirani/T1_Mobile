data class Produk(
    val id: Int,
    val nama: String,
    val harga: Double,
    val kategori: String,
    var stok: Int
) : Discountable

data class CartItem(
    val produk: Produk,
    var jumlah: Int
)

data class Customer(
    val id: Int,
    val nama: String,
    val email: String,
    val alamat: String?
)

data class Order(
    val id: Int,
    val customer: Customer,
    val items: List<CartItem>,
    var status: OrderStatus,
    val paymentMethod: PaymentMethod,
    val totalHarga: Double
)

sealed class OrderStatus {
    object Pending : OrderStatus()
    object Processing : OrderStatus()
    object Shipped : OrderStatus()
    object Delivered : OrderStatus()
    object Cancelled : OrderStatus()
}

sealed class PaymentMethod {
    object Cash : PaymentMethod()
    object Transfer : PaymentMethod()
    object EWallet : PaymentMethod()
}

interface Discountable {
    fun applyDiscount(price: Double, discount: Double): Double {
        return price - (price * discount / 100)
    }
}

class ShoppingCart {

    private val items = mutableListOf<CartItem>()

    fun tambahProduk(produk: Produk, jumlah: Int) {
        val item = items.find { it.produk.id == produk.id }

        if (item != null) {
            item.jumlah += jumlah
        } else {
            items.add(CartItem(produk, jumlah))
        }

        println("${produk.nama} ditambahkan ke keranjang.")
    }

    fun hapusProduk(produkId: Int) {
        items.removeIf { it.produk.id == produkId }
        println("Produk dihapus dari keranjang.")
    }

    fun lihatKeranjang() {
        println("\n===== KERANJANG BELANJA =====")

        items.forEach {
            println("${it.produk.nama} x${it.jumlah} = ${it.produk.harga * it.jumlah}")
        }
    }

    fun hitungTotal(discountCalculator: (Double) -> Double): Double {
        val total = items.sumOf { it.produk.harga * it.jumlah }
        return discountCalculator(total)
    }

    fun getItems(): List<CartItem> {
        return items
    }

    fun kosongkan() {
        items.clear()
    }
}

class TokoOnline {

    val daftarProduk = mutableListOf<Produk>()
    val riwayatOrder = mutableListOf<Order>()

    fun tambahProduk(produk: Produk) {
        daftarProduk.add(produk)
    }

    fun tampilkanProduk() {

        println("\n===== DAFTAR PRODUK =====")

        daftarProduk
            .sortedBy { it.harga }
            .forEach {
                println("${it.id}. ${it.nama} | ${it.kategori} | Rp${it.harga} | Stok: ${it.stok}")
            }
    }

    fun filterKategori(kategori: String) {
        println("\nProduk kategori $kategori")

        daftarProduk
            .filter { it.kategori == kategori }
            .forEach { println(it.nama) }
    }

    fun checkout(
        cart: ShoppingCart,
        customer: Customer,
        payment: PaymentMethod,
        total: Double
    ) {

        val order = Order(
            id = riwayatOrder.size + 1,
            customer = customer,
            items = cart.getItems(),
            status = OrderStatus.Pending,
            paymentMethod = payment,
            totalHarga = total
        )

        riwayatOrder.add(order)

        println("\nCheckout berhasil!")
        println("Total bayar: Rp$total")
        println("Status pesanan: Pending")
    }

    fun tampilkanRiwayat() {

        println("\n===== RIWAYAT PESANAN =====")

        riwayatOrder.forEach {
            println("Order ${it.id} - ${it.customer.nama} - Total Rp${it.totalHarga}")
        }
    }
}

fun main() {

    val toko = TokoOnline()

    toko.tambahProduk(Produk(1, "Laptop", 10000000.0, "Elektronik", 5))
    toko.tambahProduk(Produk(2, "Mouse", 150000.0, "Elektronik", 20))
    toko.tambahProduk(Produk(3, "Buku Kotlin", 120000.0, "Buku", 10))

    toko.tampilkanProduk()

    val cart = ShoppingCart()

    cart.tambahProduk(toko.daftarProduk[0], 1)
    cart.tambahProduk(toko.daftarProduk[1], 2)

    cart.lihatKeranjang()

    val total = cart.hitungTotal { total ->
        val diskon = 10
        total - (total * diskon / 100)
    }

    println("Total setelah diskon: Rp$total")

    val customer = Customer(
        1,
        "Budi Santoso",
        "budi@email.com",
        null
    )

    toko.checkout(
        cart,
        customer,
        PaymentMethod.Transfer,
        total
    )

    toko.tampilkanRiwayat()
}