package com.trechina.resource

import com.google.protobuf.Timestamp
import com.trechina.model.StockVO
import com.trechina.proto.StockGrpcService
import com.trechina.proto.StockProto
import com.trechina.proto.StockProto.Stock
import com.trechina.proto.StockProto.Stocks
import io.quarkus.grpc.GrpcClient
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.time.ZoneId

@Path("/stock")
@Produces(MediaType.TEXT_PLAIN)
class StockResource {
    @GrpcClient("stockService")
    lateinit var stockService: StockGrpcService

    @POST
    @Path("/list")
    fun getStockList(stockVO: StockVO): Uni<MutableList<StockProto.StockResponse>>? {
        val instant = stockVO.stockDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val stockDate = Timestamp.newBuilder().setSeconds(instant.epochSecond).setNanos(instant.nano).build()
        val stock = Stock.newBuilder()
            .setStockDate(stockDate)
            .setProductCode(stockVO.productCode)
            .setStoreCode(stockVO.storeCode).build()
        return stockService.findByCode(stock).onItem().transform (Stocks::getStockResponseList)
    }

}