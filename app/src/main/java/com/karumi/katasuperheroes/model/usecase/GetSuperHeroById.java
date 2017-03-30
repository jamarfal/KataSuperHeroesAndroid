package com.karumi.katasuperheroes.model.usecase;

import android.os.Handler;
import android.os.Looper;

import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.UseCase;
import com.karumi.katasuperheroes.model.repository.SuperHeroRepository;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class GetSuperHeroById extends UseCase<String, SuperHero> {

    private final SuperHeroRepository superHeroRepository;
    private UseCaseCallback<SuperHero> useCaseCallback;
    private String superHeroId;

    @Inject
    public GetSuperHeroById(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @Override
    public void run() {
        final SuperHero superHero = superHeroRepository.getById(superHeroId);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSucces(superHero);
            }
        });
    }

    @Override
    public void execute(String s, UseCaseCallback<SuperHero> useCaseCallback) {
        this.superHeroId = s;
        this.useCaseCallback = useCaseCallback;
        new Thread(this).start();
    }
}
