package com.karumi.katasuperheroes.model;

import com.karumi.katasuperheroes.model.repository.Result;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public abstract class UseCase<Input, Output> implements Runnable {

    public abstract void execute(Input input, UseCaseCallback<Output> useCaseCallback);

    public interface UseCaseCallback<Output> {
        void onSucces(Output output);

        void onError(Result.SuperHeroError error);
    }
}
