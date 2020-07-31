package com.kaushikam.transactions.domain.model.payment

import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payment_information")
@TypeDef(
    name = "json",
    typeClass = JsonStringType::class
)
class PaymentInformation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "created_on")
    val createdOn: LocalDateTime,

    @Type(type = "json")
    @Column(name = "request")
    val request: PaymentRequestDTO,

    @Column(name = "max_allowed_attempts")
    val maximumAllowedAttempts: Int
) {
    @Enumerated
    @Column(name = "payment_status", columnDefinition = "smallint")
    final var paymentStatus: PaymentStatus = PaymentStatus.INITIATED
        private set

    @Column(name = "attempt_no")
    final var numberOfAttempts: Int = 0

    @Type(type = "json")
    @Column(name = "response")
    final var response: PaymentResponseDTO? = null
        private set

    @Column(name = "response_came_on")
    final var responseCameOn: LocalDateTime? = null
        private set

    @OneToOne(mappedBy = "paymentInformation", cascade = [CascadeType.ALL], optional = false, fetch = FetchType.LAZY)
    final var receipt: Receipt? = null
        private set

    fun receiveResponse(response: PaymentResponseDTO, responseTime: LocalDateTime) {
        this.numberOfAttempts++

        if (this.response == null) {
            this.response = response
            this.responseCameOn = responseTime
            this.paymentStatus = PaymentStatus.RESPONSE_CAME
        }
    }

    fun generateReceipt(createdOn: LocalDateTime): Receipt? {
        if (this.numberOfAttempts <= maximumAllowedAttempts) {
            if (receipt?.persisted() == true) {
                return receipt
            }

            this.paymentStatus = if (this.response!!.amount == request.amount) {
                PaymentStatus.SUCCESS
            } else {
                PaymentStatus.FAILURE
            }

            return if (this.paymentStatus == PaymentStatus.SUCCESS) {
                this.receipt = Receipt(amount = request.amount, createdOn = createdOn)
                this.receipt!!.paymentInformation = this
                this.receipt!!
            } else {
                null
            }
        }

        throw MaximumAttemptsExceededException(maximumAllowedAttempts)
    }
}

class MaximumAttemptsExceededException(
    allowedAttempts: Int
): RuntimeException("Maximum number of attempts allowed is \"$allowedAttempts\" and that has been exceeded")