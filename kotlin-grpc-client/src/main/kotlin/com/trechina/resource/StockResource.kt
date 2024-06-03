package com.trechina.resource

import com.trechina.model.StockVO
import com.trechina.proto.StockGrpcServiceGrpc.StockGrpcServiceBlockingStub
import com.trechina.proto.StockProto.Stock
import io.quarkus.grpc.GrpcClient
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
class StockResource {
    @GrpcClient("stockService")
    lateinit var stockService: StockGrpcServiceBlockingStub

    @POST
    @Path("/list")
    fun getStockList(stockVO: StockVO): List<StockVO> {
        val stock = Stock.newBuilder()
            .setStockDate(stockVO.stockDate)
            .setProductCode(stockVO.productCode)
            .setStoreCode(stockVO.storeCode).build()

        val map = stockService.findByCode(stock).stockResponseList.map { item ->
            StockVO(item.productCode, item.stockDate, item.storeCode, item.quantity)
        }

        return map
    }

}