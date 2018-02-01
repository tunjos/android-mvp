package co.tunjos.mvp.repo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tunjos.mvp.R;
import co.tunjos.mvp.api.model.Repo;
import dagger.android.AndroidInjection;

public class RepoFragment extends Fragment implements RepoMVPView {

    @BindView(R.id.ll_owner_repo) LinearLayout llOwnerRepo;
    @BindView(R.id.ll_stats_container) LinearLayout llStatsContainer;

    @BindView(R.id.tv_owner) TextView tvOwner;
    @BindView(R.id.tv_repo) TextView tvRepo;
    @BindView(R.id.tv_subscribers_count) TextView tvSubscribersCount;
    @BindView(R.id.tv_stargazers_count) TextView tvStargazersCount;
    @BindView(R.id.tv_forks) TextView tvForks;
    @BindView(R.id.tv_description) TextView tvDescription;

    @BindView(R.id.tv_msg) TextView tvMsg;
    @BindView(R.id.pb_loading) ProgressBar pbLoading;

    @Inject RepoPresenter repoPresenter;

    private static final String ARG_REPO_OWNER = "arg_repo_owner";
    private static final String ARG_REPO_NAME = "arg_repo_name";

    private String owner;
    private String name;

    public RepoFragment() {
    }

    public static RepoFragment newInstance(@NonNull String owner, @NonNull String name) {
        RepoFragment repoFragment = new RepoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_REPO_OWNER, owner);
        args.putString(ARG_REPO_NAME, name);

        repoFragment.setArguments(args);
        return repoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            owner = args.getString(ARG_REPO_OWNER);
            name = args.getString(ARG_REPO_NAME);
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repoPresenter.attachView(this);
        repoPresenter.getRepo(owner, name);
    }

    @Override
    public void onDestroyView() {
        repoPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showRepo(@NonNull Repo repo) {
        llOwnerRepo.setVisibility(View.VISIBLE);
        llStatsContainer.setVisibility(View.VISIBLE);
        tvDescription.setVisibility(View.VISIBLE);

        tvOwner.setText(repo.owner.login);
        tvRepo.setText(repo.name);
        tvSubscribersCount.setText(String.format(Locale.getDefault(), "%s", repo.subscribersCount));
        tvStargazersCount.setText(String.format(Locale.getDefault(), "%s", repo.stargazersCount));
        tvForks.setText(String.format(Locale.getDefault(), "%s", repo.forks));
        tvDescription.setText(repo.description);
    }

    @Override
    public void showProgress(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showMessage(@NonNull String message, boolean error) {
        if (error) {
            tvMsg.setTextColor(Color.RED);
        }
        tvMsg.setText(message);
    }

    @Override
    public void showMessage(int resId, boolean error) {
        showMessage(getString(resId), error);
    }

    @Override
    public void showEmpty() {
    }

    @Override
    public void showMessageView(boolean show) {
        tvMsg.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
