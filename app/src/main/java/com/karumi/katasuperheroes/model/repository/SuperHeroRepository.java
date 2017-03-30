package com.karumi.katasuperheroes.model.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.marvelapiclient.CharacterApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.CharactersDto;
import com.karumi.marvelapiclient.model.CharactersQuery;
import com.karumi.marvelapiclient.model.MarvelImage;
import com.karumi.marvelapiclient.model.MarvelResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class SuperHeroRepository {

    public static final String LOGTAG = "SuperHeroRepository";

    private final MarvelApiConfig marvelApiConfig;
    private final CharacterApiClient characterApiClient;
    private final CharactersQuery spider;

    @Inject
    public SuperHeroRepository() {
        marvelApiConfig =
                new MarvelApiConfig.Builder("bf1f5d5f088f59478a3f68324fd1face",
                        "d3fa0b1bad53d48b8bac7b9d4a02a860d24caca0").debug().build();
        characterApiClient = new CharacterApiClient(marvelApiConfig);
        spider = CharactersQuery.Builder.create().withOffset(0).withLimit(10).build();
    }

    public Result<List<SuperHero>, Result.SuperHeroError> getAll() {
        Result<List<SuperHero>, Result.SuperHeroError> result;
        List<SuperHero> superHeroList = new ArrayList<>();
        try {
            MarvelResponse<CharactersDto> all = characterApiClient.getAll(spider);

            superHeroList = map(all);
            result = Result.success(superHeroList);
            Log.d(LOGTAG, "Characters downloaded = " + all.getResponse().getCharacters().size());
        } catch (MarvelApiException e) {
            if (e.getHttpCode() == 403) {
                result = Result.error(Result.SuperHeroError.NetworkError);
            } else {
                result = Result.error(Result.SuperHeroError.InvalidSession);
            }
            Log.e(LOGTAG, "Exception catch trying to download some characters from the Marvel API", e);
        }
        return result;
    }


    public SuperHero getById(String superHeroId) {
        SuperHero superHero = null;
        try {
            MarvelResponse<CharacterDto> marvelResponse = characterApiClient.getCharacter(superHeroId);
            superHero = mapSuperHero(marvelResponse.getResponse());
        } catch (MarvelApiException e) {
            Log.e(LOGTAG, "Exception catch trying to download the character from the Marvel API", e);
        }
        return superHero;
    }

    private List<SuperHero> map(MarvelResponse<CharactersDto> all) {
        List<SuperHero> superHeroList = new ArrayList<>();
        for (CharacterDto characterDto : all.getResponse().getCharacters()) {
            SuperHero superHero = mapSuperHero(characterDto);
            superHeroList.add(superHero);
        }
        return superHeroList;
    }

    @NonNull
    private SuperHero mapSuperHero(CharacterDto characterDto) {
        return new SuperHero(characterDto.getId(), characterDto.getName(), characterDto.getThumbnail().getImageUrl(MarvelImage.Size.DETAIL), false, characterDto.getDescription());
    }
}
