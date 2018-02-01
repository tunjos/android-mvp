package co.tunjos.mvp.repo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tunjos.mvp.R;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class RepoActivity extends AppCompatActivity implements HasFragmentInjector {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;

    public static final String EXTRA_REPO_OWNER = "extra_repo_owner";
    public static final String EXTRA_REPO_NAME = "extra_repo_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String repoOwner = getIntent().getStringExtra(EXTRA_REPO_OWNER);
        String repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);

        getFragmentManager().beginTransaction()
                .add(R.id.fl_repo, RepoFragment.newInstance(repoOwner, repoName)).commit();
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }
}