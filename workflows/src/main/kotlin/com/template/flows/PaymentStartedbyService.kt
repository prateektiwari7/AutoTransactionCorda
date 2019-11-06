package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import com.template.contracts.MoneyStateContract
import com.template.states.PaymentRequestState
import com.template.states.intermediateState
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

@InitiatingFlow
@StartableByService
class PaymentStartedbyService : FlowLogic<SignedTransaction>(){

   // override val progresstracker = ProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {
        val notary = serviceHub.networkMapCache.notaryIdentities[0]
        val statelist = serviceHub.vaultService.queryBy(PaymentRequestState::class.java).states
        val vaultstate = statelist.get(statelist.size-1).state.data
        val outstate= intermediateState(vaultstate.amout.toInt(),vaultstate.towhom)

        val transactionBuilder = TransactionBuilder(notary)
        val commandData = MoneyStateContract.Commands.Pay()
        transactionBuilder.addCommand(commandData, ourIdentity.owningKey, vaultstate.towhom.owningKey)
        transactionBuilder.addOutputState(outstate, MoneyStateContract.ID)
        transactionBuilder.verify(serviceHub)


        val session = initiateFlow(vaultstate.towhom)
        val signedTransaction = serviceHub.signInitialTransaction(transactionBuilder)
        val fullySignedTransaction = subFlow(CollectSignaturesFlow(signedTransaction, listOf(session)))
        return subFlow(FinalityFlow(fullySignedTransaction, listOf(session)))



    }

}

@InitiatedBy(PaymentStartedbyService::class)
class PaymentFlowResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        // Responder flow logic goes here.
        val signTransactionFlow = object : SignTransactionFlow(counterpartySession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                // TODO: Checking.
            }
        }

        val txId = subFlow(signTransactionFlow).id
        subFlow(ReceiveFinalityFlow(counterpartySession, expectedTxId = txId))

    }
}