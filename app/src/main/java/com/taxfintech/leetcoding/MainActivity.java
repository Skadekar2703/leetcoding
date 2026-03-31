package com.taxfintech.leetcoding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.taxfintech.leetcoding.data.LeetCodeDatabase;
import com.taxfintech.leetcoding.data.LeetCodeEntry;
import com.taxfintech.leetcoding.data.QuestionDifficulty;
import com.taxfintech.leetcoding.ui.LeetCodeAdapter;
import com.taxfintech.leetcoding.ui.ThemePreferenceManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private LeetCodeDatabase database;
    private ExecutorService databaseExecutor;
    private LeetCodeAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyStateLayout;
    private TextView questionCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemePreferenceManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = LeetCodeDatabase.getInstance(getApplicationContext());
        databaseExecutor = Executors.newSingleThreadExecutor();

        recyclerView = findViewById(R.id.questionsRecyclerView);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);
        questionCountText = findViewById(R.id.questionCountText);
        FloatingActionButton addQuestionFab = findViewById(R.id.addQuestionFab);
        MaterialSwitch themeSwitch = findViewById(R.id.themeSwitch);

        adapter = new LeetCodeAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        themeSwitch.setChecked(ThemePreferenceManager.isDarkModeEnabled(this));
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                ThemePreferenceManager.setDarkModeEnabled(this, isChecked));
        addQuestionFab.setOnClickListener(v -> showAddQuestionDialog());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadQuestions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseExecutor != null) {
            databaseExecutor.shutdown();
        }
    }

    private void loadQuestions() {
        databaseExecutor.execute(() -> {
            List<LeetCodeEntry> questions = database.leetCodeDao().getAllQuestions();
            runOnUiThread(() -> {
                adapter.submitList(questions);
                boolean hasItems = !questions.isEmpty();
                emptyStateLayout.setVisibility(hasItems ? View.GONE : View.VISIBLE);
                recyclerView.setVisibility(hasItems ? View.VISIBLE : View.GONE);
                questionCountText.setText(getString(R.string.question_count, questions.size()));
            });
        });
    }

    private void showAddQuestionDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_question, null, false);
        TextInputEditText questionNameInput = dialogView.findViewById(R.id.questionNameInput);
        TextInputEditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);
        TextInputEditText codeInput = dialogView.findViewById(R.id.codeInput);
        ChipGroup difficultyChipGroup = dialogView.findViewById(R.id.difficultyChipGroup);

        difficultyChipGroup.check(R.id.easyChip);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.add_question)
                .setView(dialogView)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save_question, null)
                .create();

        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(v -> saveQuestionFromDialog(
                        dialog,
                        questionNameInput,
                        descriptionInput,
                        codeInput,
                        difficultyChipGroup
                )));

        dialog.show();
    }

    private void saveQuestionFromDialog(
            @NonNull AlertDialog dialog,
            @NonNull TextInputEditText questionNameInput,
            @NonNull TextInputEditText descriptionInput,
            @NonNull TextInputEditText codeInput,
            @NonNull ChipGroup difficultyChipGroup
    ) {
        String name = getText(questionNameInput);
        String description = getText(descriptionInput);
        String code = getText(codeInput);

        if (name.isEmpty() || description.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        QuestionDifficulty difficulty = mapDifficulty(difficultyChipGroup.getCheckedChipId());
        LeetCodeEntry entry = new LeetCodeEntry(
                name,
                description,
                code,
                difficulty.name(),
                System.currentTimeMillis()
        );

        databaseExecutor.execute(() -> {
            database.leetCodeDao().insertQuestion(entry);
            runOnUiThread(() -> {
                dialog.dismiss();
                loadQuestions();
            });
        });
    }

    @NonNull
    private QuestionDifficulty mapDifficulty(int checkedChipId) {
        if (checkedChipId == R.id.mediumChip) {
            return QuestionDifficulty.MEDIUM;
        }
        if (checkedChipId == R.id.hardChip) {
            return QuestionDifficulty.HARD;
        }
        return QuestionDifficulty.EASY;
    }

    @NonNull
    private String getText(@NonNull TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }
}
