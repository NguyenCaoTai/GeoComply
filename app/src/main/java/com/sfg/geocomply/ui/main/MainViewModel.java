package com.sfg.geocomply.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sfg.geocomply.GetPageTitleFromUrl;
import com.sfg.geocomply.Utils;
import com.sfg.geocomply.ui.main.model.Link;
import com.sfg.geocomply.ui.main.model.LinkGroup;
import com.sfg.geocomply.ui.main.model.Mention;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class MainViewModel extends ViewModel {
    private final GetPageTitleFromUrl getPageTitle;

    public final MutableLiveData<String> comment = new MutableLiveData<>("");

    public final LiveData<String> mentionResponse =
            Transformations.switchMap(
                    comment,
                    input -> new MutableLiveData<>(
                            Utils.formatJson(
                                    getMentionResponse(
                                            Utils.parse(input, Constant.REGEX_MENTION)
                                    )
                            )
                    )
            );

    public final LiveData<String> linkResponse =
            Transformations.switchMap(
                    comment,
                    input -> LiveDataReactiveStreams.fromPublisher(
                            getLinkResponse(Utils.parse(input, Constant.REGEX_URL))
                                    .map(Utils::formatJson)
                                    .toFlowable()
                    )
            );

    public MainViewModel(GetPageTitleFromUrl getPageTitle) {
        super();
        this.getPageTitle = getPageTitle;
    }

    private String getMentionResponse(List<String> mentions) {
        if (mentions.isEmpty()) {
            return "";
        } else {
            List<String> removedSymbolMentions = new ArrayList<>();
            for (String mention : mentions) {
                removedSymbolMentions.add(mention.replace(Constant.SYMBOL_MENTION, ""));
            }
            Mention mention = new Mention(removedSymbolMentions);
            Gson gson = new GsonBuilder().create();

            return gson.toJson(mention);
        }
    }


    private Single<String> getLinkResponse(List<String> urls) {
        if (urls.isEmpty()) {
            return Single.just("");
        } else {
            return Flowable.fromArray(urls.toArray(new String[0]))
                    .flatMap((Function<String, Publisher<Link>>) getPageTitle::execute)
                    .toList()
                    .map(LinkGroup::new)
                    .map(new GsonBuilder().create()::toJson);
        }
    }


    static class MainViewModelFactory
            implements ViewModelProvider.Factory {

        private final GetPageTitleFromUrl getPageTitleFromUrl;

        public MainViewModelFactory(GetPageTitleFromUrl getPageTitleFromUrl) {
            this.getPageTitleFromUrl = getPageTitleFromUrl;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
            if (aClass.isAssignableFrom(MainViewModel.class)) {
                return (T) new MainViewModel(getPageTitleFromUrl);
            }

            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}