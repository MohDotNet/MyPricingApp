package com.example.mypricingapp

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypricingapp.Models.ResultItemModel
import com.example.mypricingapp.Services.PricingService
import com.example.mypricingapp.ui.theme.MyPricingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PricingForm()
        }
    }
}

@Composable
fun PricingForm(){
    val priceService by remember {mutableStateOf(PricingService())} // due to state - we do not use priceService = PricingService()
    var priceTextField by remember {mutableStateOf("")}
    var markupTextField by remember {mutableStateOf("")}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {

        TextField(
            label = {Text("Cost Amount")},
            value = priceTextField,
            onValueChange = {priceTextField = it},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = {Text("Markup %")},
            value = markupTextField,
            onValueChange = {markupTextField = it},
            singleLine = true,
        )

        /* TODO - CheckBox */
        Checkbox(checked = false, onCheckedChange = {} )

        Button(
            onClick = {
                priceService.CostAmount = priceTextField
                priceService.MarkupPercentage = markupTextField
                priceTextField = ""
                markupTextField = ""
            }
        ) {
            Text(text = "Calculate")
        }


        Card(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(1f),
            elevation = 25.dp) {
            Column(modifier = Modifier
                .padding(15.dp))
            {
                ResultCardHeader()

                val itemList : List<ResultItemModel> = listOf(
                    ResultItemModel("Cost Amount", priceService.CostAmount, painterResource(id = R.drawable.accounting)),
                    ResultItemModel("Cost Amnt Inc",priceService.CostAmountInclusiveTax().toString(), painterResource(id = R.drawable.assets)),
                    ResultItemModel("Profit Markup",priceService.ProfitAmountOnCost().toString(), painterResource(id = R.drawable.calculations)),
                    ResultItemModel("Selling Price",priceService.SellingPrice().toString(),painterResource(id = R.drawable.evaluation)),
                    ResultItemModel("Tax on Cost",priceService.TaxOnCostAmount().toString(),painterResource(id = R.drawable.report)),
                    ResultItemModel("Tax on Sale",priceService.TaxOnSaleAmount().toString(),painterResource(id = R.drawable.stock)),
                    ResultItemModel("Taxman Share",priceService.LiabilityToTaxCollector().toString(),painterResource(id = R.drawable.stock)),
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp) ,
                    content = {
                        itemList.forEach(){
                            item { ItemCard(resultItem = it) }
                        }
                    } )
            }
        }
    }
}


@Composable
fun ItemCard(resultItem : ResultItemModel){
    Card(
        modifier = Modifier
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 15.dp){
        Column() {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape),
                    painter = resultItem.itemPicture,
                    contentDescription = resultItem.Description)

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                   text = resultItem.Description)

            Text(modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally),
                text = "ZAR ${resultItem.Price}",
                fontSize = 15.sp,
                textAlign = TextAlign.Center)

        }
    }
}

@Composable
fun ResultCardHeader(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)

    ){
        Row(){
            val image : Painter = painterResource(id = R.drawable.search_result)
            Image(
                painter = image,
                contentDescription = "IconSearch" )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyPricingAppTheme {
        PricingForm()
    }
}