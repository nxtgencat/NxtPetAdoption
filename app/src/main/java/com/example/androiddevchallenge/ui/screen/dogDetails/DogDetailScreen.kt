package com.example.androiddevchallenge.ui.screen.dogDetails

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.repository.DefaultData
import com.example.androiddevchallenge.model.Pet
import com.example.androiddevchallenge.model.PetBreeds
import com.example.androiddevchallenge.ui.screen.home.HomeViewModel
import com.example.androiddevchallenge.ui.screen.home.PetImage
import com.example.androiddevchallenge.ui.theme.colorDarkText
import com.example.androiddevchallenge.ui.theme.colorTextBody

@Composable
fun DogDetailScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    val selectedDog by viewModel.selectedDog.collectAsState()
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            DogDetailTopAppBar(
                onBackButtonClicked = { navController.popBackStack() },
                onMoreButtonClicked = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
            )
        }
    ) {paddingValues ->
        val paddingValues = paddingValues
        selectedDog?.let { dog ->
            Column(
                modifier = Modifier
                    .verticalScroll(enabled = true, state = scrollState)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                PetImage(
                    imageUrl = dog.photos.firstOrNull()?.large,
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                DogDetailContent(
                    dog = dog,
                    context = context,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DogDetailContent(
    dog: Pet,
    context: Context,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        DogNameAndBreed(
            name = dog.name,
            breed = dog.breeds,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = DefaultData.defaultDescription,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Normal),
            color = if (MaterialTheme.colors.isLight)
                colorTextBody.copy(alpha = 0.8f)
            else
                colorDarkText.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        AdoptButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onClick = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AdoptButton(
    modifier: Modifier = Modifier,
    onClick: (message: String) -> Unit,
) {
    Button(
        onClick = {
            onClick("Not Implemented")
        },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp
        ),
        modifier = modifier
    ) {
        Text(
            text = "Adopt Me",
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun DogNameAndBreed(
    name: String,
    breed: PetBreeds,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.h4
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val secBreed = if (breed.secondary == null) "" else ", ${breed.secondary}"
            Text(
                text = "${breed.primary}$secBreed",
                style = MaterialTheme.typography.body2
            )
        }
    }
}



@Composable
fun DogDetailTopAppBar(
    onBackButtonClicked: () -> Unit,
    onMoreButtonClicked: (message: String) -> Unit,
) {
    TopAppBar(
        title = { },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back icon"
                )
            }
        },
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { onMoreButtonClicked("Not Implemented") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "More Icon"
                )
            }
        }
    )
}
