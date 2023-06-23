package com.itstor.paimonguide.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itstor.paimonguide.R
import com.itstor.paimonguide.common.Result
import com.itstor.paimonguide.ui.components.AppBar
import com.itstor.paimonguide.ui.components.BottomBar
import com.itstor.paimonguide.ui.components.CharacterCard
import com.itstor.paimonguide.utils.ResourceUtils

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (String) -> Unit = {},
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val characterDataResult by homeViewModel.characterData.observeAsState()

    Column (modifier = modifier.fillMaxSize()) {
        AppBar(appBarTitle = stringResource(R.string.characters_title))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            when (val value = characterDataResult) {
                is Result.Success -> {
                    val characterList = value.data

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(128.dp),
                        modifier = Modifier
                            .fillMaxSize(),
                        userScrollEnabled = true,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(characterList.size + 1, key = { index -> index }) { index ->
                            if (index < characterList.size) {
                                CharacterCard(
                                    character = characterList[index],
                                    image = ResourceUtils.getImageResource(
                                        characterList[index].id,
                                        "icon",
                                        R.drawable.traveler_icon
                                    ),
                                    onClick = {
                                        navigateToDetails(characterList[index].id)
                                    }
                                )
                            } else {
                                Spacer(modifier = Modifier.height(300.dp))
                            }
                        }
                    }
                }

                is Result.Error -> {
                    Toast.makeText(LocalContext.current, stringResource(R.string.error_loading_character_data_message), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {}
            }
            BottomBar(
                navController = navController,
                modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}