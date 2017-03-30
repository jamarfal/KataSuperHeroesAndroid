package com.karumi.katasuperheroes.model.repository;

import com.karumi.katasuperheroes.model.SuperHero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

class FakeSuperHeroRepository {

    public List<SuperHero> getAll() {
        List<SuperHero> superHeroList = new ArrayList<>();
        return superHeroList;
    }
}
