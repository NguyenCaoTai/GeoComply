package com.sfg.geocomply.domain;

import com.sfg.geocomply.ui.main.model.Link;

import io.reactivex.rxjava3.core.Flowable;

public interface GetPageTitleFromUrl {
    Flowable<Link> execute(String url);
}
