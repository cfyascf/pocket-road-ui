package com.example.pocket_road_ui.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.ui.screens.home.components.CarCard
import com.example.pocket_road_ui.ui.screens.home.components.HomeFooter
import com.example.pocket_road_ui.ui.screens.home.components.HomeHeader
import com.example.pocket_road_ui.ui.screens.home.components.StatsRow
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography
data class CarMock(
    val id: Int,
    val name: String,
    val brand: String,
    val rarity: String,
    val unlocked: Boolean,
    val imageUrl: String
)

val mockCars = listOf(
    CarMock(1, "Civic Type R", "Honda", "incomum", true, "https://images.unsplash.com/photo-1605816988069-b112b322ebcc?auto=format&fit=crop&q=80&w=800"),
    CarMock(2, "911 GT3 RS", "Porsche", "lendario", true, "https://images.unsplash.com/photo-1614162692292-7ac56d7f7f1e?auto=format&fit=crop&q=80&w=800"),
    CarMock(3, "Huracán Evo", "Lamborghini", "exotico", false, "https://images.unsplash.com/photo-1566024287286-457247b70310?auto=format&fit=crop&q=80&w=800"),
    CarMock(4, "Mustang Mach 1", "Ford", "raro", false, "https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?auto=format&fit=crop&q=80&w=800"),
    CarMock(5, "M4 Competition", "BMW", "raro", false, "https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&q=80&w=800"),
    CarMock(6, "Supra MK5", "Toyota", "raro", true, "https://images.unsplash.com/photo-1627454820574-fb05247d6297?auto=format&fit=crop&q=80&w=800")
)

@Composable
fun HomeScreen() {
    Scaffold(
        containerColor = AppColors.Gray950,
        topBar = { HomeHeader() },
        bottomBar = { HomeFooter() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            StatsRow()

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Sua garagem",
                    style = AppTypography.ScreenTitle.copy(fontSize = 18.sp)
                )
                Text(
                    text = "Ordenar",
                    style = AppTypography.Tagline.copy(
                        color = AppColors.Red500,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(mockCars) { car ->
                    CarCard(car)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}