import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Rang (Colors) jo hum use karenge
val Indigo = Color(0xFF1E1B4B)
val IndigoSoft = Color(0xFF3730A3)
val Saffron = Color(0xFFF59E0B)
val Gray100 = Color(0xFFF1F5F9)
val Gray200 = Color(0xFFE2E8F0)
val Gray500 = Color(0xFF64748B)
val Gray700 = Color(0xFF334155)
val Red500 = Color(0xFFEF4444)

val EXAMS = listOf("JEE Main", "JEE Advanced", "NEET", "UPSC", "CAT", "GATE", "Board Exams")
val STREAMS = listOf("Science (PCM)", "Science (PCB)", "Commerce", "Arts / Humanities")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = IndigoSoft
                ) {
                    OnboardingScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen() {
    var step by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var exam by remember { mutableStateOf("") }
    var stream by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Header / Logo
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Indigo, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎓", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("VidyaMitra", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Indigo)
                    Text("Your Student Wellness Agent", fontSize = 13.sp, color = Gray500)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Progress Bar
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (i in 1..4) {
                        val bgColor = when {
                            i < step -> Indigo
                            i == step -> Saffron
                            else -> Gray200
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .background(bgColor, RoundedCornerShape(4.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Step Indicator
                Text(
                    text = "STEP $step OF 4",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray500,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Forms
                when (step) {
                    1 -> {
                        Text("What's your name?", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Gray700)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("e.g. Priya Mehta") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                    2 -> {
                        Text("Which exam are you preparing for?", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Gray700)
                        Spacer(modifier = Modifier.height(8.dp))
                        EXAMS.forEach { ex ->
                            val isSelected = exam == ex
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .border(
                                        width = 1.5.dp,
                                        color = if (isSelected) Indigo else Gray200,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(if (isSelected) Color(0xFFEEEDF4) else Color.White, RoundedCornerShape(10.dp))
                                    .clickable { exam = ex }
                                    .padding(14.dp)
                            ) {
                                Text(ex, color = if (isSelected) Indigo else Gray700, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    3 -> {
                        Text("What's your stream / field?", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Gray700)
                        Spacer(modifier = Modifier.height(8.dp))
                        STREAMS.forEach { st ->
                            val isSelected = stream == st
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .border(
                                        width = 1.5.dp,
                                        color = if (isSelected) Indigo else Gray200,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(if (isSelected) Color(0xFFEEEDF4) else Color.White, RoundedCornerShape(10.dp))
                                    .clickable { stream = st }
                                    .padding(14.dp)
                            ) {
                                Text(st, color = if (isSelected) Indigo else Gray700, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    4 -> {
                        Text("You are all set! 🚀", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Indigo)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Name: $name\nExam: $exam\nStream: $stream", color = Gray700)
                    }
                }

                if (errorMsg.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("⚠️ $errorMsg", color = Red500, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (step > 1) {
                        Button(
                            onClick = { step--; errorMsg = "" },
                            colors = ButtonDefaults.buttonColors(containerColor = Gray100, contentColor = Gray700),
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Back")
                        }
                    }
                    Button(
                        onClick = {
                            when {
                                step == 1 && name.isBlank() -> errorMsg = "Please enter your name."
                                step == 2 && exam.isBlank() -> errorMsg = "Please select an exam."
                                step == 3 && stream.isBlank() -> errorMsg = "Please select a stream."
                                else -> {
                                    errorMsg = ""
                                    if (step < 4) step++
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo, contentColor = Color.White),
                        modifier = Modifier.weight(2f).height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (step == 4) "Start App" else "Continue")
                    }
                }
            }
        }
    }
}