package com.example.gtaswitchcheats

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val BOTONES_HOMBRO = setOf("L", "R", "ZL", "ZR")


enum class JuegoGta(
    val nombreEs: String,
    val nombreEn: String,
    val bannerEs: String,
    val bannerEn: String,
    @DrawableRes val fondoRes: Int
) {
    SAN_ANDREAS(
        nombreEs = "Grand Theft Auto: San Andreas",
        nombreEn = "Grand Theft Auto: San Andreas",
        bannerEs = "TRUCOS GTA SA · DEFINITIVE",
        bannerEn = "GTA SA CHEATS · DEFINITIVE",
        fondoRes = R.drawable.sa_fondo
    ),
    VICE_CITY(
        nombreEs = "Grand Theft Auto: Vice City",
        nombreEn = "Grand Theft Auto: Vice City",
        bannerEs = "TRUCOS VICE CITY · DEFINITIVE",
        bannerEn = "VICE CITY CHEATS · DEFINITIVE",
        fondoRes = R.drawable.vc_fondo
    ),
    GTA_III(
        nombreEs = "Grand Theft Auto III",
        nombreEn = "Grand Theft Auto III",
        bannerEs = "TRUCOS GTA III · DEFINITIVE",
        bannerEn = "GTA III CHEATS · DEFINITIVE",
        fondoRes = R.drawable.gta3_fondo
    )
}

data class TrucoItem(
    val id: Int,
    val juego: JuegoGta,
    val tituloEs: String,
    val tituloEn: String,
    val detalleEs: String,
    val detalleEn: String,
    val secuencia: List<String>
)

