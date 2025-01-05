package com.example.testsuitmedia.pages


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.testsuitmedia.R
import com.example.testsuitmedia.UserViewModel
import com.example.testsuitmedia.model.User
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(modifier: Modifier = Modifier, navController: NavController) {
    val fontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
    )
    val viewModel: UserViewModel = viewModel()
    val users by viewModel.users
    val isLoading by viewModel.isLoading
    val state = rememberLazyListState()
    val isAtBottom = !state.canScrollForward
    var nextPage = 1
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Mengambil data pertama kali ketika layar muncul
    LaunchedEffect(Unit) {
        viewModel.getUsers(page = 1, perPage = 10)
    }

    // Memanggil API hanya ketika sampai di bawah dan tidak ada proses loading yang sedang berjalan
    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoading) {
            // Jika data yang diambil lebih kecil dari perPage, berarti sudah tidak ada data lagi
            if (users.size % 10 == 0) {
                nextPage =
                    (users.size / 10) + 1 // Halaman selanjutnya hanya jika masih ada data yang bisa diambil
                viewModel.getUsers(page = nextPage, perPage = 10)
            }
        }
    }




    Scaffold(topBar = {
        SimpleCenteredAppbar(
            navController = navController, title = "Third Screen", fontFamily = fontFamily
        )
    }) { innerPadding ->
        if (isLoading && users.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator(color = Color(0xff2B637B))
            }
        } else if (!isLoading && users.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    text = "No users available",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xff04021D)
                )
            }
        } else {
            PullToRefreshBox(
                modifier = Modifier.padding(innerPadding),
                state = refreshState,
                isRefreshing = isRefreshing,
                onRefresh = {
                    coroutineScope.launch {
                        isRefreshing = true
                        viewModel.clearUsers() // Tambahkan fungsi clearUsers di ViewModel Anda untuk mengosongkan data
                        viewModel.getUsers(page = 1, perPage = 10)
                        isRefreshing = false
                    }
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 15.dp),
                    state = state
                ) {
                    items(users.size) { user ->
                        UserCard(
                            fontFamily = fontFamily,
                            user = users[user],
                            navController = navController
                        )
                    }
                    // Menampilkan indikator loading di bagian bawah jika data masih dimuat
                    if (isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ThirdScreenPreview() {
    ThirdScreen(navController = rememberNavController())
}

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily,
    user: User,
    navController: NavController
) {
    Column(modifier = Modifier.clickable {
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "selectedUserName", "${user.first_name} ${user.last_name}"
        )
        navController.popBackStack()
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = user.avatar,
                contentDescription = "User Avatar",
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${user.first_name} ${user.last_name}",
                    fontSize = 16.sp,
                    color = Color(0xff04021D),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.height(4.dp))
                Text(
                    text = "${user.email}",
                    color = Color(0xff686777),
                    fontSize = 10.sp
                )
            }

        }
        HorizontalDivider(color = Color(0xffE2E3E4))
    }

}

@Preview(showBackground = true)
@Composable
private fun UserCardPreview() {
    UserCard(
        fontFamily = FontFamily.Default,
        user = User(
            id = 1,
            avatar = "https://i1.sndcdn.com/artworks-000545727588-dqhvuc-t500x500.jpg",
            email = "eruma_eimi@gmail.com",
            first_name = "Elma",
            last_name = "Amy"
        ),
        navController = rememberNavController()
    )
}