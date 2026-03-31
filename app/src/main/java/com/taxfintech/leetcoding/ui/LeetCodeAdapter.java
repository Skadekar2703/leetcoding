package com.taxfintech.leetcoding.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taxfintech.leetcoding.QuestionDetailActivity;
import com.taxfintech.leetcoding.R;
import com.taxfintech.leetcoding.data.LeetCodeEntry;
import com.taxfintech.leetcoding.data.QuestionDifficulty;

import java.util.ArrayList;
import java.util.List;

public class LeetCodeAdapter extends RecyclerView.Adapter<LeetCodeAdapter.QuestionViewHolder> {

    private final Context context;
    private final List<LeetCodeEntry> items = new ArrayList<>();

    public LeetCodeAdapter(@NonNull Context context) {
        this.context = context;
    }

    public void submitList(@NonNull List<LeetCodeEntry> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_card, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        LeetCodeEntry entry = items.get(position);
        QuestionDifficulty difficulty = QuestionDifficulty.fromValue(entry.getDifficulty());
        String metaText = difficulty.getLabel(context) + " • "
                + context.getString(R.string.saved_on, UiUtils.formatDate(entry.getSolvedAt()));

        holder.questionNameText.setText(entry.getQuestionName());
        holder.questionMetaText.setText(metaText);
        holder.difficultyDot.setBackgroundTintList(UiUtils.getDifficultyTint(context, difficulty));
        holder.itemView.setOnClickListener(v -> openDetails(entry));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void openDetails(@NonNull LeetCodeEntry entry) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        intent.putExtra(QuestionDetailActivity.EXTRA_QUESTION_ID, entry.getId());
        context.startActivity(intent);
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private final View difficultyDot;
        private final TextView questionNameText;
        private final TextView questionMetaText;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            difficultyDot = itemView.findViewById(R.id.difficultyDot);
            questionNameText = itemView.findViewById(R.id.questionNameText);
            questionMetaText = itemView.findViewById(R.id.questionMetaText);
        }
    }
}
