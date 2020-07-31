package com.kaushikam.transactions.infrastructure.repository.hibernate

import com.kaushikam.transactions.domain.model.payment.IReceiptRepository
import com.kaushikam.transactions.domain.model.payment.Receipt
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import javax.persistence.EntityManager

@Repository
class HibernateReceiptRepositoryImpl(
    private val entityManager: EntityManager
): IReceiptRepository {
    override fun save(receipt: Receipt) {
        if (receipt.id == null) {
            entityManager.persist(receipt)
        } else {
            entityManager.merge(receipt)
        }
    }
}