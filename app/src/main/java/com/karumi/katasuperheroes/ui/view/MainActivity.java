/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.katasuperheroes.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;

import com.karumi.katasuperheroes.R;
import com.karumi.katasuperheroes.SuperHeroesApplication;
import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.UseCase;
import com.karumi.katasuperheroes.model.repository.SuperHeroRepository;
import com.karumi.katasuperheroes.model.usecase.GetSuperHeros;
import com.karumi.katasuperheroes.ui.presenter.MainPresenter;
import com.karumi.marvelapiclient.CharacterApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.CharactersDto;
import com.karumi.marvelapiclient.model.CharactersQuery;
import com.karumi.marvelapiclient.model.MarvelResponse;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainPresenter.MainView {

    public static final String LOGTAG = "MainActivity";
    private SuperHeroesAdapter adapter;
    @Inject
    MainPresenter mainPresenter;

    @Bind(R.id.tv_empty_case)
    View emptyCaseView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
        initializeAdapter();
        initializeRecyclerView();
        mainPresenter.setView(this);
        mainPresenter.initialize();

    }

  /*  private void initializePresenter() {
        SuperHeroRepository superHeroRepository = new SuperHeroRepository();
        GetSuperHeros getSuperHeros = new GetSuperHeros(superHeroRepository);
        mainPresenter = new MainPresenter(getSuperHeros);
        mainPresenter.setView(this);
        mainPresenter.initialize();
    }*/


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void showSuperHeroes(List<SuperHero> superHeroes) {
        adapter.addAll(superHeroes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openSuperHeroScreen(SuperHero superHero) {
        SuperHeroDetailActivity.open(this, superHero.getName());
    }

    @Override
    public void showEmptyCase() {
        emptyCaseView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyCase() {
        emptyCaseView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initializeDagger() {
        SuperHeroesApplication app = (SuperHeroesApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializeAdapter() {
        adapter = new SuperHeroesAdapter();
    }

    private void initializeRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        ;
    }

}
