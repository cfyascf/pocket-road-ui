package com.example.pocket_road_ui.ui.screens.friendprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.ui.components.CarCard
import com.example.pocket_road_ui.ui.components.UserProfileCard
import com.example.pocket_road_ui.ui.screens.profile.mockFriends
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

val mockCars = listOf<Car>(
    Car("1", "Civic Type R", "Honda", CarRarity.RARE, "https://images"),
    Car("2", "911 GT3 RS", "Porsche", CarRarity.LEGENDARY, "https://images"),
    Car("3", "Huracán Evo", "Lamborghini", CarRarity.EXOTIC, "https://images"),
    Car("4", "Mustang Mach 1", "Ford", CarRarity.RARE, "https://images"),
    Car("5", "M4 Competition", "BMW", CarRarity.RARE, "https://images"),
)
@Composable
fun FriendProfileScreen(
    friendId: Int,
    onBackClick: () -> Unit,
    onCarClick: (String) -> Unit
) {
    // Simulando busca de dados baseada no ID
    val friend = mockFriends.find { it.id == friendId } ?: mockFriends[0]

    // Simulando que o amigo tem carros diferentes desbloqueados
    // (Em um app real, isso viria da API)
    val friendCars = remember(friendId) {
        mockCars.map { car ->
            car.copy()
        }
    }

    Scaffold(
        containerColor = AppColors.Gray950,
        topBar = {
            // Header Customizado com Botão Voltar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .background(AppColors.Gray900, CircleShape)
                        .border(1.dp, AppColors.Gray800, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Garagem de ${friend.name}",
                    style = AppTypography.ScreenTitle.copy(fontSize = 18.sp)
                )
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            // Background
            Image(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.15f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                UserProfileCard(false)

                Spacer(modifier = Modifier.height(32.dp))

                // 2. Título da Seção
                Text(
                    text = "COLEÇÃO (${friendCars.count()}/${friendCars.size})",
                    style = AppTypography.Tagline.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 3. Grid de Carros (Reutilizando CarCard)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(friendCars) { car ->
                        CarCard(
                            car = car,
                            onClick = onCarClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FriendHeaderCard(
    name: String,
    level: Int,
    initials: String,
    carsCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        AppColors.Gray900,
                        AppColors.Gray900.copy(alpha = 0.8f)
                    )
                )
            )
            .border(1.dp, AppColors.Gray800, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar Grande
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(AppColors.Red600)
                        .border(4.dp, AppColors.Gray800, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        style = AppTypography.ScreenTitle.copy(fontSize = 24.sp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = name,
                        style = AppTypography.ScreenTitle.copy(fontSize = 20.sp)
                    )
                    Text(
                        text = "Level $level • $carsCount Carros",
                        style = AppTypography.Tagline
                    )
                }
            }

            // Botão "Seguindo" Estático
            Surface(
                color = AppColors.Red600.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Red500.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = AppColors.Red500,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Seguindo",
                        style = AppTypography.Tagline.copy(
                            fontSize = 10.sp,
                            color = AppColors.Red500,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FriendProfilePreview() {
    FriendProfileScreen(
        friendId = 101,
        onBackClick = {},
        onCarClick = {}
    )
}