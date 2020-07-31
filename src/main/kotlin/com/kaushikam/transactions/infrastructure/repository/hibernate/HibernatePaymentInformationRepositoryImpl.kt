package com.kaushikam.transactions.infrastructure.repository.hibernate

import com.kaushikam.transactions.domain.model.payment.IPaymentInformationRepository
import com.kaushikam.transactions.domain.model.payment.PaymentInformation
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class HibernatePaymentInformationRepositoryImpl(
    private val entityManager: EntityManager
): IPaymentInformationRepository {
    override fun save(information: PaymentInformation) {
        if (information.id == null)
            entityManager.persist(information)
        else
            entityManager.merge(information)
    }

    override fun findById(id: Long): PaymentInformation? {
        return entityManager.find(PaymentInformation::class.java, id)
    }

    override fun findAll(): List<PaymentInformation> {
        val query = entityManager.createQuery(
            "SELECT i FROM PaymentInformation i",
            PaymentInformation::class.java
        )
        return query.resultList
    }
}