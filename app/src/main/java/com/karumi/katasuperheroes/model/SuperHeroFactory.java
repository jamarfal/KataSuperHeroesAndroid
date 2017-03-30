package com.karumi.katasuperheroes.model;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class SuperHeroFactory {

    private final SuperHero superHero;

    public SuperHeroFactory(SuperHero superHero) {
        this.superHero = superHero;
    }

    public SuperHero withIsAvenger(boolean isAvenger) {
        return new SuperHero(superHero.getId(), superHero.getName(), superHero.getPhoto(), isAvenger, superHero.getDescription());
    }

}
