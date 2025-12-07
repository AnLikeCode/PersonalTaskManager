package com.example.personaltaskmanager.features.task_manager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.personaltaskmanager.R

class TaskManagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.feature_task_manager_main, container, false)

        // Load TaskListFragment bên trong nếu muốn
        childFragmentManager.beginTransaction()
            .replace(R.id.main, TaskListFragment())
            .commit()

        return root
    }
}
