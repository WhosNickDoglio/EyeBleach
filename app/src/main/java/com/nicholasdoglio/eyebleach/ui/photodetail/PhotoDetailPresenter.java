/*
    Aww Gallery
    Copyright (C) 2017  Nicholas Doglio

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nicholasdoglio.eyebleach.ui.photodetail;

import com.nicholasdoglio.eyebleach.data.source.RedditPostRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Nicholas Doglio
 */
public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {
    private static final int IMAGES_LOAD_VIEWPAGER = 10;
    public PhotoDetailContract.View view;
    private RedditPostRepository repository;
    private CompositeDisposable compositeDisposable;

    @Inject
    PhotoDetailPresenter(RedditPostRepository redditPostRepository) {
        repository = redditPostRepository;
        compositeDisposable = new CompositeDisposable();
    }

    public void firstLoad() {
        compositeDisposable.add(repository.getFirstLoadPosts(IMAGES_LOAD_VIEWPAGER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(childData -> view.updateList(childData)));
    }

    @Override
    public void clear() {
        compositeDisposable.clear();
    }

    @Override
    public void takeView(PhotoDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
