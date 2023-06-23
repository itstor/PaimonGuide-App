package com.itstor.paimonguide.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itstor.paimonguide.domain.model.Character
import com.itstor.paimonguide.domain.model.Vision
import com.itstor.paimonguide.ui.theme.AmberBackground
import com.itstor.paimonguide.ui.theme.BlueBackground
import com.itstor.paimonguide.ui.theme.BlueGray
import com.itstor.paimonguide.ui.theme.GreenBackground
import com.itstor.paimonguide.ui.theme.LightBlueBackground
import com.itstor.paimonguide.ui.theme.PurpleBackground
import com.itstor.paimonguide.ui.theme.RedBrownBackground
import com.itstor.paimonguide.ui.theme.TealBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(modifier: Modifier = Modifier, character: Character, image: Int, onClick: () -> Unit) {
    val backgroundColor = when (character.vision) {
        Vision.PYRO -> RedBrownBackground
        Vision.CRYO -> LightBlueBackground
        Vision.DENDRO -> GreenBackground
        Vision.GEO -> AmberBackground
        Vision.HYDRO -> BlueBackground
        Vision.ELECTRO -> PurpleBackground
        Vision.ANEMO -> TealBackground
    }

    Card(
        modifier = modifier
            .width(220.dp)
            .height(IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .background(BlueGray)
                .fillMaxHeight()
                .padding(12.dp)
                .wrapContentHeight(Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(backgroundColor, RoundedCornerShape(16.dp))
            ) {
                Image(
                    painterResource(id = image),
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = character.name,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}