package com.template.states

import com.template.contracts.PaymentRequestContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

@BelongsToContract(PaymentRequestContract::class)
data class PaymentRequestState(
        val amout: String,
        val towhom: Party,
        override val participants: List<AbstractParty> = listOf()) : ContractState