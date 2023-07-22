package files.app.weather.Layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import files.app.weather.logic.API
import files.app.weather.logic.CardData
import files.app.weather.ui.theme.BlueLight
import files.app.weather.ui.theme.Purple
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(data: API) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    var tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos),
                    color = Purple,
                    height = 2.dp
                )
            },
            backgroundColor = BlueLight,
            modifier = Modifier.padding(bottom = 3.dp)
        ) {
            tabList.forEachIndexed { index, text ->
                Tab( selected = tabIndex == index, onClick = {
                    tabIndex = index
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(tabIndex)
                    }
                }) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        color = if (tabIndex == index) Color.White else Color.Black,
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }

    HorizontalPager(
        count = tabList.size,
        state = pagerState,
        itemSpacing = 3.dp
    ) { index ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(if (index == 0) data.hours else data.days) { cardData ->
                ListItem(cardData)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItem(data: CardData) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp),
        color = BlueLight,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                Text(data.time)
                Text(
                    text = data.weatherState,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(0.45f)
                )
            }
            Text(text = data.temperature, color = Color.White, fontSize = 25.sp)
            Image(
                painter = rememberImagePainter(data.imageURL),
                contentDescription = "weatherURL", modifier = Modifier.size(35.dp)
            )
        }
    }
}
