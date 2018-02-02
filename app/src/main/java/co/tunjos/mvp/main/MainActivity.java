package co.tunjos.mvp.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.tunjos.mvp.BuildConfig;
import co.tunjos.mvp.R;
import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.util.IntentUtils;
import co.tunjos.mvp.util.NetworkUtils;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainMVPView, ReposRecyclerViewAdapter.ClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_repos) RecyclerView rvRepos;
    @BindView(R.id.tv_msg) TextView tvMsg;
    @BindView(R.id.pb_loading) ProgressBar pbLoading;

    @Inject MainPresenter mainPresenter;
    @Inject ReposRecyclerViewAdapter reposRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter.attachView(this);
        setupRecyclerView();
        setSupportActionBar(toolbar);

        showDialogIfFirstRun();
    }

    private void showDialogIfFirstRun() {
        boolean firstRun = mainPresenter.getFirstRun();
        if (firstRun) {
            showUsernameDialog();
            mainPresenter.setFirstRun(false);
        } else {
            mainPresenter.getRepos(mainPresenter.getLastUsername());
        }
    }

    private void setupRecyclerView() {
        reposRecyclerViewAdapter.setClickListener(this);

        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(reposRecyclerViewAdapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(rvRepos.getContext()));
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.fabUsername)
    public void onClickFabUsername() {
        showUsernameDialog();
    }

    private void showUsernameDialog() {
        @SuppressLint("InflateParams")
        View usernameView = getLayoutInflater().inflate(R.layout.username_dialog, null);

        final TextView edtxUsername = usernameView.findViewById(R.id.edtx_username);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AndroidMVPDialogStyle)
                .setTitle(R.string.tx_github)
                .setView(usernameView)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.tx_github, null);
        final AlertDialog alertDialog = builder.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String username = edtxUsername.getText().toString();

            if (username.length() == 0) {
                edtxUsername.setError(getString(R.string.err_username_empty));
                return;
            }

            clearReposRecyclerViewAdapter();

            mainPresenter.getRepos(username);
            mainPresenter.setLastUsername(username);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle(username);
            }
            alertDialog.dismiss();
        });

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            edtxUsername.setText("");
            mainPresenter.getRepos(GithubService.USERNAME_GITHUB);
            mainPresenter.setLastUsername(GithubService.USERNAME_GITHUB);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle(GithubService.USERNAME_GITHUB);
            }
            alertDialog.dismiss();
        });
    }

    private void showAboutDialog() {
        @SuppressLint("InflateParams")
        View aboutView = getLayoutInflater().inflate(R.layout.about_dialog, null);

        final TextView tvVersionName = aboutView.findViewById(R.id.tv_version_name);
        final TextView tvVersionNo = aboutView.findViewById(R.id.tv_version_no);
        final TextView tvBuildTime = aboutView.findViewById(R.id.tv_build_time);

        tvVersionName.setText(BuildConfig.VERSION_NAME);
        tvVersionNo.setText(String.format(Locale.getDefault(), "%d", BuildConfig.VERSION_CODE));
        tvBuildTime.setText(BuildConfig.latestBuildTime);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AndroidMVPDialogStyle)
                .setTitle(R.string.action_about)
                .setView(aboutView)
                .setPositiveButton(android.R.string.ok, null);
        final AlertDialog alertDialog = builder.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
    }

    private void clearReposRecyclerViewAdapter() {
        reposRecyclerViewAdapter.clear();
        reposRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            showAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRepos(@NonNull List<Repo> repos) {
        rvRepos.setVisibility(View.VISIBLE);
        reposRecyclerViewAdapter.setRepos(repos);
        reposRecyclerViewAdapter.notifyDataSetChanged();
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
    public void showMessage(@StringRes int resId, boolean error) {
        showMessage(getString(resId), error);
    }

    @Override
    public void showEmpty() {
        showMessage(R.string.tx_empty_repo, false);
    }

    @Override
    public void showMessageView(boolean show) {
        tvMsg.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClickRepo(@NonNull Repo repo) {
        IntentUtils.startRepoActivity(this, repo.owner.login, repo.name);
    }

    @Override
    public void onClickStargazers(@NonNull String stargazersUrl) {
        NetworkUtils.openUrl(this, stargazersUrl);
    }

    @Override
    public void onClickForks(@NonNull String forksUrl) {
        NetworkUtils.openUrl(this, forksUrl);
    }
}
