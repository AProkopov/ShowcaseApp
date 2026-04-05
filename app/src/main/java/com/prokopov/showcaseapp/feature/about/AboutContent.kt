package com.prokopov.showcaseapp.feature.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prokopov.showcaseapp.R
import com.prokopov.showcaseapp.ui.theme.ShowcaseAppTheme

@Composable
fun AboutContent(
    uiState: AboutUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Text(text = uiState.appName, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${stringResource(R.string.about_version_label)} ${uiState.versionName}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = uiState.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        AboutInfoLine("${stringResource(R.string.about_author_label)}: ${uiState.author}")
        Spacer(modifier = Modifier.height(8.dp))
        AboutInfoLine(
            text = "${stringResource(R.string.about_github_label)}: ${uiState.githubUrl}",
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun AboutInfoLine(
    text: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium, color = color, modifier = modifier)
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun AboutContentPreview() {
    ShowcaseAppTheme {
        AboutContent(
            uiState =
                AboutUiState(
                    appName = "ShowcaseApp",
                    versionName = "1.0",
                    author = "Anton Prokopov",
                    description = "Android app demonstrating best engineering practices",
                    githubUrl = "https://github.com/AProkopov/ShowcaseApp",
                ),
        )
    }
}
