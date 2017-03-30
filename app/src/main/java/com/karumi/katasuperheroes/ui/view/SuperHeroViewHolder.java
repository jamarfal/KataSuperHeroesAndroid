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

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.karumi.katasuperheroes.R;
import com.karumi.katasuperheroes.model.SuperHero;
import com.squareup.picasso.Picasso;

public class SuperHeroViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_super_hero_photo)
    ImageView photoImageView;
    @Bind(R.id.tv_super_hero_name)
    TextView nameTextView;
    @Bind(R.id.iv_avengers_badge)
    View avengersBadgeView;

    public SuperHeroViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(SuperHero superHero) {
        hookListeners(superHero);
        renderSuperHeroPhoto(superHero.getPhoto());
        renderSuperHeroName(superHero.getName());
        renderAvengersBadge(superHero.isAvenger());
    }

    private void hookListeners(final SuperHero superHero) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SuperHeroDetailActivity.class);
                intent.putExtra("superhero_id", superHero.getId());
                getContext().startActivity(intent);
            }
        });
    }

    private void renderSuperHeroPhoto(String photo) {
        Picasso.with(getContext()).load(photo).fit().centerCrop().into(photoImageView);
    }

    private void renderSuperHeroName(String name) {
        nameTextView.setText(name);
    }

    private void renderAvengersBadge(boolean isAvenger) {
        avengersBadgeView.setVisibility(isAvenger ? View.VISIBLE : View.GONE);
    }

    private Context getContext() {
        return itemView.getContext();
    }
}
