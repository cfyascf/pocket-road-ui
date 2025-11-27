package com.example.pocket_road_ui.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.R
import com.example.pocket_road_ui.ui.components.UserProfileCard
import com.example.pocket_road_ui.ui.screens.home.components.HomeFooter
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

data class FriendMock(
    val id: Int,
    val name: String,
    val level: Int,
    val avatarInitials: String,
    val carsCount: Int
)

val mockFriends = listOf(
    FriendMock(101, "Lucas Drift", 15, "LD", 52),
    FriendMock(102, "Ana Turbo", 22, "AT", 89),
    FriendMock(103, "Japeta", 8, "JP", 12),
    FriendMock(104, "V8 King", 30, "V8", 120),
)

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onFriendClick: (Int) -> Unit // Futuro: ver perfil do amigo
) {
    Scaffold(
        containerColor = AppColors.Gray950,
        bottomBar = { HomeFooter() } // Mantemos a navegação
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Fundo (Mesmo padrão das outras telas)
            Image(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.2f // Bem suave no perfil
            )

            // Overlay para garantir leitura
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.Gray950.copy(alpha = 0.8f))
            )

            // 2. Conteúdo Rolável
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Header / Cartão do Usuário
                UserProfileCard()

                Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(32.dp))

                // Lista de Amigos
                Text(
                    text = "PERFIS QUE SIGO",
                    style = AppTypography.Tagline.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    mockFriends.forEach { friend ->
                        FriendItem(friend = friend, onClick = { onFriendClick(friend.id) })
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botão Logout
                LogoutButton(onLogout)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Composable
fun UserStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatItem(value = "42", label = "Carros")
        StatItem(value = "152", label = "Seguindo")
        StatItem(value = "2.4M", label = "Valor")
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(95.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = value,
            style = AppTypography.ScreenTitle.copy(fontSize = 20.sp)
        )
        Text(
            text = label.uppercase(),
            style = AppTypography.Tagline.copy(fontSize = 10.sp)
        )
    }
}

@Composable
fun FriendItem(friend: FriendMock, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = AppColors.Gray900,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Gray800)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar com Iniciais
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AppColors.Red600.copy(alpha = 0.2f))
                        .border(1.dp, AppColors.Red600, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = friend.avatarInitials,
                        style = AppTypography.Tagline.copy(
                            color = AppColors.Red500,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = friend.name,
                        style = AppTypography.ScreenTitle.copy(fontSize = 14.sp)
                    )
                    Text(
                        text = "Nível ${friend.level} • ${friend.carsCount} carros",
                        style = AppTypography.Tagline.copy(fontSize = 12.sp)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Following",
                tint = Color(0xFFEAB308), // Yellow
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFFEF4444) // Red-500
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF7F1D1D).copy(alpha = 0.5f)), // Red-900
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Sair da Conta",
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onLogout = {}, onFriendClick = {})
}