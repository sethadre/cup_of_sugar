package com.example.cupofsugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        initTips()
    }

    private fun initTips(){
        val purchasesUpdatedListener =
            PurchasesUpdatedListener {
                    billingResult, purchases ->
            }

        var billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                }
            }
            override fun onBillingServiceDisconnected() {
            }
        })

        suspend fun querySkuDetails() {
            val skuList = ArrayList<String>()
            skuList.add("premium_upgrade")
            skuList.add("gas")
            val params = SkuDetailsParams.newBuilder()
            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

            val skuDetailsResult = withContext(Dispatchers.IO) {
                billingClient.querySkuDetails(params.build())
            }

        }

        suspend fun handlePurchase(purchase: Purchase) {
            val client: BillingClient = billingClient
            val acknowledgePurchaseResponseListener: AcknowledgePurchaseResponseListener
//            val purchase : Purchase = purchasesUpdatedListener.purchases()


            val consumeParams =
                ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build()
            val consumeResult = withContext(Dispatchers.IO) {
                client.consumePurchase(consumeParams)
            }

            if (purchase.purchaseState === Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                    val ackPurchaseResult = withContext(Dispatchers.IO) {
                        client.acknowledgePurchase(acknowledgePurchaseParams.build())
                    }
                }
            }
        }

        val activity : TipsActivity//need to adjust

        val flowParams = BillingFlowParams.newBuilder()
//            .setSkuDetails(skuDetails)
            .build()
//        val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode

        fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
//                    handlePurchase(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            } else {
            }
        }
    }
}