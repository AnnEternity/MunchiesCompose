package com.example.munchiescompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.munchiescompose.R
import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.database.RestaurantWithFilters
import com.example.munchiescompose.ui.theme.Filter
import com.example.munchiescompose.ui.theme.MunchiesComposeTheme
import com.example.munchiescompose.ui.theme.Rating
import com.example.munchiescompose.ui.theme.Selected
import com.example.munchiescompose.ui.theme.Subtitle
import com.example.munchiescompose.ui.theme.Title
import com.example.munchiescompose.ui.theme.footer1
import com.example.munchiescompose.ui.theme.rating
import com.example.munchiescompose.ui.theme.subtitle1
import com.example.munchiescompose.ui.theme.title1
import com.example.munchiescompose.ui.theme.title2
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(
    start = true,
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navigation = viewState.navigation

    LaunchedEffect(navigation) {
        when (navigation) {
            is MainScreenNavigation.DetailScreen -> {
                navigator.navigate(
                    direction = DetailScreenDestination(
                        restaurantWithFilters = navigation.restaurantWithFilters,
                    )
                )
                viewModel.onEvent(MainScreenEvent.NavigationComplete(navigation))
            }

            is MainScreenNavigation.None -> {
                Unit
            }
        }
    }

    MainScreenContent(modifier, viewState) { onEvent -> viewModel.onEvent(onEvent) }
}

@Composable
fun MainScreenContent(
    modifier: Modifier,
    viewState: MainViewState,
    onEvent: (MainScreenEvent) -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Bar()
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(viewState.filterList) {
                FilterCard(
                    filter = it,
                    onEvent = onEvent,
                    clickedFilterKey = viewState.clickedFilterKey,
                )
            }
        }
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(viewState.restaurantList) {
                RestaurantCard(
                    restaurant = it,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
fun Bar(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.umain_logo),
        modifier
    )
}

@Composable
fun FilterCard(
    modifier: Modifier = Modifier,
    filter: FilterEntity,
    onEvent: (MainScreenEvent) -> Unit,
    clickedFilterKey: List<Int?>,
) {
    if (filter.name == null || filter.url == null) {
        return
    }
    val wasClicked = clickedFilterKey.contains(filter.key)

    ElevatedCard(
        modifier = modifier.clickable { onEvent(MainScreenEvent.FilterClicked(filter)) },
        shape = RoundedCornerShape(60),
        colors = CardColors(
            contentColor = if (wasClicked) Color.White else Color.Black,
            containerColor = if (wasClicked) Selected else Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                model = filter.url,
                contentDescription = stringResource(R.string.filter_image),
                modifier = Modifier.size(48.dp),
            )
            Text(
                filter.name,
                style = title2,
                color = Filter,
            )
        }
    }
}

@Composable
fun RestaurantCard(
    modifier: Modifier = Modifier,
    restaurant: RestaurantWithFilters,
    onEvent: (MainScreenEvent) -> Unit,
) {
    val entity = restaurant.restaurantEntity

    if (
        entity.name == null ||
        entity.imageUrl == null ||
        entity.rating == null ||
        entity.deliveryTimeMinutes == null
    ) {
        return
    }

    ElevatedCard(
        modifier = modifier
            .padding(bottom = 16.dp)
            .clickable {
                onEvent(MainScreenEvent.RestaurantCardClicked(restaurant))
            },
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        ),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = entity.imageUrl,
                contentDescription = stringResource(R.string.banner_image),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            RestaurantCardFooter(
                entity.name,
                restaurant,
                restaurant.restaurantEntity.deliveryTimeMinutes,
                restaurant.restaurantEntity.rating,
            )
        }
    }
}

@Composable
private fun RestaurantCardFooter(
    name: String,
    restaurant: RestaurantWithFilters,
    deliveryTimeMinutes: Int,
    rating: Float,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                name,
                style = title1,
                color = Title,
            )
            RestaurantFiltersList(restaurant)
            DeliveryTime(deliveryTimeMinutes)
        }
        Rating(rating)
    }
}

@Composable
private fun RestaurantFiltersList(restaurant: RestaurantWithFilters) {
    val list = restaurant.filtersList.mapNotNull { it -> it.name }
    Text(
        text = list.joinToString(" • "),
        color = Subtitle,
        style = subtitle1,
    )
}

@Composable
private fun DeliveryTime(deliveryTimeMinutes: Int) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.clock_icon),
            contentDescription = stringResource(R.string.clock_icon),
            modifier = Modifier.size(10.dp),
        )
        Text(
            stringResource(
                R.string.delivery_time,
                deliveryTimeMinutes,
            ),
            style = footer1,
            color = Rating,
        )
    }
}

@Composable
private fun Rating(ratingFloat: Float) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_icon),
            contentDescription = stringResource(R.string.rating_icon),
            modifier = Modifier.size(10.dp),
        )
        Text(
            stringResource(R.string.rating, ratingFloat),
            style = rating,
            color = Rating,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MunchiesComposeTheme {
        MainScreenContent(
            modifier = Modifier,
            viewState = MainViewState(),
            onEvent = {},
        )
    }
}