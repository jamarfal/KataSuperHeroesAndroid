package com.karumi.katasuperheroes.ui.presenter;

import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.UseCase;
import com.karumi.katasuperheroes.model.repository.Result;
import com.karumi.katasuperheroes.model.usecase.GetSuperHeroById;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class SuperHeroDetailPresenter extends Presenter<SuperHeroDetailPresenter.SuperHeroDetailView> {

    private final GetSuperHeroById getSuperHeroById;

    public void setSuperHeroId(String superHeroId) {
        this.superHeroId = superHeroId;
    }

    private String superHeroId;

    @Inject
    public SuperHeroDetailPresenter(GetSuperHeroById getSuperHeroById) {
        this.getSuperHeroById = getSuperHeroById;
    }

    @Override
    public void initialize() {
        super.initialize();
        final SuperHeroDetailView superHeroDetailView = getView();
        if (superHeroDetailView != null) {
            superHeroDetailView.showLoading();
            getSuperHeroById.execute(superHeroId, new UseCase.UseCaseCallback<SuperHero>() {
                @Override
                public void onSucces(SuperHero superHero) {
                    superHeroDetailView.showSuperHero(superHero);
                    superHeroDetailView.hideLoading();
                }

                @Override
                public void onError(Result.SuperHeroError error) {

                }
            });
        }
    }

    public interface SuperHeroDetailView extends Presenter.View {
        void showSuperHero(SuperHero superHero);
    }
}
