package co.tunjos.mvp.repo;


import android.support.annotation.NonNull;

import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.base.MVPView;

interface RepoMVPView extends MVPView {
    void showRepo(@NonNull Repo repo);
}
