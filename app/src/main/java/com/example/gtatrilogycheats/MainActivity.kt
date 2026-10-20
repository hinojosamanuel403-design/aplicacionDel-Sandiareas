package com.example.gtatrilogycheats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Modelo de datos para los trucos
data class Cheat(
    val title: String,
    val game: String,
    val buttons: List<String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }

    // Lista de trucos de GTA Trilogy para Nintendo Switch
    val cheats = remember {
        listOf(
            Cheat("Salud, Armadura y $250,000", "GTA San Andreas", listOf("R", "ZR", "L", "B", "IZQ", "ABAJO", "DER", "ARRIBA", "IZQ", "ABAJO", "DER", "ARRIBA")),
            Cheat("Nivel de búsqueda al máximo", "GTA San Andreas", listOf("R", "R", "A", "ZR", "IZQ", "DER", "IZQ", "DER", "IZQ", "DER")),
            Cheat("Conjunto de armas 1", "GTA Vice City", listOf("R", "ZR", "L", "ZR", "IZQ", "ABAJO", "DER", "ARRIBA", "IZQ", "ABAJO", "DER", "ARRIBA")),
            Cheat("Salud al máximo", "GTA Vice City", listOf("R", "ZR", "L", "A", "IZQ", "ABAJO", "DER", "ARRIBA", "IZQ", "ABAJO", "DER", "ARRIBA")),
            Cheat("Tanque Rhino", "GTA III", listOf("A", "A", "A", "A", "A", "A", "R", "ZL", "L", "X", "A", "X")),
            Cheat("Todas las armas", "GTA III", listOf("ZR", "ZR", "L", "ZR", "IZQ", "ABAJO", "DER", "ARRIBA", "IZQ", "ABAJO", "DER", "ARRIBA"))
        )
    }

    // Filtrar trucos en tiempo real
    val filteredCheats = cheats.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.game.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar truco o juego...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredCheats) { cheat ->
                CheatCard(cheat = cheat)
            }
        }
    }
}

@Composable
fun CheatCard(cheat: Cheat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = cheat.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = cheat.game,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Secuencia: ${cheat.buttons.joinToString(" - ")}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}