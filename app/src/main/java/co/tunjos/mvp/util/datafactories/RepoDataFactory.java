package co.tunjos.mvp.util.datafactories;

import java.util.ArrayList;
import java.util.List;

import co.tunjos.mvp.api.model.Repo;

import static co.tunjos.mvp.util.datafactories.TestDataFactory.getRandomInt;
import static co.tunjos.mvp.util.datafactories.TestDataFactory.getRandomUUID;

/**
 * Factory class that creates instances of data models for Repo.
 */
public class RepoDataFactory {

    private RepoDataFactory() {
    }

    static RepoDataFactory newInstance() {
        return new RepoDataFactory();
    }

    public String createUsername() {
        return getRandomUUID();
    }

    private Repo createRepo() {
        Repo repo = new Repo();

        repo.id = getRandomInt();
        repo.name = getRandomUUID();
        repo.full_name = getRandomUUID();
        repo.url = getRandomUUID();
        repo.forksUrl = getRandomUUID();
        repo.stargazersUrl = getRandomUUID();
        repo.description = getRandomUUID();
        repo.stargazersCount = getRandomInt();
        repo.language = getRandomUUID();
        repo.forks = getRandomInt();

        return repo;
    }

    public List<Repo> createRepoList(int reposCount) {
        List<Repo> repos = new ArrayList<>();
        for (int i = 0; i < reposCount; i++) {
            repos.add(createRepo());
        }
        return repos;
    }
}
