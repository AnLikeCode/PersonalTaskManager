package com.example.personaltaskmanager.features.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.personaltaskmanager.R
import com.example.personaltaskmanager.features.calendar_events.screens.CalendarFragment
import com.example.personaltaskmanager.features.task_manager.screens.TaskListFragment

class NavigationActivity : AppCompatActivity() {

    // Không cần biến này nữa vì trạng thái sẽ được quản lý bằng Compose State
    // private var currentItem: NavItem = NavItem.TASKS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val composeNav = findViewById<ComposeView>(R.id.bottom_nav_compose)

        // ⭐ SỬA LỖI CHÍNH: Khai báo trạng thái Compose State bên trong setContent
        composeNav.setContent {
            // Sử dụng remember và mutableStateOf để tạo trạng thái có thể quan sát
            // Khởi tạo trạng thái ban đầu là TASKS
            var currentItemState by remember { mutableStateOf(NavItem.TASKS) }

            BottomNavBar(
                current = currentItemState, // Truyền biến trạng thái Compose
                onSelect = { selected ->
                    currentItemState = selected // CẬP NHẬT: Thay đổi State, kích hoạt re-compose
                    navigateTo(selected)
                }
            )
        }

        // Chuyển màn hình Fragment ban đầu (Task)
        navigateTo(NavItem.TASKS)
    }

    private fun navigateTo(item: NavItem) {
        val fragment: Fragment = when (item) {
            NavItem.TASKS -> TaskListFragment()
            NavItem.CALENDAR -> CalendarFragment()
            NavItem.SETTINGS -> SettingsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_container, fragment)
            .commit()
    }
}