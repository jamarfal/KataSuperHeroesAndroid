package com.karumi.katasuperheroes.model;

import com.karumi.katasuperheroes.model.repository.Result;
import com.karumi.katasuperheroes.model.repository.SuperHeroRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class KataSuperHeroes {


    private final SuperHeroRepository repository;

    @Inject
    public KataSuperHeroes(SuperHeroRepository repository) {
        this.repository = repository;
    }

    public Result<List<SuperHero>, Result.SuperHeroError> getAll() {
        List<SuperHero> superHeroListCopy = new ArrayList<>();
        Result<List<SuperHero>, Result.SuperHeroError> result = repository.getAll();
        List<SuperHero> superHeroList = result.getValue();
        if (superHeroList != null) {
            SuperHero superHero;
            int supeHeroListSize = superHeroList.size();
            for (int index = 0; index < supeHeroListSize; index++) {
                superHero = superHeroList.get(index);
                if (index % 3 == 0) {
                    SuperHeroFactory factory = new SuperHeroFactory(superHero);
                    superHero = factory.withIsAvenger(true);
                }
                superHeroListCopy.add(superHero);
            }
        }
        Result<List<SuperHero>, Result.SuperHeroError> resultCopy = new Result<>(superHeroListCopy, result.getError());
        return resultCopy;
    }
}
