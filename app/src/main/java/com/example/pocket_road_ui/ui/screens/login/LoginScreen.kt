import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocket_road_ui.ui.screens.login.LoginViewModel
import com.example.pocket_road_ui.R
import com.example.pocket_road_ui.data.local.SessionManager
import com.example.pocket_road_ui.data.repository.AuthRepositoryMock
import com.example.pocket_road_ui.ui.components.NotificationType
import com.example.pocket_road_ui.ui.components.PopupNotification
import com.example.pocket_road_ui.ui.components.Title
import com.example.pocket_road_ui.ui.components.form.Form
import com.example.pocket_road_ui.ui.components.form.InputData
import com.example.pocket_road_ui.ui.screens.login.LoginSideEffect
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppDimensions

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToCardexScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is LoginSideEffect.NavigateToHome -> {
                    onNavigateToCardexScreen()
                }

                LoginSideEffect.NavigateToHome -> TODO()
            }
        }
    }

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

        // content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Title(true)

            Spacer(modifier = Modifier.height(30.dp))

            val fields = mutableListOf(
                InputData(
                    state.username,
                    { viewModel.onUsernameChange(it) },
                    "USERNAME",
                    Icons.Default.Email,
                    "driftking",
                    KeyboardType.Text,
                    false
                )
            )

            if(state.isRegistering) {
                fields.add(
                    InputData(
                        state.email,
                        { viewModel.onEmailChange(it) },
                        "EMAIL",
                        Icons.Default.Email,
                        "email@gmail.com",
                        KeyboardType.Email,
                        false
                ))
            }

            fields.add(
                InputData(
                    state.password,
                    { viewModel.onPasswordChange(it) },
                    "PASSWORD",
                    Icons.Default.Lock,
                    ".........",
                    KeyboardType.Password,
                    true
                ))

            Form(
                title = if (state.isRegistering) "Criar Conta" else "Bem-vindo",
                fields = fields,
                buttonText = if (state.isRegistering) "Cadastrar" else "Entrar",
                buttonAction = {
                    if (state.isRegistering)
                        viewModel.onRegisterClick()
                    else
                        viewModel.onLoginClick()
                },
                isLoading = state.isLoading
            )

            if (!state.isRegistering) {
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
                .padding(bottom = AppDimensions.navBarBottomPadding + 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (state.isRegistering) "Já tem uma conta? " else "Ainda não tem conta? ",
                    color = AppColors.Gray400,
                    fontSize = 14.sp
                )
                Text(
                    text = if (state.isRegistering) "Faça Login" else "Registre-se",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        viewModel.toggleRegisterMode()
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            PopupNotification(
                state.notification?.message,
                state.notification?.type ?: NotificationType.ERROR,
                state.notification != null,
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
    val fakeVM = LoginViewModel(
        fakeRepo,
        SessionManager(
            context = LocalContext.current
    ))

    LoginScreen(fakeVM, onNavigateToCardexScreen = {})
}