package com.trechina.grpc.repository

import com.trechina.grpc.entity.StockEntity
import com.trechina.proto.StockProto
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import utils.CommUtil


@ApplicationScoped
class StockRepository : PanacheRepository<StockEntity> {
    fun findByCode(stock: StockProto.Stock?): Uni<List<StockEntity>> {
        val sql: String = "select stockDate stockDate,storeCode storeCode,productCode productCode,sum(quantity) quantity from init_stock where"+
                " stockDate = :stockDate and storeCode = :storeCode and productCode = :productCode"+
                " group by stockDate,storeCode,productCode"

        val timestampToLocalDate = CommUtil.timestampToLocalDate(stock!!.stockDate)
        return find(sql, Parameters.with("stockDate", timestampToLocalDate)
                .and("storeCode", stock.storeCode)
                .and("productCode", stock.productCode)).project(StockEntity::class.java).list()
    }
}