package co.tunjos.mvp.repo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import co.tunjos.mvp.R;
import co.tunjos.mvp.api.model.Repo;
import dagger.android.AndroidInjection;

public class RepoFragment extends Fragment implements RepoMVPView {

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
        ButterKnife.bind(view);
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
        Toast.makeText(getActivity(), "TEST", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(@NonNull String message, boolean error) {

    }

    @Override
    public void showMessage(int resId, boolean error) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMessageView(boolean show) {

    }
}
