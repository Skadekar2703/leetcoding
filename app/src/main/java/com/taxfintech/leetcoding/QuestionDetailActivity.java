package com.taxfintech.leetcoding;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.taxfintech.leetcoding.data.LeetCodeDatabase;
import com.taxfintech.leetcoding.data.LeetCodeEntry;
import com.taxfintech.leetcoding.data.QuestionDifficulty;
import com.taxfintech.leetcoding.ui.ThemePreferenceManager;
import com.taxfintech.leetcoding.ui.UiUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuestionDetailActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_ID = "extra_question_id";

    private ExecutorService databaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemePreferenceManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_detail);

        View rootView = findViewById(R.id.detailRoot);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.detailToolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        long questionId = getIntent().getLongExtra(EXTRA_QUESTION_ID, -1L);
        if (questionId == -1L) {
            finish();
            return;
        }

        databaseExecutor = Executors.newSingleThreadExecutor();
        loadQuestion(questionId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseExecutor != null) {
            databaseExecutor.shutdown();
        }
    }

    private void loadQuestion(long questionId) {
        databaseExecutor.execute(() -> {
            LeetCodeEntry entry = LeetCodeDatabase.getInstance(getApplicationContext())
                    .leetCodeDao()
                    .getQuestionById(questionId);

            if (entry == null) {
                runOnUiThread(this::finish);
                return;
            }

            runOnUiThread(() -> bindQuestion(entry));
        });
    }

    private void bindQuestion(@NonNull LeetCodeEntry entry) {
        TextView nameText = findViewById(R.id.detailQuestionNameText);
        View difficultyDot = findViewById(R.id.detailDifficultyDot);
        TextView difficultyText = findViewById(R.id.detailDifficultyText);
        TextView savedOnText = findViewById(R.id.detailSavedOnText);
        TextView descriptionText = findViewById(R.id.detailDescriptionText);
        TextView codeText = findViewById(R.id.detailCodeText);

        QuestionDifficulty difficulty = QuestionDifficulty.fromValue(entry.getDifficulty());

        nameText.setText(entry.getQuestionName());
        difficultyText.setText(difficulty.getLabel(this));
        savedOnText.setText(getString(R.string.saved_on, UiUtils.formatDate(entry.getSolvedAt())));
        descriptionText.setText(entry.getDescription());
        codeText.setText(entry.getCode());
        difficultyDot.setBackgroundTintList(UiUtils.getDifficultyTint(this, difficulty));
    }
}
