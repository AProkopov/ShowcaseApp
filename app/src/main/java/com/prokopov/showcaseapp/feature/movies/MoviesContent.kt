package com.prokopov.showcaseapp.feature.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prokopov.showcaseapp.R
import com.prokopov.showcaseapp.ui.theme.ShowcaseAppTheme

@Suppress("TopLevelPropertyNaming")
private const val RATING_HIGH_THRESHOLD = 8.0

@Suppress("TopLevelPropertyNaming")
private const val RATING_MEDIUM_THRESHOLD = 6.0

@Suppress("TopLevelPropertyNaming")
private const val LOAD_MORE_THRESHOLD = 5

private val RatingGreenColor = Color(0xFF4CAF50)
private val RatingYellowColor = Color(0xFFFFC107)
private val RatingRedColor = Color(0xFFF44336)

@Composable
fun MoviesContent(
    uiState: MoviesUiState,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        MoviesHeader()
        when (uiState) {
            is MoviesUiState.Loading -> LoadingState()
            is MoviesUiState.Error -> ErrorState(message = uiState.message, onRetry = onRetry)
            is MoviesUiState.Success ->
                MoviesList(
                    movies = uiState.movies,
                    isLoadingMore = uiState.isLoadingMore,
                    onLoadMore = onLoadMore,
                )
        }
    }
}

@Composable
private fun MoviesHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(R.string.movies_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.movies_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.movies_retry))
        }
    }
}

@Composable
private fun MoviesList(
    movies: List<Movie>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= movies.size - LOAD_MORE_THRESHOLD
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onLoadMore()
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(items = movies, key = { it.id }) { movie ->
            MovieCard(movie = movie)
        }
        if (isLoadingMore) {
            item { LoadMoreIndicator() }
        }
    }
}

@Composable
private fun LoadMoreIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth().padding(16.dp),
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp),
        ) {
            MoviePoster(url = movie.posterUrl, title = movie.title)
            Spacer(modifier = Modifier.width(12.dp))
            MovieInfo(title = movie.title, year = movie.year, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            RatingBadge(rating = movie.rating)
        }
    }
}

@Composable
private fun MoviePoster(
    url: String?,
    title: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = url,
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier =
            modifier
                .size(width = 64.dp, height = 96.dp)
                .clip(RoundedCornerShape(8.dp)),
    )
}

@Composable
private fun MovieInfo(
    title: String,
    year: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = year,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        when {
            rating >= RATING_HIGH_THRESHOLD -> RatingGreenColor
            rating >= RATING_MEDIUM_THRESHOLD -> RatingYellowColor
            else -> RatingRedColor
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .size(40.dp)
                .background(color = backgroundColor, shape = CircleShape),
    ) {
        Text(
            text = String.format(java.util.Locale.US, "%.1f", rating),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun MoviesContentLoadingPreview() {
    ShowcaseAppTheme(darkTheme = true) {
        MoviesContent(
            uiState = MoviesUiState.Loading,
            onLoadMore = {},
            onRetry = {},
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun MoviesContentErrorPreview() {
    ShowcaseAppTheme(darkTheme = true) {
        MoviesContent(
            uiState = MoviesUiState.Error(message = "Network error"),
            onLoadMore = {},
            onRetry = {},
        )
    }
}

private val previewMovies =
    listOf(
        Movie(id = 1, title = "The Shawshank Redemption", posterUrl = null, year = "1994", rating = 9.3),
        Movie(id = 2, title = "The Godfather", posterUrl = null, year = "1972", rating = 8.7),
        Movie(id = 3, title = "The Dark Knight", posterUrl = null, year = "2008", rating = 7.5),
        Movie(id = 4, title = "Bad Movie", posterUrl = null, year = "2020", rating = 4.2),
    )

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun MoviesContentSuccessPreview() {
    ShowcaseAppTheme(darkTheme = true) {
        MoviesContent(
            uiState =
                MoviesUiState.Success(
                    movies = previewMovies,
                    isLoadingMore = false,
                    page = 1,
                ),
            onLoadMore = {},
            onRetry = {},
        )
    }
}
