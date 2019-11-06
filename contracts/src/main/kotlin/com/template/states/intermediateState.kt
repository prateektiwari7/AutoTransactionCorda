package com.template.states

import com.template.contracts.MoneyStateContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

@BelongsToContract(MoneyStateContract::class)
data class intermediateState(val amout: Int,
                             val towhom: Party,
                             override val participants: List<AbstractParty> = listOf()) : ContractState
