package com.trechina.grpc.repository

import com.trechina.grpc.entity.StockEntity
import com.trechina.proto.StockProto
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hibernate.query.TupleTransformer
import utils.CommUtil


@ApplicationScoped
class StockRepository : PanacheRepository<StockEntity> {
    @PersistenceContext
    lateinit var em: EntityManager
    fun findByCode(stock: StockProto.Stock?): List<StockEntity> {
        val sql: String = "select to_char(stock_date, 'YYYY-MM-DD') stockDate,store_code storeCode,product_code productCode,sum(quantity) quantity from init_stock where"+
                " stock_date = :stockDate and store_code = :storeCode and product_code = :productCode"+
                " group by stockDate,storeCode,productCode"

        val timestampToLocalDate = CommUtil.strToLocalDate(stock!!.stockDate)

        val query = em.createNativeQuery(sql)
        query.setParameter("stockDate", timestampToLocalDate)
        query.setParameter("storeCode", stock.storeCode)
        query.setParameter("productCode", stock.productCode)
        query.unwrap(org.hibernate.query.Query::class.java).setTupleTransformer(TupleTransformer { tuple, aliases ->
            val stockDate = CommUtil.strToLocalDate(tuple[0] as String)
            val storeCode = tuple[1] as Long
            val productCode = tuple[2] as String
            val quantity = tuple[3] as Double
            StockEntity(0, stockDate, storeCode, productCode, quantity)
        })

        return query.resultList as List<StockEntity>
    }
}