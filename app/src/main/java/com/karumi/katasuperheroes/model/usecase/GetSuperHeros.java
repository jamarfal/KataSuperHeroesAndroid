package com.karumi.katasuperheroes.model.usecase;

import android.os.Handler;
import android.os.Looper;

import com.karumi.katasuperheroes.model.KataSuperHeroes;
import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.UseCase;
import com.karumi.katasuperheroes.model.repository.Result;
import com.karumi.katasuperheroes.model.repository.SuperHeroRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class GetSuperHeros extends UseCase<Void, List<SuperHero>> {


    private final KataSuperHeroes kataSuperHeroes;
    private UseCaseCallback<List<SuperHero>> useCaseCallback;


    @Inject
    public GetSuperHeros(KataSuperHeroes kataSuperHeroes) {
        this.kataSuperHeroes = kataSuperHeroes;
    }

    @Override
    public void run() {
        final Result<List<SuperHero>, Result.SuperHeroError> result;
        result = kataSuperHeroes.getAll();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (result.getValue() != null) {
                    useCaseCallback.onSucces(result.getValue());
                } else {
                    useCaseCallback.onError(result.getError());
                }
            }
        });
    }

    @Override
    public void execute(Void aVoid, UseCaseCallback<List<SuperHero>> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
        new Thread(this).start();
    }
}
