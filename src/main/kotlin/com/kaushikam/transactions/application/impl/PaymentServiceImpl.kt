package com.kaushikam.transactions.application.impl

import com.kaushikam.transactions.application.IPaymentService
import com.kaushikam.transactions.domain.model.payment.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {  }

@Service
class PaymentServiceImpl(
    private val informationRepository: IPaymentInformationRepository,
    private val receiptRepository: IReceiptRepository,
    @Value("\${application.response.maxAttempts}")
    private val maxResponseAttempts: Int
): IPaymentService {
    @Transactional(rollbackFor = [Exception::class])
    override fun placeOrder(paymentRequest: PaymentRequestDTO,
                            requestTime: LocalDateTime): PaymentInformation {
        val paymentInformation = PaymentInformation(createdOn = requestTime, request = paymentRequest,
            maximumAllowedAttempts = maxResponseAttempts)
        informationRepository.save(paymentInformation)
        return paymentInformation
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun savePaymentResponse(informationId: Long,
                                     paymentResponse: PaymentResponseDTO,
                                     responseTime: LocalDateTime): PaymentInformation {
        val paymentInformation = informationRepository.findById(informationId)
            ?: throw IllegalArgumentException("There is no such payment information with id $informationId")
        paymentInformation.receiveResponse(paymentResponse, responseTime)
        informationRepository.save(paymentInformation)
        return paymentInformation
    }

    @Transactional
    override fun makePayment(paymentInformation: PaymentInformation,
                             receiptCreationTime: LocalDateTime): PaymentInformation {
        val receipt = paymentInformation.generateReceipt(receiptCreationTime)
        if (receipt?.persisted() == false) {
            receiptRepository.save(receipt)
        }
        logger.info { "Receipt id is ${paymentInformation.receipt!!.id}" }
        informationRepository.save(paymentInformation)
        throw IllegalArgumentException("Testing whether response alone gets saved")
        return paymentInformation
    }

    override fun listAllInformation(): List<PaymentInformation> {
        return this.informationRepository.findAll()
    }
}