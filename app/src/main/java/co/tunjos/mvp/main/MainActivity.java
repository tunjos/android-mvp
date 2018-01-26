package co.tunjos.mvp.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.tunjos.mvp.R;
import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.util.NetworkUtils;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainMVPView, ReposRecyclerViewAdapter.ClickListener {
    @Inject MainPresenter mainPresenter;
    @Inject ReposRecyclerViewAdapter reposRecyclerViewAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_repos) RecyclerView rvRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter.attachView(this);
        setupRecyclerView();
        setSupportActionBar(toolbar);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AndroidMVPDialogStyle);
        builder.setTitle(R.string.tx_github);
        builder.setView(usernameView);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(R.string.tx_github, null);
        final AlertDialog alertDialog = builder.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtxUsername.getText().toString();

                if (username.length() == 0) {
                    edtxUsername.setError(getString(R.string.err_username_empty));
                    return;
                }

                mainPresenter.getRepos(username);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setSubtitle(username);
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtxUsername.setText("");
                mainPresenter.getRepos(GithubService.USERNAME_GITHUB);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setSubtitle(GithubService.USERNAME_GITHUB);
                }
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRepos(List<Repo> repos) {
        reposRecyclerViewAdapter.setRepos(repos);
        reposRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showError(int resId) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMessageView(boolean show) {

    }

    @Override
    public void onClickRepo(@NonNull Repo repo) {
        Toast.makeText(this, "Repo", Toast.LENGTH_SHORT).show();
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
