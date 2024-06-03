package com.trechina.grpc.service

import com.trechina.grpc.entity.StockEntity
import com.trechina.grpc.repository.StockRepository
import com.trechina.proto.StockGrpcService
import com.trechina.proto.*
import io.quarkus.grpc.GrpcService
import io.smallrye.common.annotation.Blocking
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import utils.CommUtil


@Blocking
@GrpcService
class StockServiceImpl : StockGrpcService {
    @Inject
    lateinit var stockRepository: StockRepository

    override fun findByCode(request: StockProto.Stock?): Uni<StockProto.Stocks> {
        val result = stockRepository.findByCode(request!!)
        return Uni.createFrom().item(result).map(this::mapToStocks)
    }

    private fun mapToStocks(list: List<StockEntity>): StockProto.Stocks {
        val builder: StockProto.Stocks.Builder = StockProto.Stocks.newBuilder()
        list.forEach { p: StockEntity -> builder.addStockResponse(mapToStock(p)) }
        return builder.build()
    }

    private fun mapToStock(entity: StockEntity): StockProto.StockResponse? {
        val builder: StockProto.StockResponse.Builder = StockProto.StockResponse.newBuilder()
        val localDateToTimestamp = CommUtil.localDateToStr(entity.stockDate!!)
        return builder
            .setStockDate(localDateToTimestamp)
            .setProductCode(entity.productCode)
            .setQuantity(entity.quantity)
            .setStoreCode(entity.storeCode)
            .build()
    }
}