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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.repository.IAuthRepository
import com.example.pocket_road_ui.ui.screens.login.LoginViewModel
import com.example.pocket_road_ui.R
import com.example.pocket_road_ui.data.repository.AuthRepositoryMock
import com.example.pocket_road_ui.domain.models.User
import com.example.pocket_road_ui.ui.components.PopupNotification
import com.example.pocket_road_ui.ui.components.Title
import com.example.pocket_road_ui.ui.components.form.Form
import com.example.pocket_road_ui.ui.components.form.InputData
import com.example.pocket_road_ui.ui.theme.AppColors

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.Gray950)
    ) {

        // background
        Image(
            painter = painterResource(id = R.drawable.login_background),
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
                            AppColors.Gray950.copy(alpha = 0.4f),
                            AppColors.Gray950.copy(alpha = 0.8f),
                            AppColors.Gray950
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

            Title(true)

            val fields = mutableListOf<InputData>(
                InputData(
                    viewModel.username,
                    { viewModel.username = it },
                    "USERNAME",
                    Icons.Default.Email,
                    "driftking",
                    KeyboardType.Text,
                    false
                )
            )

            if(viewModel.isRegistering) {
                fields.add(
                    InputData(
                        viewModel.email,
                        { viewModel.email = it },
                        "EMAIL",
                        Icons.Default.Email,
                        "email@gmail.com",
                        KeyboardType.Email,
                        false
                ))
            }

            fields.add(
                InputData(
                    viewModel.password,
                    { viewModel.password = it },
                    "PASSWORD",
                    Icons.Default.Lock,
                    ".........",
                    KeyboardType.Password,
                    true
                ))

            Form(
                title = if (viewModel.isRegistering) "Criar Conta" else "Bem-vindo",
                fields = fields,
                buttonText = if (viewModel.isRegistering) "Cadastrar" else "Entrar",
                buttonAction = {
                    if (viewModel.isRegistering)
                        viewModel.onRegisterClick(onNavigateToHomeScreen)
                    else
                        viewModel.onLoginClick(onNavigateToHomeScreen)
                },
                isLoading = viewModel.isLoading
            )

            if (!viewModel.isRegistering) {
                Text(
                    text = "Esqueceu a senha?",
                    color = AppColors.Red500,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                        .clickable { /* Ação */ }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (viewModel.isRegistering) "Já tem uma conta? " else "Ainda não tem conta? ",
                    color = AppColors.Gray400,
                    fontSize = 14.sp
                )
                Text(
                    text = if (viewModel.isRegistering) "Faça Login" else "Registre-se",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        viewModel.isRegistering = !viewModel.isRegistering
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter // Alinha no topo
        ) {
            PopupNotification(
                viewModel.notificationMessage,
                viewModel.notificationType,
                viewModel.showNotification,
                { viewModel.dismissNotification() }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun LoginScreenPreview() {
    val fakeRepo = AuthRepositoryMock()
    val fakeVM = LoginViewModel(fakeRepo)

    LoginScreen(fakeVM, onNavigateToHomeScreen = {})
}