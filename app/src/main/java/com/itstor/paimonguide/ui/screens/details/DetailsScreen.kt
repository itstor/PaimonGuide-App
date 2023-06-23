package com.itstor.paimonguide.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.itstor.paimonguide.R
import com.itstor.paimonguide.common.Result
import com.itstor.paimonguide.domain.model.Character
import com.itstor.paimonguide.domain.model.Rarity
import com.itstor.paimonguide.domain.model.Vision
import com.itstor.paimonguide.domain.model.Weapon
import com.itstor.paimonguide.ui.components.AppBar
import com.itstor.paimonguide.ui.components.CharacterTalentCard
import com.itstor.paimonguide.ui.components.IconText
import com.itstor.paimonguide.ui.theme.AmberBackground
import com.itstor.paimonguide.ui.theme.BlueBackground
import com.itstor.paimonguide.ui.theme.BlueGray
import com.itstor.paimonguide.ui.theme.BlueGrayBackground
import com.itstor.paimonguide.ui.theme.GreenBackground
import com.itstor.paimonguide.ui.theme.LightBlueBackground
import com.itstor.paimonguide.ui.theme.PurpleBackground
import com.itstor.paimonguide.ui.theme.Red
import com.itstor.paimonguide.ui.theme.RedBrownBackground
import com.itstor.paimonguide.ui.theme.TealBackground
import com.itstor.paimonguide.ui.theme.YellowGold
import com.itstor.paimonguide.utils.ResourceUtils

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    characterId: String,
    navigateBack: () -> Unit,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
) {
    val characterDataResult by detailsViewModel.characterData.observeAsState()
    val isFavorite by detailsViewModel.isFavorite.observeAsState()

    LaunchedEffect(Unit) {
        detailsViewModel.loadCharacterById(characterId)
        detailsViewModel.isFavoriteCharacter(characterId)
    }

    when (val value = characterDataResult) {
        is Result.Success -> {
            val character = value.data
            if (character != null) {
                DetailsContent(
                    character = character,
                    isFavorite = isFavorite ?: false,
                    toggleFavorite = { detailsViewModel.toggleFavoriteCharacter(characterId) },
                    navigateBack = { navigateBack() },
                )
            } else {
                Box(modifier = modifier) {
                    Text(text = stringResource(R.string.character_not_found_message))
                }
            }
        }

        is Result.Error -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.error_loading_character_data_message),
                Toast.LENGTH_SHORT
            ).show()
        }

        is Result.Loading -> {
            CircularProgressIndicator()
        }
        else -> {}
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    character: Character,
    isFavorite: Boolean = false,
    toggleFavorite: () -> Unit = {},
    navigateBack: () -> Unit,
) {
    val backgroundColor = when (character.vision) {
        Vision.PYRO -> RedBrownBackground
        Vision.CRYO -> LightBlueBackground
        Vision.DENDRO -> GreenBackground
        Vision.GEO -> AmberBackground
        Vision.HYDRO -> BlueBackground
        Vision.ELECTRO -> PurpleBackground
        Vision.ANEMO -> TealBackground
    }

    val characterPortraitImage = ResourceUtils.getImageResource(
        id = character.id,
        suffix = "full",
        defaultImage = R.drawable.traveler_icon
    )

    val characterStarCount = when (character.rarity) {
        Rarity.FIVE -> 5
        Rarity.FOUR -> 4
    }

    val characterWeaponIcon = when (character.weapon) {
        Weapon.BOW -> R.drawable.bow_icon
        Weapon.CATALYST -> R.drawable.catalyst_icon
        Weapon.CLAYMORE -> R.drawable.claymore_icon
        Weapon.POLEARM -> R.drawable.polearm_icon
        Weapon.SWORD -> R.drawable.sword_icon
    }

    val burstIcon = ResourceUtils.getImageResource(
        id = character.id,
        suffix = "burst_icon",
        defaultImage = R.drawable.sword_icon
    )

    val elementalIcon = ResourceUtils.getImageResource(
        id = character.id,
        suffix = "elemental_icon",
        defaultImage = R.drawable.sword_icon
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    BlueGray
                )
        ) {
            item {
                AppBar(modifier = Modifier.padding(15.dp), appBarTitle = character.name, onBackClick = navigateBack, containerColor = BlueGray)
            }
            item {
                CharacterStatsSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    hp = character.stats.hp,
                    atk = character.stats.atk,
                    def = character.stats.def,
                    rarity = characterStarCount,
                    characterImage = characterPortraitImage,
                    backgroundColor = backgroundColor
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 50.dp,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                            clip = true
                        )
                        .background(color = BlueGrayBackground)
                        .padding(24.dp)
                ) {
                    Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        IconText(icon = R.drawable.bullet_icon, text = stringResource(R.string.introduction_title))
                        IconButton(onClick = { toggleFavorite() }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = if (isFavorite) Red else Color.White,
                            )
                        }
                    }
                    Text(
                        text = character.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    IconText(icon = R.drawable.bullet_icon, text = stringResource(R.string.talents_title))
                    Spacer(modifier = Modifier.padding(4.dp))
                    CharacterTalentCard(
                        modifier = Modifier.fillMaxWidth(),
                        icon = characterWeaponIcon,
                        iconColor = backgroundColor,
                        name = character.normalAttack.name,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    CharacterTalentCard(
                        modifier = Modifier.fillMaxWidth(),
                        icon = elementalIcon,
                        iconColor = backgroundColor,
                        name = character.elementalSkill.name,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    CharacterTalentCard(
                        modifier = Modifier.fillMaxWidth(),
                        icon = burstIcon,
                        iconColor = backgroundColor,
                        name = character.burst.name,
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterStatsSection(
    modifier: Modifier = Modifier,
    hp: Int,
    atk: Int,
    def: Int,
    rarity: Int,
    characterImage: Int,
    backgroundColor: Color,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = characterImage),
                contentDescription = "Image",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .scale(1.5f)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .zIndex(1f)
                .offset(y = 20.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            backgroundColor.copy(alpha = 0.6f),
                            Color.Transparent,
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp)

                )
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .offset(y = -(13).dp),
            ) {
                repeat(rarity) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = YellowGold,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.hp), style = MaterialTheme.typography.labelSmall)
                Text(
                    text = hp.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = stringResource(R.string.atk), style = MaterialTheme.typography.labelSmall)
                Text(
                    text = atk.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = stringResource(R.string.def), style = MaterialTheme.typography.labelSmall)
                Text(
                    text = def.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