object DatosGtaCheetos {
    val trucos: List<TrucoItem> = listOf(
        TrucoItem(1, JuegoGta.SAN_ANDREAS, "Pack 1", "Weapons Pack 1", "Bate, pistola, escopeta, mini SMG, AK-47, lanzacohetes, molotov, rifle y cuchillo.", "Bat, pistol, shotgun, mini SMG, AK-47, rocket launcher, molotov, rifle and knife.", listOf("ZR", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(2, JuegoGta.SAN_ANDREAS, "Pack de armas 2", "Weapons Pack 2", "Cuchillo, Desert Eagle, recortada, Tec-9, M4, francotirador, lanzallamas, granadas y extintor.", "Knife, Desert Eagle, sawn-off, Tec-9, M4, sniper, flamethrower, grenades and extinguisher.", listOf("ZR", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▼", "◀")),
        TrucoItem(3, JuegoGta.SAN_ANDREAS, "Pack de armas 3", "Weapons Pack 3", "Motosierra, pistola silenciada, SPAS-12, MP5, M4, lanzacohetes térmico y cargas remotas.", "Chainsaw, silenced pistol, SPAS-12, MP5, M4, heat-seeking RPG and satchel charges.", listOf("ZR", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▼", "▼")),
        TrucoItem(4, JuegoGta.SAN_ANDREAS, "Salud completa + armadura, $250,000", "Full Health + Armor, $250,000", "También repara el vehículo si vas conduciendo.", "Also repairs vehicle if you are driving.", listOf("R", "ZR", "L", "B", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(5, JuegoGta.SAN_ANDREAS, "Salud casi infinita", "Near Infinite Health", "Resiste disparos y golpes (no explosiones ni caídas).", "Protects against bullets and melee hits (not explosions/falls).", listOf("▼", "B", "▶", "◀", "▶", "ZR", "▶", "▼", "▲", "X")),
        TrucoItem(6, JuegoGta.SAN_ANDREAS, "Munición infinita", "Infinite Ammo", "Sin recargar en todas las armas.", "No reload needed for all weapons.", listOf("L", "ZR", "Y", "R", "◀", "ZR", "R", "◀", "Y", "▼", "L", "L")),
        TrucoItem(7, JuegoGta.SAN_ANDREAS, "Oxígeno infinito", "Infinite Oxygen", "Buceo ilimitado bajo el agua.", "Unlimited underwater lung capacity.", listOf("▼", "◀", "L", "▼", "▼", "ZR", "▼", "ZL", "▼")),
        TrucoItem(8, JuegoGta.SAN_ANDREAS, "Nunca tener hambre", "Never Get Hungry", "CJ no pierde energía ni músculo por hambre.", "CJ never gets hungry.", listOf("Y", "ZL", "R", "X", "▲", "Y", "ZL", "▲", "B")),
        TrucoItem(9, JuegoGta.SAN_ANDREAS, "Jetpack", "Jetpack", "Aparece un jetpack listo para volar.", "Spawns a jetpack ready to fly.", listOf("L", "ZL", "R", "ZR", "▲", "▼", "◀", "▶", "L", "ZL", "R", "ZR", "▲", "▼", "◀", "▶")),
        TrucoItem(10, JuegoGta.SAN_ANDREAS, "Paracaídas", "Parachute", "Añade un paracaídas al inventario.", "Gives CJ a parachute.", listOf("◀", "▶", "L", "ZL", "R", "ZR", "ZR", "▲", "▼", "▶", "L")),
        TrucoItem(11, JuegoGta.SAN_ANDREAS, "Subir nivel de búsqueda", "Raise Wanted Level", "Aumenta la búsqueda policial (+2 estrellas).", "Increases police wanted level (+2 stars).", listOf("R", "R", "A", "ZR", "◀", "▶", "◀", "▶", "◀", "▶")),
        TrucoItem(12, JuegoGta.SAN_ANDREAS, "Bajar nivel de búsqueda", "Lower Wanted Level", "Elimina estrellas policiales.", "Clears police wanted stars.", listOf("R", "R", "A", "ZR", "▲", "▼", "▲", "▼", "▲", "▼")),
        TrucoItem(13, JuegoGta.SAN_ANDREAS, "Nunca ser buscado", "Never Wanted", "Bloquea el nivel de búsqueda en cero.", "Locks wanted level at zero.", listOf("A", "▶", "A", "▶", "◀", "Y", "X", "▲")),
        TrucoItem(14, JuegoGta.SAN_ANDREAS, "6 estrellas de búsqueda", "6-Star Wanted Level", "Activa el nivel máximo de persecución.", "Sets wanted level to 6 stars.", listOf("A", "▶", "A", "▶", "◀", "Y", "B", "▼")),
        TrucoItem(15, JuegoGta.SAN_ANDREAS, "Musculatura al máximo", "Max Muscle", "CJ alcanza el 100% de musculatura.", "Maximizes CJ's muscle.", listOf("X", "▲", "▲", "◀", "▶", "Y", "A", "◀")),
        TrucoItem(16, JuegoGta.SAN_ANDREAS, "Gordura al máximo", "Max Fat", "CJ alcanza el 100% de peso.", "Maximizes CJ's fat.", listOf("X", "▲", "▲", "◀", "▶", "Y", "A", "▼")),
        TrucoItem(17, JuegoGta.SAN_ANDREAS, "Delgado", "Skinny CJ", "Reduce grasa y musculatura al mínimo.", "Sets fat and muscle to minimum.", listOf("X", "▲", "▲", "◀", "▶", "Y", "A", "▶")),
        TrucoItem(18, JuegoGta.SAN_ANDREAS, "Respeto al máximo", "Max Respect", "Llena la barra de respeto al 100%.", "Maximizes respect.", listOf("L", "R", "X", "▼", "ZR", "B", "L", "▲", "ZL", "ZL", "L", "L")),
        TrucoItem(19, JuegoGta.SAN_ANDREAS, "Sex Appeal al máximo", "Max Sex Appeal", "Llena el atractivo al 100%.", "Maximizes sex appeal.", listOf("A", "X", "X", "▲", "A", "R", "ZL", "▲", "X", "L", "L", "L")),
        TrucoItem(20, JuegoGta.SAN_ANDREAS, "Resistencia al máximo", "Max Stamina", "Permite correr y pedalear sin cansarse.", "Unlimited sprint stamina.", listOf("▲", "B", "X", "B", "X", "B", "Y", "ZR", "▶")),
        TrucoItem(21, JuegoGta.SAN_ANDREAS, "Nivel Sicario en armas", "Hitman Weapon Skill", "Permite usar armas dobles y máxima puntería.", "Hitman rank for all weapons.", listOf("▼", "Y", "B", "◀", "R", "ZR", "◀", "▼", "▼", "L", "L", "L")),
        TrucoItem(22, JuegoGta.SAN_ANDREAS, "Conducción al máximo", "Max Vehicle Skills", "Máximo nivel en autos, motos, bicis y aviones.", "Max driving, bike, cycling and flying skill.", listOf("Y", "ZL", "B", "R", "ZL", "ZL", "◀", "R", "▶", "L", "L", "L")),
        TrucoItem(23, JuegoGta.SAN_ANDREAS, "Súper puñetazos", "Mega Punch", "Un golpe lanza volando a los enemigos.", "One-hit knockout punches.", listOf("▲", "◀", "B", "X", "R", "A", "A", "A", "ZL")),
        TrucoItem(24, JuegoGta.SAN_ANDREAS, "Súper salto", "Mega Jump", "CJ salta a gran altura.", "Huge jump height.", listOf("▲", "▲", "X", "X", "▲", "▲", "◀", "▶", "Y", "ZR", "ZR")),
        TrucoItem(25, JuegoGta.SAN_ANDREAS, "Salto gigante en BMX", "Huge BMX Hop", "Saltos enormes en bicicleta.", "Massive bunny hops on bikes.", listOf("X", "Y", "A", "A", "Y", "A", "A", "L", "ZL", "ZL", "R", "ZR")),
        TrucoItem(26, JuegoGta.SAN_ANDREAS, "Tanque Rhino", "Rhino Tank", "Invoca un tanque militar.", "Spawns a military tank.", listOf("A", "A", "L", "A", "A", "A", "L", "ZL", "R", "X", "A", "X")),
        TrucoItem(27, JuegoGta.SAN_ANDREAS, "Caza Hydra", "Hydra Jet", "Genera un caza Hydra con despegue vertical.", "Spawns a Hydra fighter jet.", listOf("X", "X", "Y", "A", "B", "L", "L", "▼", "▲")),
        TrucoItem(28, JuegoGta.SAN_ANDREAS, "Helicóptero Hunter", "Hunter Helicopter", "Genera un helicóptero de ataque Hunter.", "Spawns a Hunter attack helicopter.", listOf("A", "B", "L", "A", "A", "L", "A", "R", "ZR", "ZL", "L", "L")),
        TrucoItem(29, JuegoGta.SAN_ANDREAS, "Avioneta Stuntplane", "Stuntplane", "Genera una avioneta acrobática.", "Spawns a Stuntplane.", listOf("A", "▲", "L", "ZL", "▼", "R", "L", "L", "◀", "◀", "B", "X")),
        TrucoItem(30, JuegoGta.SAN_ANDREAS, "Aerodeslizador Vortex", "Vortex Hovercraft", "Genera un vehículo anfibio Vortex.", "Spawns a Vortex hovercraft.", listOf("X", "X", "Y", "A", "B", "L", "ZL", "▼", "▼")),
        TrucoItem(31, JuegoGta.SAN_ANDREAS, "Cuatrimoto Quad", "Quadbike", "Genera una cuatrimoto todo terreno.", "Spawns a Quadbike.", listOf("◀", "◀", "▼", "▼", "▲", "▲", "Y", "A", "X", "R", "ZR")),
        TrucoItem(32, JuegoGta.SAN_ANDREAS, "Monster Truck", "Monster Truck", "Genera una camioneta Monster.", "Spawns a Monster Truck.", listOf("▶", "▲", "R", "R", "R", "▼", "X", "X", "B", "A", "L", "L")),
        TrucoItem(33, JuegoGta.SAN_ANDREAS, "Hotring Racer 1", "Hotring Racer 1", "Genera un auto de carreras Hotring.", "Spawns Hotring Racer #1.", listOf("R", "A", "ZR", "▶", "L", "ZL", "B", "B", "Y", "R")),
        TrucoItem(34, JuegoGta.SAN_ANDREAS, "Hotring Racer 2", "Hotring Racer 2", "Genera la variante 2 del Hotring Racer.", "Spawns Hotring Racer #2.", listOf("ZR", "L", "A", "▶", "L", "R", "▶", "▲", "A", "ZR")),
        TrucoItem(35, JuegoGta.SAN_ANDREAS, "Bloodring Banger", "Bloodring Banger", "Genera un auto de demolición Bloodring.", "Spawns a Bloodring Banger.", listOf("▼", "R", "A", "ZL", "ZL", "B", "R", "L", "◀", "◀")),
        TrucoItem(36, JuegoGta.SAN_ANDREAS, "Rancher", "Rancher", "Genera una camioneta 4x4 Rancher.", "Spawns a Rancher SUV.", listOf("▲", "▶", "▶", "L", "▶", "▲", "Y", "ZL")),
        TrucoItem(37, JuegoGta.SAN_ANDREAS, "Limusina Stretch", "Stretch Limo", "Genera una limusina de lujo.", "Spawns a Stretch limousine.", listOf("ZR", "▲", "ZL", "◀", "◀", "R", "L", "A", "▶")),
        TrucoItem(38, JuegoGta.SAN_ANDREAS, "Coche fúnebre Romero", "Romero's Hearse", "Genera un coche fúnebre.", "Spawns Romero's Hearse.", listOf("▼", "ZR", "▼", "R", "ZL", "◀", "R", "L", "◀", "▶")),
        TrucoItem(39, JuegoGta.SAN_ANDREAS, "Camión de basura Trashmaster", "Trashmaster", "Genera un camión de basura.", "Spawns a Trashmaster.", listOf("A", "R", "A", "R", "◀", "◀", "R", "L", "A", "▶")),
        TrucoItem(40, JuegoGta.SAN_ANDREAS, "Carrito de golf Caddy", "Caddy", "Genera un carrito de golf.", "Spawns a golf Caddy.", listOf("A", "L", "▲", "R", "ZL", "B", "R", "L", "A", "B")),
        TrucoItem(41, JuegoGta.SAN_ANDREAS, "Bulldozer", "Dozer", "Genera una excavadora Bulldozer.", "Spawns a Bulldozer.", listOf("ZR", "L", "L", "▶", "▶", "▲", "▲", "B", "L", "◀")),
        TrucoItem(42, JuegoGta.SAN_ANDREAS, "Camión cisterna Tanker", "Tanker", "Genera un tráiler con remolque cisterna.", "Spawns a Tanker truck with trailer.", listOf("R", "▲", "◀", "▶", "ZR", "▲", "▶", "Y", "▶", "ZL", "L", "L")),
        TrucoItem(43, JuegoGta.SAN_ANDREAS, "Nitro en todos los autos", "All Cars Have Nitro", "Equipa óxido nitroso en todos los autos.", "Gives all cars nitro boost.", listOf("◀", "X", "R", "L", "▲", "Y", "X", "▼", "A", "ZL", "L", "L")),
        TrucoItem(44, JuegoGta.SAN_ANDREAS, "Autos voladores", "Flying Cars", "Los autos vuelan como aviones.", "Cars can fly.", listOf("Y", "▼", "ZL", "▲", "L", "A", "▲", "B", "◀")),
        TrucoItem(45, JuegoGta.SAN_ANDREAS, "Barcos voladores", "Flying Boats", "Los barcos planean sobre el aire.", "Boats can glide in the air.", listOf("ZR", "A", "▲", "L", "▶", "R", "▶", "▲", "Y", "X")),
        TrucoItem(46, JuegoGta.SAN_ANDREAS, "Conducir sobre el agua", "Drive on Water", "Los vehículos conducen sobre el mar.", "Cars drive on water.", listOf("▶", "ZR", "A", "R", "ZL", "Y", "R", "ZR")),
        TrucoItem(47, JuegoGta.SAN_ANDREAS, "Coche indestructible", "Invincible Car", "Tu vehículo es indestructible y explota lo que toca.", "Your car is invincible and destroys on touch.", listOf("L", "ZL", "ZL", "▲", "▼", "▼", "▲", "R", "ZR", "ZR")),
        TrucoItem(48, JuegoGta.SAN_ANDREAS, "Explotar todos los autos", "Blow Up All Cars", "Destruye todos los vehículos cercanos.", "Blows up all nearby cars.", listOf("ZR", "ZL", "R", "L", "ZL", "ZR", "Y", "X", "A", "X", "ZL", "L")),
        TrucoItem(49, JuegoGta.SAN_ANDREAS, "Autos flotan al chocar", "Cars Float Away", "Los autos chocados flotan hacia el cielo.", "Hit cars float away.", listOf("Y", "ZR", "▼", "▼", "◀", "▼", "◀", "◀", "ZL", "B")),
        TrucoItem(50, JuegoGta.SAN_ANDREAS, "Autos invisibles", "Invisible Cars", "Solo se ven las llantas de los autos.", "Only wheels are visible.", listOf("X", "L", "X", "ZR", "Y", "L", "L")),
        TrucoItem(51, JuegoGta.SAN_ANDREAS, "Conducción perfecta y salto", "Perfect Handling & Hop", "Manejo perfecto y salto de suspensión con L3.", "Perfect handling and L3 bunny hop.", listOf("X", "R", "R", "◀", "R", "L", "ZR", "L")),
        TrucoItem(52, JuegoGta.SAN_ANDREAS, "Taxis con nitro y salto", "Taxis Have Nitro & Hop", "Todos los taxis tienen turbo y suspensión.", "All taxis get nitro and hop.", listOf("▲", "B", "X", "B", "X", "B", "Y", "ZR", "▶")),
        TrucoItem(53, JuegoGta.SAN_ANDREAS, "Semáforos en verde", "Always Green Lights", "Todos los semáforos permanecen en verde.", "All traffic lights stay green.", listOf("▶", "R", "▲", "ZL", "ZL", "◀", "R", "L", "R", "R")),
        TrucoItem(54, JuegoGta.SAN_ANDREAS, "Tráfico rosa", "Pink Traffic", "Todos los autos se pintan de rosa.", "Turns all traffic pink.", listOf("A", "L", "▼", "ZL", "◀", "B", "R", "L", "▶", "B")),
        TrucoItem(55, JuegoGta.SAN_ANDREAS, "Tráfico negro", "Black Traffic", "Todos los autos se pintan de negro.", "Turns all traffic black.", listOf("A", "ZL", "▲", "R", "◀", "B", "R", "L", "◀", "A")),
        TrucoItem(56, JuegoGta.SAN_ANDREAS, "Solo autos deportivos", "Sports Cars Traffic", "El tráfico consiste en autos deportivos rápidos.", "Traffic becomes fast sports cars.", listOf("▲", "L", "R", "▲", "▶", "▲", "B", "ZL", "B", "L")),
        TrucoItem(57, JuegoGta.SAN_ANDREAS, "Solo autos viejos", "Junk Car Traffic", "El tráfico consiste en autos viejos.", "Traffic becomes junk cars.", listOf("ZL", "▶", "L", "▲", "B", "L", "ZL", "ZR", "R", "L", "L", "L")),
        TrucoItem(58, JuegoGta.SAN_ANDREAS, "Tráfico rural", "Country Traffic", "Vehículos y peatones de campo en toda la ciudad.", "Country vehicles and peds everywhere.", listOf("L", "L", "R", "R", "ZL", "L", "ZR", "▼", "◀", "▲")),
        TrucoItem(59, JuegoGta.SAN_ANDREAS, "Poco tráfico", "Reduced Traffic", "Reduce casi por completo los autos y peatones.", "Greatly reduces traffic and pedestrians.", listOf("B", "▼", "▲", "ZR", "▼", "X", "L", "X", "◀")),
        TrucoItem(60, JuegoGta.SAN_ANDREAS, "Conductores agresivos", "Aggressive Drivers", "Los conductores disparan y chocan.", "Drivers become aggressive.", listOf("ZR", "A", "R", "ZL", "◀", "R", "L", "ZR", "ZL")),
        TrucoItem(61, JuegoGta.SAN_ANDREAS, "Reclutar con 9mm", "Recruit Anyone (9mm)", "Recluta cualquier peatón armado con pistola.", "Recruit any pedestrian with a 9mm pistol.", listOf("▼", "Y", "▲", "ZR", "ZR", "▲", "▶", "▶", "▲")),
        TrucoItem(62, JuegoGta.SAN_ANDREAS, "Reclutar con lanzacohetes", "Recruit Anyone (Rockets)", "Recluta cualquier peatón con lanzacohetes.", "Recruit any pedestrian with a rocket launcher.", listOf("ZR", "ZR", "ZR", "B", "ZL", "L", "ZR", "L", "▼", "B")),
        TrucoItem(63, JuegoGta.SAN_ANDREAS, "Disturbios en la ciudad", "Pedestrian Riot", "Caos total y peleas en las calles.", "Citywide pedestrian riot.", listOf("▼", "◀", "▲", "◀", "B", "ZR", "R", "ZL", "L")),
        TrucoItem(64, JuegoGta.SAN_ANDREAS, "Peatones armados", "Armed Pedestrians", "Todos los peatones llevan armas.", "All pedestrians are armed.", listOf("ZR", "R", "B", "X", "B", "X", "▲", "▼")),
        TrucoItem(65, JuegoGta.SAN_ANDREAS, "Peatones con lanzacohetes", "Peds Attack With Rockets", "Peatones hostiles armados con lanzacohetes.", "Hostile pedestrians with rocket launchers.", listOf("B", "L", "▲", "Y", "▼", "B", "ZL", "X", "▼", "ZR", "L", "L")),
        TrucoItem(66, JuegoGta.SAN_ANDREAS, "Pandillas por todas partes", "Gangs Everywhere", "Las bandas controlan todas las calles.", "Gangs spawn everywhere.", listOf("ZL", "▲", "R", "R", "◀", "R", "R", "ZR", "▶", "▼")),
        TrucoItem(67, JuegoGta.SAN_ANDREAS, "Modo Fiesta en la playa", "Beach Party Mode", "Peatones en bañador y autos de playa.", "Beachwear peds and beach vehicles.", listOf("▲", "▲", "▼", "▼", "Y", "A", "L", "R", "X", "▼")),
        TrucoItem(68, JuegoGta.SAN_ANDREAS, "Modo Ninja", "Ninja Theme", "Peatones con katanas y autos negros.", "Katana-wielding peds and black cars.", listOf("B", "B", "▼", "ZR", "ZL", "A", "R", "A", "Y")),
        TrucoItem(69, JuegoGta.SAN_ANDREAS, "Modo Payasos", "Funhouse / Clown Theme", "Peatones disfrazados de payasos y comida rápida.", "Clown and fast-food themed peds.", listOf("X", "X", "L", "Y", "Y", "A", "Y", "▼", "A")),
        TrucoItem(70, JuegoGta.SAN_ANDREAS, "Modo Elvis", "Elvis Everywhere", "Todos los peatones son imitadores de Elvis.", "All pedestrians become Elvis.", listOf("L", "A", "X", "L", "L", "Y", "ZL", "▲", "▼", "◀")),
        TrucoItem(71, JuegoGta.SAN_ANDREAS, "Imán de chicas", "Babe Magnet", "Atrae a las mujeres hacia CJ.", "Attracts women to CJ.", listOf("Y", "▲", "Y", "▲", "◀", "▶", "Y", "B", "X", "L", "L", "L")),
        TrucoItem(72, JuegoGta.SAN_ANDREAS, "Apuntar libre al conducir", "Free Aim While Driving", "Permite apuntar cualquier arma al conducir.", "Free weapon aiming while driving.", listOf("▲", "▲", "Y", "ZL", "▶", "B", "R", "▼", "A")),
        TrucoItem(73, JuegoGta.SAN_ANDREAS, "Reloj súper rápido", "Faster Clock", "Los días pasan mucho más rápido.", "In-game clock runs faster.", listOf("A", "A", "L", "Y", "L", "Y", "Y", "Y", "L", "X", "A", "X")),
        TrucoItem(74, JuegoGta.SAN_ANDREAS, "Cámara rápida", "Fast Gameplay", "Acelera la velocidad de todo el juego.", "Speeds up overall gameplay.", listOf("X", "▲", "▶", "▼", "ZL", "L", "Y")),
        TrucoItem(75, JuegoGta.SAN_ANDREAS, "Cámara lenta", "Slow Motion", "Ralentiza la acción del juego.", "Slows down gameplay.", listOf("X", "▲", "▶", "▼", "Y", "ZR", "R")),
        TrucoItem(76, JuegoGta.SAN_ANDREAS, "Siempre medianoche", "Always Midnight", "Congela la hora a las 00:00.", "Locks time at 00:00.", listOf("Y", "L", "R", "▶", "B", "▲", "L", "◀", "◀")),
        TrucoItem(77, JuegoGta.SAN_ANDREAS, "Atardecer naranja", "Orange Sky", "Congela el cielo en un atardecer naranja.", "Locks sky at 21:00 orange sunset.", listOf("◀", "◀", "ZL", "R", "▶", "Y", "Y", "L", "ZL", "B")),
        TrucoItem(78, JuegoGta.SAN_ANDREAS, "Clima soleado", "Sunny Weather", "Cielo despejado.", "Clear sunny sky.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "X")),
        TrucoItem(79, JuegoGta.SAN_ANDREAS, "Clima muy soleado", "Very Sunny Weather", "Calor intenso y sol brillante.", "Extra sunny weather.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "▼")),
        TrucoItem(80, JuegoGta.SAN_ANDREAS, "Clima nublado", "Cloudy Weather", "Cielo cubierto de nubes.", "Overcast cloudy weather.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "Y")),
        TrucoItem(81, JuegoGta.SAN_ANDREAS, "Clima lluvioso", "Rainy Weather", "Lluvia fuerte.", "Heavy rain.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "A")),
        TrucoItem(82, JuegoGta.SAN_ANDREAS, "Clima con niebla", "Foggy Weather", "Niebla densa.", "Dense fog.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "B")),
        TrucoItem(83, JuegoGta.SAN_ANDREAS, "Tormenta de arena", "Sandstorm", "Activa una tormenta de arena con poca visibilidad.", "Triggers a dense sandstorm.", listOf("▲", "▼", "L", "L", "ZL", "ZL", "L", "ZL", "R", "ZR")),

        // ==================== GTA VICE CITY · DEFINITIVE (SWITCH) ====================
        TrucoItem(101, JuegoGta.VICE_CITY, "Pack de armas 1", "Weapons Pack 1", "Puño americano, bate, molotov, pistola, escopeta, tec-9, rifle, lanzacohetes y francotirador.", "Brass knuckles, bat, molotov, pistol, shotgun, tec-9, rifle, rocket launcher and sniper.", listOf("R", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(102, JuegoGta.VICE_CITY, "Pack de armas 2", "Weapons Pack 2", "Katana, granadas remotas, Python, SPAS-12, M4, lanzacohetes y láser.", "Katana, remote grenades, Python, SPAS-12, M4, rocket launcher and laser sniper.", listOf("R", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▼", "◀")),
        TrucoItem(103, JuegoGta.VICE_CITY, "Pack de armas 3", "Weapons Pack 3", "Motosierra, granadas, Python, SPAS-12, MP5, M4, minigun y francotirador.", "Chainsaw, grenades, Python, SPAS-12, MP5, M4, minigun and sniper.", listOf("R", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▼", "▼")),
        TrucoItem(104, JuegoGta.VICE_CITY, "Salud al máximo", "Max Health", "Restaura la salud por completo.", "Restores health to full.", listOf("R", "ZR", "L", "A", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(105, JuegoGta.VICE_CITY, "Blindaje al máximo", "Max Armor", "Chaleco antibalas al 100%.", "Body armor at 100%.", listOf("R", "ZR", "L", "B", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(106, JuegoGta.VICE_CITY, "Subir nivel de búsqueda", "Raise Wanted Level", "Aumenta 2 estrellas de policía.", "Increases wanted level by 2 stars.", listOf("R", "R", "A", "ZR", "◀", "▶", "◀", "▶", "◀", "▶")),
        TrucoItem(107, JuegoGta.VICE_CITY, "Bajar nivel de búsqueda", "Lower Wanted Level", "Elimina las estrellas de búsqueda.", "Clears wanted level.", listOf("R", "R", "A", "ZR", "▲", "▼", "▲", "▼", "▲", "▼")),
        TrucoItem(108, JuegoGta.VICE_CITY, "Tanque Rhino", "Rhino Tank", "Genera el tanque en Vice City.", "Spawns the tank in Vice City.", listOf("A", "A", "L", "A", "A", "A", "L", "ZL", "R", "X", "A", "X")),
        TrucoItem(109, JuegoGta.VICE_CITY, "Hotring Racer 1", "Hotring Racer 1", "Genera un auto de carreras Hotring.", "Spawns Hotring Racer #1.", listOf("R", "A", "ZR", "▶", "L", "ZL", "B", "B", "Y", "R")),
        TrucoItem(110, JuegoGta.VICE_CITY, "Hotring Racer 2", "Hotring Racer 2", "Genera la variante 2 del Hotring Racer.", "Spawns Hotring Racer #2.", listOf("ZR", "L", "A", "▶", "L", "R", "▶", "▲", "A", "ZR")),
        TrucoItem(111, JuegoGta.VICE_CITY, "Bloodring Banger", "Bloodring Banger", "Genera un auto de demolición Bloodring.", "Spawns a Bloodring Banger.", listOf("▲", "▶", "▶", "L", "▶", "▲", "Y", "ZL")),
        TrucoItem(112, JuegoGta.VICE_CITY, "Bloodring Racer", "Bloodring Racer", "Genera el auto Bloodring Racer.", "Spawns a Bloodring Racer.", listOf("▼", "R", "A", "ZL", "ZL", "B", "R", "L", "◀", "◀")),
        TrucoItem(113, JuegoGta.VICE_CITY, "Sabre Turbo", "Sabre Turbo", "Genera un Sabre Turbo clásico.", "Spawns a Sabre Turbo.", listOf("▶", "ZL", "▼", "ZL", "ZL", "B", "R", "L", "A", "◀")),
        TrucoItem(114, JuegoGta.VICE_CITY, "Limusina Love Fist", "Love Fist Limo", "Genera la limusina de Love Fist.", "Spawns the Love Fist limousine.", listOf("ZR", "▲", "ZL", "◀", "◀", "R", "L", "A", "▶")),
        TrucoItem(115, JuegoGta.VICE_CITY, "Coche fúnebre Romero", "Romero's Hearse", "Genera un coche fúnebre.", "Spawns Romero's Hearse.", listOf("▼", "ZR", "▼", "R", "ZL", "◀", "R", "L", "◀", "▶")),
        TrucoItem(116, JuegoGta.VICE_CITY, "Camión de basura Trashmaster", "Trashmaster", "Genera un camión de basura.", "Spawns a Trashmaster.", listOf("A", "R", "A", "R", "◀", "◀", "R", "L", "A", "▶")),
        TrucoItem(117, JuegoGta.VICE_CITY, "Carrito de golf Caddie", "Caddie", "Genera un carrito de golf.", "Spawns a golf Caddy.", listOf("A", "L", "▲", "R", "ZL", "B", "R", "L", "A", "B")),
        TrucoItem(118, JuegoGta.VICE_CITY, "Explotar todos los autos", "Blow Up All Cars", "Hace explotar todos los vehículos cercanos.", "Destroys all nearby cars.", listOf("ZR", "ZL", "R", "L", "ZL", "ZR", "Y", "X", "A", "X", "ZL", "L")),
        TrucoItem(119, JuegoGta.VICE_CITY, "Autos voladores", "Flying Cars", "Los autos planean al tomar velocidad.", "Cars glide when picking up speed.", listOf("▶", "ZR", "A", "R", "ZL", "▼", "L", "R")),
        TrucoItem(120, JuegoGta.VICE_CITY, "Conducir sobre el agua", "Drive on Water", "Los autos flotan y avanzan sobre el mar.", "Cars drive on water.", listOf("▶", "ZR", "A", "R", "ZL", "Y", "R", "ZR")),
        TrucoItem(121, JuegoGta.VICE_CITY, "Barcos voladores", "Flying Boats", "Los botes planean en el aire.", "Boats can glide in the air.", listOf("ZR", "A", "▲", "L", "▶", "R", "▶", "▲", "Y", "X")),
        TrucoItem(122, JuegoGta.VICE_CITY, "Mejor conducción", "Better Handling", "Agarres extremos y salto con L3.", "Improved handling and hop with L3.", listOf("X", "R", "R", "◀", "R", "L", "ZR", "L")),
        TrucoItem(123, JuegoGta.VICE_CITY, "Tráfico rosa", "Pink Cars", "Todos los vehículos se vuelven rosa.", "Turns all traffic pink.", listOf("A", "L", "▼", "ZL", "◀", "B", "R", "L", "▶", "B")),
        TrucoItem(124, JuegoGta.VICE_CITY, "Tráfico negro", "Black Cars", "Todos los vehículos se vuelven negros.", "Turns all traffic black.", listOf("A", "ZL", "▲", "R", "◀", "B", "R", "L", "◀", "A")),
        TrucoItem(125, JuegoGta.VICE_CITY, "Ruedas grandes", "Big Wheels", "Agranda los neumáticos de los autos.", "Enlarges vehicle wheels.", listOf("R", "B", "X", "▶", "ZR", "Y", "▲", "▼", "Y")),
        TrucoItem(126, JuegoGta.VICE_CITY, "Semáforos en verde", "Green Traffic Lights", "Todos los semáforos cambian a verde.", "All traffic lights turn green.", listOf("▶", "R", "▲", "ZL", "ZL", "◀", "R", "L", "R", "R")),
        TrucoItem(127, JuegoGta.VICE_CITY, "Conductores agresivos", "Aggressive Drivers", "El tráfico conduce de forma agresiva.", "Traffic drives aggressively.", listOf("ZR", "A", "R", "ZL", "◀", "R", "L", "ZR", "ZL")),
        TrucoItem(128, JuegoGta.VICE_CITY, "Atuendo aleatorio", "Random Outfit", "Cambia la ropa de Tommy al azar.", "Changes Tommy's outfit.", listOf("▶", "▶", "◀", "▲", "L", "ZL", "◀", "▲", "▼", "▶")),
        TrucoItem(129, JuegoGta.VICE_CITY, "Jugar como Ricardo Diaz", "Play as Ricardo Diaz", "Cambia el modelo a Ricardo Diaz.", "Changes skin to Ricardo Diaz.", listOf("L", "ZL", "R", "ZR", "▼", "L", "ZR", "ZL")),
        TrucoItem(130, JuegoGta.VICE_CITY, "Jugar como Lance Vance", "Play as Lance Vance", "Cambia el modelo a Lance Vance.", "Changes skin to Lance Vance.", listOf("A", "ZL", "◀", "B", "R", "L", "B", "L")),
        TrucoItem(131, JuegoGta.VICE_CITY, "Jugar como Ken Rosenberg", "Play as Ken Rosenberg", "Cambia el modelo a Ken Rosenberg.", "Changes skin to Ken Rosenberg.", listOf("▶", "L", "▲", "ZL", "L", "▶", "R", "L", "B", "R")),
        TrucoItem(132, JuegoGta.VICE_CITY, "Jugar como Hilary King", "Play as Hilary King", "Cambia el modelo a Hilary King.", "Changes skin to Hilary King.", listOf("R", "A", "ZR", "L", "▶", "R", "L", "B", "ZR")),
        TrucoItem(133, JuegoGta.VICE_CITY, "Jugar como Love Fist 1", "Play as Love Fist #1", "Cambia el modelo a Jezz Torrent.", "Changes skin to Jezz Torrent.", listOf("▼", "L", "▼", "ZL", "◀", "B", "R", "L", "B", "B")),
        TrucoItem(134, JuegoGta.VICE_CITY, "Jugar como Love Fist 2", "Play as Love Fist #2", "Cambia el modelo a Dick.", "Changes skin to Dick.", listOf("R", "ZL", "ZR", "L", "▶", "ZR", "◀", "B", "Y", "L")),
        TrucoItem(135, JuegoGta.VICE_CITY, "Jugar como Phil Cassidy", "Play as Phil Cassidy", "Cambia el modelo a Phil Cassidy.", "Changes skin to Phil Cassidy.", listOf("▶", "R", "▲", "ZR", "L", "▶", "R", "L", "▶", "A")),
        TrucoItem(136, JuegoGta.VICE_CITY, "Jugar como Sonny Forelli", "Play as Sonny Forelli", "Cambia el modelo a Sonny Forelli.", "Changes skin to Sonny Forelli.", listOf("A", "L", "A", "ZL", "◀", "B", "R", "L", "B", "B")),
        TrucoItem(137, JuegoGta.VICE_CITY, "Jugar como Mercedes", "Play as Mercedes", "Cambia el modelo a Mercedes Cortez.", "Changes skin to Mercedes Cortez.", listOf("ZR", "L", "▲", "L", "▶", "R", "▶", "▲", "A", "X")),
        TrucoItem(138, JuegoGta.VICE_CITY, "Disturbios en Vice City", "Pedestrian Riot", "Los peatones se atacan entre sí.", "Pedestrians fight each other.", listOf("▼", "◀", "▲", "◀", "B", "ZR", "R", "ZL", "L")),
        TrucoItem(139, JuegoGta.VICE_CITY, "Peatones hostiles", "Hostile Pedestrians", "Todos los peatones atacan a Tommy.", "Pedestrians attack Tommy.", listOf("▼", "▲", "▲", "▲", "B", "ZR", "R", "ZL", "ZL")),
        TrucoItem(140, JuegoGta.VICE_CITY, "Peatones armados", "Armed Pedestrians", "Todos los ciudadanos portan armas.", "All pedestrians carry weapons.", listOf("ZR", "R", "B", "X", "B", "X", "▲", "▼")),
        TrucoItem(141, JuegoGta.VICE_CITY, "Mujeres armadas", "Armed Female Peds", "Las mujeres peatones llevan armas.", "Female pedestrians carry weapons.", listOf("▶", "L", "A", "ZL", "◀", "B", "R", "L", "L", "B")),
        TrucoItem(142, JuegoGta.VICE_CITY, "Mujeres siguen a Tommy", "Fanny Magnet", "Las mujeres siguen al jugador.", "Women follow Tommy.", listOf("A", "B", "L", "L", "ZR", "B", "B", "A", "X")),
        TrucoItem(143, JuegoGta.VICE_CITY, "Modo playa", "Beach Mode", "Peatones en traje de baño.", "Beachwear pedestrians.", listOf("▲", "▲", "▼", "▼", "Y", "A", "L", "R", "X", "▼")),
        TrucoItem(144, JuegoGta.VICE_CITY, "Reloj rápido", "Faster Clock", "El reloj avanza más rápido.", "Clock runs faster.", listOf("A", "A", "L", "Y", "L", "Y", "Y", "Y", "L", "X", "A", "X")),
        TrucoItem(145, JuegoGta.VICE_CITY, "Cámara rápida", "Fast Motion", "Acelera la velocidad del juego.", "Speeds up gameplay.", listOf("X", "▲", "▶", "▼", "ZL", "L", "Y")),
        TrucoItem(146, JuegoGta.VICE_CITY, "Cámara lenta", "Slow Motion", "Ralentiza la velocidad del juego.", "Slows down gameplay.", listOf("X", "▲", "▶", "▼", "Y", "ZR", "R")),
        TrucoItem(147, JuegoGta.VICE_CITY, "Clima soleado", "Sunny Weather", "Cielo despejado y soleado.", "Clear sunny weather.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "▼")),
        TrucoItem(148, JuegoGta.VICE_CITY, "Clima muy soleado", "Very Sunny Weather", "Sol intenso.", "Extra sunny weather.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "X")),
        TrucoItem(149, JuegoGta.VICE_CITY, "Clima nublado", "Cloudy Weather", "Cielo nublado.", "Cloudy sky.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "Y")),
        TrucoItem(150, JuegoGta.VICE_CITY, "Clima lluvioso", "Rainy Weather", "Tormenta y lluvia.", "Stormy rain.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "A")),
        TrucoItem(151, JuegoGta.VICE_CITY, "Clima con niebla", "Foggy Weather", "Niebla espesa.", "Foggy weather.", listOf("ZR", "B", "L", "L", "ZL", "ZL", "ZL", "B")),

        // ==================== GTA III · DEFINITIVE (SWITCH) ====================
        TrucoItem(201, JuegoGta.GTA_III, "Todas las armas", "All Weapons", "Arsenal completo disponible.", "Full weapon set available.", listOf("ZR", "ZR", "L", "ZR", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(202, JuegoGta.GTA_III, "Salud al máximo", "Max Health", "Recupera toda la vida y repara el auto.", "Restores health and repairs vehicle.", listOf("ZR", "ZR", "L", "R", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(203, JuegoGta.GTA_III, "Blindaje al máximo", "Max Armor", "Llena el blindaje al máximo.", "Fills armor to maximum.", listOf("ZR", "ZR", "L", "ZL", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(204, JuegoGta.GTA_III, "$250,000", "$250,000", "Suma dinero en efectivo.", "Adds cash money.", listOf("ZR", "ZR", "L", "L", "◀", "▼", "▶", "▲", "◀", "▼", "▶", "▲")),
        TrucoItem(205, JuegoGta.GTA_III, "Subir nivel de búsqueda", "Raise Wanted Level", "Añade 2 estrellas de policía.", "Adds 2 wanted stars.", listOf("ZR", "ZR", "L", "ZR", "◀", "▶", "◀", "▶", "◀", "▶")),
        TrucoItem(206, JuegoGta.GTA_III, "Bajar nivel de búsqueda", "Lower Wanted Level", "Elimina las estrellas de búsqueda.", "Clears wanted level.", listOf("ZR", "ZR", "L", "ZR", "▲", "▼", "▲", "▼", "▲", "▼")),
        TrucoItem(207, JuegoGta.GTA_III, "Tanque Rhino", "Rhino Tank", "Aparece un tanque Rhino.", "Spawns a Rhino tank.", listOf("A", "A", "A", "A", "A", "A", "R", "ZL", "L", "X", "A", "X")),
        TrucoItem(208, JuegoGta.GTA_III, "Explotar todos los autos", "Blow Up All Cars", "Detona los vehículos cercanos.", "Detonates nearby vehicles.", listOf("ZL", "ZR", "L", "R", "ZL", "ZR", "X", "Y", "A", "X", "ZL", "L")),
        TrucoItem(209, JuegoGta.GTA_III, "Mejor manejo de autos", "Improved Handling", "Mejora la suspensión y permite saltar con L3.", "Improves vehicle handling and hop with L3.", listOf("R", "L", "ZR", "L", "◀", "R", "R", "X")),
        TrucoItem(210, JuegoGta.GTA_III, "Autos invisibles", "Invisible Cars", "Solo se ven las ruedas de los vehículos.", "Only vehicle wheels are visible.", listOf("L", "L", "Y", "ZR", "X", "L", "X")),
        TrucoItem(211, JuegoGta.GTA_III, "Autos voladores", "Flying Cars", "Permite planear en el aire con los autos.", "Vehicles glide in the air.", listOf("▶", "ZR", "A", "R", "ZL", "▼", "L", "R")),
        TrucoItem(212, JuegoGta.GTA_III, "Cambiar atuendo", "Change Outfit", "Cambia el aspecto de Claude al azar.", "Changes Claude's pedestrian skin.", listOf("▶", "▼", "◀", "▲", "L", "ZL", "▲", "◀", "▼", "▶")),
        TrucoItem(213, JuegoGta.GTA_III, "Peatones agresivos", "Hostile Pedestrians", "Todos los peatones atacan al jugador.", "All pedestrians attack the player.", listOf("▼", "▲", "◀", "▲", "B", "R", "ZR", "ZL", "L")),
        TrucoItem(214, JuegoGta.GTA_III, "Disturbios en la ciudad", "Pedestrian Riot", "Los peatones pelean entre sí.", "Pedestrians fight each other.", listOf("▼", "▲", "◀", "▲", "B", "R", "ZR", "L", "ZL")),
        TrucoItem(215, JuegoGta.GTA_III, "Armas para peatones", "Armed Pedestrians", "Todos los peatones llevan armas.", "All pedestrians carry weapons.", listOf("ZR", "R", "X", "B", "▲", "▼", "X", "▲")),
        TrucoItem(216, JuegoGta.GTA_III, "Acelerar el reloj", "Faster Clock", "El tiempo del juego avanza más rápido.", "In-game clock runs faster.", listOf("A", "A", "A", "Y", "Y", "Y", "Y", "Y", "L", "X", "A", "X")),
        TrucoItem(217, JuegoGta.GTA_III, "Cámara rápida", "Fast Gameplay", "Acelera la velocidad general del juego.", "Speeds up overall gameplay.", listOf("X", "▲", "▶", "▼", "Y", "L", "ZL")),
        TrucoItem(218, JuegoGta.GTA_III, "Cámara lenta", "Slow Motion", "Ralentiza la velocidad del juego.", "Slows down gameplay.", listOf("X", "▲", "▶", "▼", "Y", "R", "ZR")),
        TrucoItem(219, JuegoGta.GTA_III, "Clima despejado", "Clear Weather", "Cielo completamente despejado.", "Clear sunny sky.", listOf("L", "ZL", "R", "ZR", "ZR", "R", "ZL", "X")),
        TrucoItem(220, JuegoGta.GTA_III, "Clima nublado", "Cloudy Weather", "Cielo con nubes.", "Overcast sky.", listOf("L", "ZL", "R", "ZR", "ZR", "R", "ZL", "Y")),
        TrucoItem(221, JuegoGta.GTA_III, "Clima lluvioso", "Rainy Weather", "Activa lluvia y tormenta.", "Starts rain.", listOf("L", "ZL", "R", "ZR", "ZR", "R", "ZL", "A")),
        TrucoItem(222, JuegoGta.GTA_III, "Clima con niebla", "Foggy Weather", "Niebla densa en Liberty City.", "Dense fog.", listOf("L", "ZL", "R", "ZR", "ZR", "R", "ZL", "B")),
        TrucoItem(223, JuegoGta.GTA_III, "Modo Gore", "Gore Mode", "Desmembramientos activados.", "Enables gore effects.", listOf("Y", "L", "A", "▼", "L", "R", "X", "▶", "L", "B"))
    )
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent {
            var modoOscuro by rememberSaveable { mutableStateOf(true) }
            var idiomaEspanol by rememberSaveable { mutableStateOf(true) }
            var idsGuardados by rememberSaveable { mutableStateOf(listOf<Int>()) }

            val esquemaColor = if (modoOscuro) {
                darkColorScheme(
                    background = Color(0xFF0B0B0D),
                    surface = Color(0xFF141418),
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            } else {
                lightColorScheme(
                    background = Color(0xFFF2F2F5),
                    surface = Color.White,
                    onBackground = Color(0xFF111111),
                    onSurface = Color(0xFF111111)
                )
            }

            MaterialTheme(colorScheme = esquemaColor) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GtaChetosApp(
                        modoOscuro = modoOscuro,
                        onCambiarTema = { modoOscuro = !modoOscuro },
                        idiomaEspanol = idiomaEspanol,
                        onCambiarIdioma = { idiomaEspanol = !idiomaEspanol },
                        idsGuardados = idsGuardados,
                        onAlternarGuardado = { id ->
                            idsGuardados = if (id in idsGuardados) {
                                idsGuardados - id
                            } else {
                                idsGuardados + id
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GtaChetosApp(
    modoOscuro: Boolean,
    onCambiarTema: () -> Unit,
    idiomaEspanol: Boolean,
    onCambiarIdioma: () -> Unit,
    idsGuardados: List<Int>,
    onAlternarGuardado: (Int) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var pantallaActiva by rememberSaveable { mutableStateOf<String?>(null) }
    var textoBuscador by rememberSaveable { mutableStateOf("") }
    val setGuardados = remember(idsGuardados) { idsGuardados.toHashSet() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(265.dp)
                    .statusBarsPadding(),
                drawerContainerColor = Color(0xFFF3F3F5),
                drawerContentColor = Color(0xFF1A1A1A),
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Config (Tema claro / oscuro)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCambiarTema() }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = if (idiomaEspanol) "Config" else "Settings",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = if (modoOscuro) {
                            if (idiomaEspanol) "Oscuro" else "Dark"
                        } else {
                            if (idiomaEspanol) "Claro" else "Light"
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF555555)
                    )
                }

                // Idioma
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCambiarIdioma() }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1E88E5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (idiomaEspanol) "ES" else "EN",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = if (idiomaEspanol) "Idioma" else "Language",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = if (idiomaEspanol) "Español" else "English",
                        fontSize = 12.sp,
                        color = Color(0xFF555555)
                    )
                }

                // Guardados
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            pantallaActiva = "guardados"
                            scope.launch { drawerState.close() }
                        }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFE68A00)
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = if (idiomaEspanol) "Guardados" else "Saved",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "${idsGuardados.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF444444)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ogo_cruz_azul),
                        contentDescription = "Cruz Azul",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(1.dp, Color(0xFF1E88E5)), RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFF222222)),
                        color = Color.White
                    ) {
                        Text(
                            text = "Created by Memeh",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF111111),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    ) {
        when (pantallaActiva) {
            null -> PantallaInicioJuegos(
                idiomaEspanol = idiomaEspanol,
                textoBuscador = textoBuscador,
                onCambioBuscador = { textoBuscador = it },
                setGuardados = setGuardados,
                onAlternarGuardado = onAlternarGuardado,
                onAbrirMenu = { scope.launch { drawerState.open() } },
                onSeleccionarJuego = { juego -> pantallaActiva = juego.name }
            )

            "guardados" -> PantallaListaTrucos(
                tituloCabecera = if (idiomaEspanol) "TRUCOS GUARDADOS" else "SAVED CHEATS",
                trucos = DatosGtaCheetos.trucos.filter { it.id in setGuardados },
                idiomaEspanol = idiomaEspanol,
                setGuardados = setGuardados,
                onAlternarGuardado = onAlternarGuardado,
                onVolver = { pantallaActiva = null },
                mostrarBadgeJuego = true
            )

            else -> {
                val juego = JuegoGta.entries.find { it.name == pantallaActiva } ?: JuegoGta.SAN_ANDREAS
                PantallaListaTrucos(
                    tituloCabecera = if (idiomaEspanol) juego.bannerEs else juego.bannerEn,
                    trucos = DatosGtaCheetos.trucos.filter { it.juego == juego },
                    idiomaEspanol = idiomaEspanol,
                    setGuardados = setGuardados,
                    onAlternarGuardado = onAlternarGuardado,
                    onVolver = { pantallaActiva = null },
                    mostrarBadgeJuego = false
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaInicioJuegos(
    idiomaEspanol: Boolean,
    textoBuscador: String,
    onCambioBuscador: (String) -> Unit,
    setGuardados: Set<Int>,
    onAlternarGuardado: (Int) -> Unit,
    onAbrirMenu: () -> Unit,
    onSeleccionarJuego: (JuegoGta) -> Unit
) {
    val consultaLimpia = remember(textoBuscador) { textoBuscador.trim() }

    val resultadosBusqueda = remember(consultaLimpia, idiomaEspanol) {
        if (consultaLimpia.isEmpty()) {
            emptyList()
        } else {
            DatosGtaCheetos.trucos.filter { item ->
                val titulo = if (idiomaEspanol) item.tituloEs else item.tituloEn
                val detalle = if (idiomaEspanol) item.detalleEs else item.detalleEn
                titulo.contains(consultaLimpia, ignoreCase = true) ||
                        detalle.contains(consultaLimpia, ignoreCase = true) ||
                        item.secuencia.any { it.contains(consultaLimpia, ignoreCase = true) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onAbrirMenu) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "GTA Chetos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo_switch),
                contentDescription = "Nintendo Switch",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(7.dp))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (consultaLimpia.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(resultadosBusqueda, key = { it.id }) { truco ->
                    TarjetaTrucoEstiloBoceto(
                        truco = truco,
                        idiomaEspanol = idiomaEspanol,
                        estaGuardado = truco.id in setGuardados,
                        onAlternarGuardado = { onAlternarGuardado(truco.id) },
                        mostrarBadgeJuego = true
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                JuegoGta.entries.forEach { juego ->
                    TarjetaPortadaJuego(
                        juego = juego,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = { onSeleccionarJuego(juego) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = textoBuscador,
            onValueChange = onCambioBuscador,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(if (idiomaEspanol) "Buscador" else "Search")
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (textoBuscador.isNotEmpty()) {
                    IconButton(onClick = { onCambioBuscador("") }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE9E9EC),
                unfocusedContainerColor = Color(0xFFE9E9EC),
                focusedTextColor = Color(0xFF111111),
                unfocusedTextColor = Color(0xFF111111)
            )
        )
    }
}

@Composable
fun TarjetaPortadaJuego(
    juego: JuegoGta,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.5.dp, Color.White.copy(alpha = 0.35f)), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = juego.fondoRes),
            contentDescription = juego.nombreEs,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.20f), Color.Transparent)
                    )
                )
        )
    }
}

@Composable
fun PantallaListaTrucos(
    tituloCabecera: String,
    trucos: List<TrucoItem>,
    idiomaEspanol: Boolean,
    setGuardados: Set<Int>,
    onAlternarGuardado: (Int) -> Unit,
    onVolver: () -> Unit,
    mostrarBadgeJuego: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVolver,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = if (idiomaEspanol) "Atrás" else "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (idiomaEspanol) "GTA Chetos" else "GTA Cheats",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF232325),
            border = BorderStroke(1.dp, Color(0xFF4A4A4F)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = tituloCabecera,
                color = Color(0xFFE0E0E5),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (trucos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (idiomaEspanol) "Aún no tienes trucos guardados." else "No saved cheats yet.",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(trucos, key = { it.id }) { truco ->
                    TarjetaTrucoEstiloBoceto(
                        truco = truco,
                        idiomaEspanol = idiomaEspanol,
                        estaGuardado = truco.id in setGuardados,
                        onAlternarGuardado = { onAlternarGuardado(truco.id) },
                        mostrarBadgeJuego = mostrarBadgeJuego
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TarjetaTrucoEstiloBoceto(
    truco: TrucoItem,
    idiomaEspanol: Boolean,
    estaGuardado: Boolean,
    onAlternarGuardado: () -> Unit,
    mostrarBadgeJuego: Boolean = false
) {
    val titulo = if (idiomaEspanol) truco.tituloEs else truco.tituloEn
    val detalle = if (idiomaEspanol) truco.detalleEs else truco.detalleEn

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF7F7F9),
        contentColor = Color(0xFF141416),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, Color(0xFF262629))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 44.dp)
                )

                if (mostrarBadgeJuego) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    when (truco.juego) {
                                        JuegoGta.VICE_CITY -> listOf(Color(0xFFFF4DA6), Color(0xFF9C27B0))
                                        JuegoGta.SAN_ANDREAS -> listOf(Color(0xFF2E7D32), Color(0xFF66BB6A))
                                        JuegoGta.GTA_III -> listOf(Color(0xFF283593), Color(0xFFFFB300))
                                    }
                                )
                            )
                            .border(1.dp, Color.White.copy(alpha = 0.75f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (truco.juego) {
                                JuegoGta.VICE_CITY -> "V"
                                JuegoGta.SAN_ANDREAS -> "S"
                                JuegoGta.GTA_III -> "3"
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )
                    }
                }

                Surface(
                    onClick = onAlternarGuardado,
                    shape = RoundedCornerShape(10.dp),
                    color = if (estaGuardado) Color(0xFF2E7D32) else Color(0xFFE3E5EA),
                    border = BorderStroke(
                        1.dp,
                        if (estaGuardado) Color(0xFF1B5E20) else Color(0xFF9AA0AC)
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (estaGuardado) Icons.Default.Check else Icons.Default.Add,
                            contentDescription = if (estaGuardado) "Guardado" else "Guardar",
                            tint = if (estaGuardado) Color.White else Color(0xFF1C1C1E),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = detalle,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF2D2D32),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                truco.secuencia.forEach { boton ->
                    val esHombro = boton in BOTONES_HOMBRO
                    Surface(
                        shape = if (esHombro) RoundedCornerShape(7.dp) else CircleShape,
                        color = if (esHombro) Color(0xFF23272E) else Color(0xFF1E2229),
                        border = BorderStroke(
                            1.dp,
                            if (esHombro) Color(0xFF5A6270) else Color(0xFF8DA2B5)
                        ),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = boton,
                                color = Color.White,
                                fontSize = if (boton.length > 1) 10.sp else 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}