package co.tunjos.mvp.main;


import android.support.annotation.NonNull;

import java.util.List;

import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.base.MVPView;

interface MainMVPView extends MVPView {
    void showRepos(@NonNull List<Repo> repos);
}
