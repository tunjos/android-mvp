package co.tunjos.mvp.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tunjos.mvp.R;
import co.tunjos.mvp.api.model.Repo;


public class ReposRecyclerViewAdapter extends RecyclerView.Adapter<ReposRecyclerViewAdapter.RepoViewHolder> {

    private List<Repo> repos;
    private ClickListener clickListener;

    @Inject
    public ReposRecyclerViewAdapter() {
        repos = Collections.emptyList();
    }

    void setRepos(List<Repo> repos) {
        this.repos = repos;
    }

    void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_list_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, int position) {
        holder.repo = repos.get(position);
        holder.tvName.setText(holder.repo.name);
        holder.tvDescription.setText(holder.repo.description);
        holder.tvLanguage.setText(holder.repo.language);
        holder.tvStarGazersCount.setText(String.format("%d", holder.repo.stargazersCount));
        holder.tvForks.setText(String.format("%d", holder.repo.forks));

        holder.llProjectContainer.setOnClickListener(v -> {
            if (null != clickListener) {
                clickListener.onClickRepo(holder.repo);
            }
        });
        holder.tvStarGazersCount.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClickStargazers(holder.repo.stargazersUrl);
            }
        });
        holder.tvForks.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClickForks(holder.repo.forksUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_project_container) View llProjectContainer;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_description) TextView tvDescription;
        @BindView(R.id.tv_language) TextView tvLanguage;
        @BindView(R.id.tv_stargazers_count) TextView tvStarGazersCount;
        @BindView(R.id.tv_forks) TextView tvForks;

        Repo repo;

        RepoViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickListener {
        void onClickRepo(@NonNull Repo repo);

        void onClickStargazers(@NonNull String stargazersUrl);

        void onClickForks(@NonNull String forksUrl);
    }
}