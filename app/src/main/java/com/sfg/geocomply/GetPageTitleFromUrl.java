package com.sfg.geocomply;

import com.sfg.geocomply.ui.main.model.Link;

import io.reactivex.rxjava3.core.Flowable;

public interface GetPageTitleFromUrl {
    Flowable<Link> execute(String url);
}
