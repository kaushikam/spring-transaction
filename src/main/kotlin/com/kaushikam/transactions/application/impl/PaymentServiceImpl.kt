package com.kaushikam.transactions.application.impl

import com.kaushikam.transactions.application.IPaymentService
import com.kaushikam.transactions.common.exception.BaseRuntimeException
import com.kaushikam.transactions.common.exception.ErrorCode
import com.kaushikam.transactions.domain.model.payment.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
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
        val paymentInformation = findPaymentInformation(informationId)
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
        return paymentInformation
    }

    override fun listAllInformation(): List<PaymentInformation> {
        return this.informationRepository.findAll()
    }

    private fun findPaymentInformation(informationId: Long): PaymentInformation {
        return informationRepository.findById(informationId)
            ?: throw PaymentInformationNotFoundException(informationId = informationId)
    }
}

class PaymentInformationNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PAYMENT_INFORMATION_NOT_FOUND,
    informationId: Long
): BaseRuntimeException("There is no payment information id found with id $informationId", errorCode, arrayOf(informationId), null)