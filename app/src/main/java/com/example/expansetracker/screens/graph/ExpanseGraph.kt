package com.example.expansetracker.screens.graph
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expansetracker.screens.addexpanse.AddExpanseViewmodel
import com.example.expansetracker.screens.home.ItemLayout
import com.example.expansetracker.util.dateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpanseGraph(
    modifier: Modifier = Modifier,
    expanseViewmodel: AddExpanseViewmodel = hiltViewModel()
) {
    // Collect all transactions from the ViewModel
    val allTransaction by expanseViewmodel.allTransactions.collectAsState()

    // Prepare the data for the graph
    val transactions = allTransaction.sortedBy { it.date.dateFormatter() } // Sort by date
    val amounts = transactions.map { it.amount.toFloat() }
    val dates = transactions.map { formatDate(it.date) } // Format to "dd MMM"

    // Draw the graph
    if (transactions.isNotEmpty()) {
        Scaffold(
            topBar = { TopAppBar(
                title = {
                    Text("Expanse Graph")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Cyan,
                    titleContentColor = Color.Black
                )

            ) }
        ) {


            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                ExpenseTrackerGraph(
                    dataPoints = amounts,
                    labels = dates,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.LightGray),
                    graphColor = Color.Red // Customize graph color
                )
            }
        }
    }
    else {
            Text(text = "No transactions available")
        }

    }


// Utility function to format date
fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    return try {
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date ?: inputDate)
    } catch (e: Exception) {
        inputDate // Return the original date if parsing fails
    }
}

// Graph drawing function (reuse from previous implementation)
@Composable
fun ExpenseTrackerGraph(
    dataPoints: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Calculate spacing between points
        val spacing = width / (dataPoints.size - 1)

        // Normalize data points to fit within the canvas
        val maxValue = dataPoints.maxOrNull() ?: 1f
        val minValue = dataPoints.minOrNull() ?: 0f
        val normalizedPoints = dataPoints.map { point ->
            (point - minValue) / (maxValue - minValue) * height
        }

        // Create the Path for the wavy curve
        val path = Path().apply {
            for (i in dataPoints.indices) {
                val x = i * spacing
                val y = height - normalizedPoints[i]

                if (i == 0) {
                    moveTo(x, y)
                } else {
                    val previousX = (i - 1) * spacing
                    val previousY = height - normalizedPoints[i - 1]
                    val controlX = (previousX + x) / 2
                    quadraticTo(controlX, previousY, x, y)
                }
            }
        }

        // Draw the curve
        drawPath(
            path = path,
            color = graphColor,
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Add labels for X-axis and Y-axis points
        for (i in dataPoints.indices) {
            val x = i * spacing
            val y = height - normalizedPoints[i]

            // Draw circles on data points
            drawCircle(
                color = graphColor,
                center = Offset(x, y),
                radius = 6.dp.toPx()
            )

            // Draw X-axis labels (dates)
            drawContext.canvas.nativeCanvas.drawText(
                labels[i],
                x,
                height + 16.dp.toPx(),
                android.graphics.Paint().apply {
                    textSize = 12.sp.toPx()
                    color = android.graphics.Color.BLACK
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}
