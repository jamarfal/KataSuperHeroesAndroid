package com.karumi.katasuperheroes.ui.presenter;

import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.UseCase;
import com.karumi.katasuperheroes.model.repository.Result;
import com.karumi.katasuperheroes.model.usecase.GetSuperHeros;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class MainPresenter extends Presenter<MainPresenter.MainView> {

    private final GetSuperHeros getSuperHeros;

    @Inject
    public MainPresenter(GetSuperHeros getSuperHeros) {
        this.getSuperHeros = getSuperHeros;
    }

    @Override
    public void initialize() {
        super.initialize(); // Buena práctica por convención. Siguiendo el Principio de sustitución de Liskov, para no romper el padre
        final MainView mainView = getView();
        if (mainView != null) {
            mainView.showLoading();
            getSuperHeros.execute(null, new UseCase.UseCaseCallback<List<SuperHero>>() {
                @Override
                public void onSucces(List<SuperHero> superHeros) {
                    if (superHeros.isEmpty()) {
                        mainView.showEmptyCase();
                    } else {
                        mainView.showSuperHeroes(superHeros);
                    }
                    mainView.hideLoading();
                }

                @Override
                public void onError(Result.SuperHeroError error) {
                    if(error == Result.SuperHeroError.NetworkError){
                        mainView.showError("Ha ocurrido un error!");
                    }
                }
            });
        }

    }

    public interface MainView extends Presenter.View {

        void showSuperHeroes(List<SuperHero> superHeroes);

        void openSuperHeroScreen(SuperHero superHero);

        void showEmptyCase();

        void hideEmptyCase();

        void showError(String message);
    }
}
