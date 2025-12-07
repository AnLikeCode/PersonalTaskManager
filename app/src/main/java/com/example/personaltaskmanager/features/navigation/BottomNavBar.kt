package com.example.personaltaskmanager.features.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// ⭐ Màu xanh lơ lạnh theo yêu cầu (ví dụ)
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.launch
import com.example.personaltaskmanager.R


enum class NavItem(val label: String, val icon: Int) {
    TASKS("Tasks", R.drawable.feature_task_manager_ic_calendar), // Dùng icon task mới
    CALENDAR("Calendar", R.drawable.feature_calendar_ic_calendar), // Dùng icon calendar mới
    SETTINGS("Settings", android.R.drawable.ic_menu_preferences)
}


@Composable
fun BottomNavBar(
    current: NavItem,
    onSelect: (NavItem) -> Unit
) {
    val coroutine = rememberCoroutineScope()
    val density = LocalDensity.current

    var itemWidth by remember { mutableStateOf(0.dp) }

    // Vị trí offset animation của highlight bubble
    val targetIndex = NavItem.values().indexOf(current)
    val offsetX by animateDpAsState(
        targetValue = itemWidth * targetIndex,
        label = "OffsetXAnimation"
    )

    // ⭐ THIẾT LẬP MÀU XANH LƠ CHỦ ĐẠO (Chỉ định màu nếu chưa thiết lập trong Theme)
    // Nếu bạn đã đặt MaterialTheme.colorScheme.primary là màu xanh lơ, có thể bỏ qua dòng này.
    // Nếu chưa, hãy dùng màu cố định cho bong bóng và icon được chọn.
    val BlueGreen = Color(0xFF5AE4D9)
    val LightBlueGreenBackground = BlueGreen.copy(alpha = 0.25f) // Nền bong bóng nhạt hơn

    Surface(
        // Giữ nguyên shadow và elevation
        tonalElevation = 3.dp,
        shadowElevation = 6.dp,
        color = Color.White,
        // ⭐ Bo tròn lớn hơn (ví dụ 32.dp) để hiện đại hơn
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // ⭐ Chiều cao thanh điều hướng được giữ nguyên hoặc điều chỉnh nhẹ
                .height(92.dp)
        ) {

            // ════════════════════════════════════════
            //  ⭐ BONG BÓNG HIGHLIGHT ANIMATION (RỰC RỠ)
            // ════════════════════════════════════════
            Box(
                modifier = Modifier
                    .offset(x = offsetX, y = 8.dp) // ⭐ ĐIỀU CHỈNH: y = 8.dp để dịch xuống một chút
                    .width(itemWidth)
                    .height(56.dp) // ⭐ THIẾT KẾ: Bong bóng hơi dẹt và lớn hơn
                    .padding(horizontal = 8.dp) // ⭐ THIẾT KẾ: Thêm padding ngang
                    .background(
                        color = LightBlueGreenBackground, // Dùng màu xanh lơ nhạt
                        shape = RoundedCornerShape(28.dp) // ⭐ THIẾT KẾ: Bo tròn hoàn toàn
                    )
            )


            // ════════════════════════════════════════
            //  ⭐ ROW CHỨA CÁC TAB
            // ════════════════════════════════════════
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp) // Giảm padding ngang để bong bóng rộng hơn
            ) {

                NavItem.values().forEachIndexed { idx, item ->

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .onGloballyPositioned {
                                // Tính toán itemWidth một lần trên item đầu tiên
                                if (idx == 0 && itemWidth == 0.dp) {
                                    itemWidth = with(density) { it.size.width.toDp() }
                                }
                            }
                            .clickable {
                                coroutine.launch { onSelect(item) }
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        val selected = (item == current)

                        val scale by animateFloatAsState(
                            targetValue = if (selected) 1.1f else 1f, // ⭐ THIẾT KẾ: Scale nhẹ hơn
                            label = "IconScale"
                        )

                        // ⭐ THIẾT KẾ: Dùng màu xanh lơ chủ đạo cho icon và text khi chọn
                        val tint by animateColorAsState(
                            targetValue = if (selected) BlueGreen else Color.Gray.copy(alpha = 0.7f),
                            label = "IconTint"
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 12.dp) // Dịch nội dung lên
                        ) {

                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                tint = tint,
                                modifier = Modifier
                                    .size(28.dp)
                                    .scale(scale)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = item.label,
                                fontSize = 12.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = tint
                            )
                        }
                    }
                }
            }
        }
    }
}