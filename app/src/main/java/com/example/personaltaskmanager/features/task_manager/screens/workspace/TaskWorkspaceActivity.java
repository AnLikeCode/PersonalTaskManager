package com.example.personaltaskmanager.features.task_manager.screens.workspace;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
// Nếu bật drag & drop → mở comment
// import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlock;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlockParser;
import com.example.personaltaskmanager.features.task_manager.viewmodel.TaskViewModel;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskWorkspaceActivity extends AppCompatActivity {

    // ----------------------------------------------------------------------
    // VIEW LAYER
    // ----------------------------------------------------------------------
    private RecyclerView rvWorkspace;
    private Chip btnAddParagraph, btnAddTodo, btnAddBullet, btnAddDivider;
    private ImageButton btnBack;

    // ----------------------------------------------------------------------
    // DATA LAYER
    // ----------------------------------------------------------------------
    private final List<NotionBlock> blocks = new ArrayList<>();
    private NotionBlockAdapter adapter;
    private TaskViewModel vm;
    private Task task;


    // ----------------------------------------------------------------------
    // LIFECYCLE
    // ----------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_task_manager_workspace);

        vm = new ViewModelProvider(this).get(TaskViewModel.class);

        int taskId = getIntent().getIntExtra("task_id", -1);
        task = vm.getTaskById(taskId);

        initViews();
        initRecycler();
        loadBlocks();
        setupActions();
    }


    // ----------------------------------------------------------------------
    // INIT UI
    // ----------------------------------------------------------------------
    private void initViews() {

        rvWorkspace = findViewById(R.id.rv_workspace);

        btnAddParagraph = findViewById(R.id.btn_add_paragraph);
        btnAddTodo = findViewById(R.id.btn_add_todo);
        btnAddBullet = findViewById(R.id.btn_add_bullet);
        btnAddDivider = findViewById(R.id.btn_add_divider);
        btnBack = findViewById(R.id.btn_back_ws);
    }


    // ----------------------------------------------------------------------
    // INIT RECYCLER + (OPTIONAL) DRAG & DROP
    // ----------------------------------------------------------------------
    private void initRecycler() {

        rvWorkspace.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotionBlockAdapter(blocks);
        rvWorkspace.setAdapter(adapter);

        // Nếu muốn bật drag & drop, gỡ comment:
        /*
        ItemTouchHelper helper = new ItemTouchHelper(new BlockDragCallback(adapter));
        helper.attachToRecyclerView(rvWorkspace);
        */
    }


    // ----------------------------------------------------------------------
    // LOAD EXISTING BLOCKS FROM TASK JSON
    // ----------------------------------------------------------------------
    private void loadBlocks() {
        blocks.clear();
        blocks.addAll(NotionBlockParser.fromJson(task.getNotesJson()));
        adapter.notifyDataSetChanged();
    }


    // ----------------------------------------------------------------------
    // BUTTON CLICK LISTENERS
    // ----------------------------------------------------------------------
    private void setupActions() {

        btnBack.setOnClickListener(v -> {
            save();
            finish();
        });

        btnAddParagraph.setOnClickListener(v ->
                addBlock(NotionBlock.Type.PARAGRAPH));

        btnAddTodo.setOnClickListener(v ->
                addBlock(NotionBlock.Type.TODO));

        btnAddBullet.setOnClickListener(v ->
                addBlock(NotionBlock.Type.BULLET));

        btnAddDivider.setOnClickListener(v ->
                addBlock(NotionBlock.Type.DIVIDER));
    }


    // ----------------------------------------------------------------------
    // ADD BLOCK FUNCTION
    // ----------------------------------------------------------------------
    private void addBlock(NotionBlock.Type type) {

        blocks.add(new NotionBlock(
                UUID.randomUUID().toString(), // block ID
                type,                          // block type
                "",                            // placeholder text
                false                          // default checked (todo)
        ));

        adapter.notifyItemInserted(blocks.size() - 1);
        rvWorkspace.scrollToPosition(blocks.size() - 1);
    }


    // ----------------------------------------------------------------------
    // SAVE ALL BLOCKS BACK TO TASK
    // ----------------------------------------------------------------------
    private void save() {

        String json = NotionBlockParser.toJson(blocks);
        task.setNotesJson(json);

        vm.updateTask(
                task,
                task.getTitle(),
                task.getDescription(),
                task.getDeadline()
        );
    }
}
