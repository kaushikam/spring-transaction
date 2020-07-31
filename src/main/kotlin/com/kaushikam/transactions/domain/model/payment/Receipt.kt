package com.kaushikam.transactions.domain.model.payment

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "receipt")
class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "created_on")
    val createdOn: LocalDateTime,

    @Column(name = "amount")
    val amount: Double
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    var paymentInformation: PaymentInformation? = null

    fun persisted() = this.id != null
}