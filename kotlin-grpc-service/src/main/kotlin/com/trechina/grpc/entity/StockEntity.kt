package com.trechina.grpc.entity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate

@Entity(name = "init_stock")
data class StockEntity(
    @Id @GeneratedValue
    open val id:Long = 0,
    @Column(name = "stock_date")
    open val stockDate: LocalDate? = null,
    @Column(name = "store_code")
    open val storeCode: Long = 0,
    @Column(name = "product_code")
    open val productCode: String = "",
    @Column(name = "quantity")
    open val quantity: Double = 0.0){
    constructor() : this(0, LocalDate.now(), 0L, "", 0.0)
}