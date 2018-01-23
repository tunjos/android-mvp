package co.tunjos.mvp.main;


import java.util.List;

import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.base.MVPView;

public interface MainMVPView extends MVPView {

    void showProgress(boolean show);

    void showRepos(List<Repo> repos);

    void showError();
}
