package com.kaushikam.transactions.`interface`.web.rest.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.kaushikam.transactions.application.IPaymentService
import com.kaushikam.transactions.domain.model.payment.PaymentRequestDTO
import com.kaushikam.transactions.domain.model.payment.PaymentResponseDTO
import com.kaushikam.transactions.domain.model.payment.PaymentStatus
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/payment")
class PaymentController(
    private val paymentService: IPaymentService
) {

    @PostMapping("/orders", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun placeOrder(@RequestBody request: PaymentRequestDTO): ResponseEntity<PaymentOrderDetailsDTO> {
        val information = paymentService.placeOrder(request, LocalDateTime.now())
        return ResponseEntity(
            PaymentOrderDetailsDTO(information.id!!, information.createdOn,
                information.request.amount, information.paymentStatus),
            HttpStatus.CREATED
        )
    }

    @Transactional
    @PostMapping("/orders/{orderId}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun makePayment(@RequestBody response: PaymentResponseDTO,
                    @PathVariable("orderId") informationId: Long): ResponseEntity<ReceiptDTO> {
        val responseTime = LocalDateTime.now()
        val information = paymentService.savePaymentResponse(informationId, response, responseTime)
        val receiptCreationTime = LocalDateTime.now()
        paymentService.makePayment(information, receiptCreationTime)
        val receipt = information.receipt ?: throw IllegalArgumentException("There is no such receipt")
        return ResponseEntity(
            ReceiptDTO(receipt.id!!, receipt.amount, receipt.createdOn),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/orders")
    fun listAllOrders(): ResponseEntity<List<PaymentOrderDetailsDTO>> {
        val list = paymentService.listAllInformation()
        return ResponseEntity(
            list.map { PaymentOrderDetailsDTO(it.id!!, it.createdOn, it.request.amount, it.paymentStatus, it.responseCameOn,
                if (it.receipt != null)
                    ReceiptDTO(it.receipt!!.id!!, it.receipt!!.amount, it.receipt!!.createdOn)
                else null
            ) },
            HttpStatus.OK
        )
    }
}

data class ReceiptDTO(
    val receiptNumber: Long,
    val amount: Double,
    val createdOn: LocalDateTime
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentOrderDetailsDTO(
    val informationId: Long,
    val createdOn: LocalDateTime,
    val amount: Double,
    val status: PaymentStatus,
    val responseTime: LocalDateTime? = null,
    val receipt: ReceiptDTO? = null
)