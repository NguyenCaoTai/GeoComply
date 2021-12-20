package com.sfg.geocomply.domain;

import com.sfg.geocomply.data.NetService;
import com.sfg.geocomply.ui.main.model.Link;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetPageTitleFromUrlImpl implements GetPageTitleFromUrl {

    final NetService netService;

    public GetPageTitleFromUrlImpl(NetService netService) {
        this.netService = netService;
    }

    @Override
    public Flowable<Link> execute(String url) {
        return Flowable.fromCallable(() -> netService.getPageTitle(url))
                .onErrorReturnItem("")
                .map(title -> new Link(url, title))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single());
    }
}
