import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.repository.AuthRepository
import com.example.pocket_road_ui.data.repository.IAuthRepository
import com.example.pocket_road_ui.ui.screens.login.LoginViewModel

val ColorGray950 = Color(0xFF030712) // gray-950
val ColorGray900 = Color(0xFF111827) // gray-900
val ColorGray800 = Color(0xFF1F2937) // gray-800
val ColorGray700 = Color(0xFF374151) // gray-700
val ColorGray400 = Color(0xFF9CA3AF) // gray-400
val ColorIndigo500 = Color(0xFF6366F1) // indigo-500
val ColorIndigo600 = Color(0xFF4F46E5) // indigo-600

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize().background(ColorGray950)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&q=80&w=1000"),
            contentDescription = "Background Car",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ColorGray950.copy(alpha = 0.4f),
                            ColorGray950.copy(alpha = 0.8f),
                            ColorGray950
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White, fontStyle = FontStyle.Italic)) {
                        append("AUTO")
                    }
                    withStyle(style = SpanStyle(color = ColorIndigo500, fontStyle = FontStyle.Italic)) {
                        append("DEX")
                    }
                },
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
            Text(
                text = "CAPTURE • COLECIONE • COMPITA",
                color = ColorGray400,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.1f), // Borda sutil
                        shape = RoundedCornerShape(24.dp)
                    ),
                color = Color.Black.copy(alpha = 0.3f), // Fundo preto translúcido
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (viewModel.isRegistering) "Criar Conta" else "Bem-vindo",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (viewModel.isRegistering) {
                        CustomInput(
                            value = viewModel.username,
                            onValueChange = { viewModel.username = it },
                            label = "NOME DE PILOTO",
                            icon = Icons.Default.Person,
                            placeholder = "Ex: DriftKing"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    CustomInput(
                        value = viewModel.username,
                        onValueChange = { viewModel.username = it },
                        label = "USERNAME",
                        icon = Icons.Default.Email,
                        placeholder = "seu@email.com",
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomInput(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        label = "SENHA",
                        icon = Icons.Default.Lock,
                        placeholder = "••••••••",
                        isPassword = true
                    )

                    if (!viewModel.isRegistering) {
                        Text(
                            text = "Esqueceu a senha?",
                            color = ColorIndigo500,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp)
                                .clickable { /* Ação */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = {
                            viewModel.onLoginClick()
                            onNavigateToHomeScreen()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorIndigo600),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp)
                    ) {
                        if (viewModel.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (viewModel.isRegistering) "Cadastrar" else "Entrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (viewModel.isRegistering) "Já tem uma conta? " else "Ainda não tem conta? ",
                    color = ColorGray400,
                    fontSize = 14.sp
                )
                Text(
                    text = if (viewModel.isRegistering) "Faça Login" else "Registre-se",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { viewModel.isRegistering = !viewModel.isRegistering }
                )
            }
        }
    }
}

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = ColorGray400,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ColorGray900.copy(alpha = 0.5f))
                .border(1.dp, ColorGray700, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ColorGray700,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(text = placeholder, color = Color.Gray.copy(alpha = 0.5f), fontSize = 14.sp)
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                    cursorBrush = SolidColor(ColorIndigo500)
                )
            }

            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle Password",
                        tint = ColorGray700,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun LoginScreenPreview() {
    val fakeRepo = object : IAuthRepository {
        override suspend fun login(e: String, p: String) = Result.success(
            ApiResponse<String?>("test", true, "test")
        )
    }

    val fakeVM = LoginViewModel(fakeRepo)

    LoginScreen(fakeVM, onNavigateToHomeScreen = {})
}