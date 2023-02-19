package com.example.mypricingapp.Services

/*** A Simple pricing service class that will be responsible for
 * cost calculations, vat calculations and liability to revenue
 *
 */

class PricingService {
    var CostAmount : String = "0.00"
    var MarkupPercentage : String = "0.00"
    var TaxRate : String = "15.00"


    fun TaxOnCostAmount() : Double{
        return CostAmount.toDouble() * (TaxRate.toDouble()/100)
    }

    fun CostAmountInclusiveTax() : Double{
        return CostAmount.toDouble() + TaxOnCostAmount()
    }

    fun ProfitAmountOnCost() : Double{
        return CostAmountInclusiveTax() * (MarkupPercentage.toDouble() / 100)
    }

    fun SellingPrice() : Double{
        return CostAmountInclusiveTax() + ProfitAmountOnCost()
    }

    fun TaxOnSaleAmount() : Double{
        return SellingPrice() * (TaxRate.toDouble() / 100)
    }

    fun LiabilityToTaxCollector() : Double{
        return TaxOnSaleAmount() - TaxOnCostAmount()
    }
}