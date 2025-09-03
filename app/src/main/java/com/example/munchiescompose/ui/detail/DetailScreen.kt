package com.example.munchiescompose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.munchiescompose.R
import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.ui.theme.MunchiesComposeTheme
import com.example.munchiescompose.ui.theme.Negative
import com.example.munchiescompose.ui.theme.Positive
import com.example.munchiescompose.ui.theme.Subtitle
import com.example.munchiescompose.ui.theme.Title
import com.example.munchiescompose.ui.theme.headline1
import com.example.munchiescompose.ui.theme.headline2
import com.example.munchiescompose.ui.theme.title1
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(
    navArgs = DetailScreenArgs::class,
)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navigation = viewState.navigation

    LaunchedEffect(navigation) {
        when (navigation) {
            is DetailScreenNavigation.MainScreen -> {
                navigator.popBackStack()
                viewModel.onEvent(DetailScreenEvent.NavigationComplete)
            }

            is DetailScreenNavigation.None -> {
                Unit
            }
        }
    }
    DetailScreenContent(
        modifier,
        viewState,
        { onEvent -> viewModel.onEvent(onEvent) })
}

@Composable
fun DetailScreenContent(
    modifier: Modifier = Modifier,
    viewState: DetailViewState,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    if (viewState.restaurant == null) {
        return
    }
    val restaurantName = viewState.restaurant.name
    val restaurantFilters = viewState.filtersList
    val restaurantStatus = viewState.isOpenStatus

    AsyncImage(
        model = viewState.restaurant.imageUrl,
        contentDescription = stringResource(R.string.banner_image),
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
    )
    Image(
        painter = painterResource(R.drawable.icon_chevron),
        contentDescription = stringResource(R.string.chevron_image),
        modifier
            .padding(16.dp)
            .size(24.dp)
            .clickable { onEvent(DetailScreenEvent.ChevronIconClicked) },
    )
    ElevatedCard(
        modifier
            .padding(vertical = 180.dp, horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        DetailCard(restaurantName, restaurantStatus, restaurantFilters)
    }
}


@Composable
fun DetailCard(
    name: String?,
    status: Boolean,
    filters: List<FilterEntity>,
) {
    val listFilters = filters.mapNotNull { it -> it.name }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        if (name != null) {
            Text(
                text = name,
                style = headline1,
                color = Title,
            )
        }
        Text(
            text = listFilters.joinToString(" • "),
            style = headline2,
            color = Subtitle,
        )

        if (status) {
            Text(
                text = stringResource(R.string.open),
                style = title1,
                color = Positive,
            )
        } else {
            Text(
                text = stringResource(R.string.close),
                style = title1,
                color = Negative,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentPreview() {
    MunchiesComposeTheme {
        DetailScreenContent(
            modifier = Modifier,
            viewState = DetailViewState(),
            onEvent = {},
        )
    }
}