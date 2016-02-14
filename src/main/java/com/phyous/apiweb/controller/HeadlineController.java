package com.phyous.apiweb.controller;

import com.phyous.apiweb.model.ImmutableHeadline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.pippo.controller.Controller;
import ro.pippo.core.Param;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeadlineController extends Controller {

    private static final String sourceUrl = "http://www.techmeme.com/";

    public void getHeadlines(@Param("date") String headlineDate) {
        try {
            Document doc = Jsoup.connect(sourceUrl).get();
            Elements topLinks = doc.select("#topcol1 .clus");

            List<ImmutableHeadline> headlines = topLinks.stream()
                .map(x -> {
                    Element titleBar = x.select(".shrtbl cite").first();
                    Element mainStory = x.select(".itc1 .itc2 .item .ii").first();

                    String reporter = titleBar.ownText().split(" / ")[0];
                    String source = titleBar.select("a").first().ownText();
                    String title = mainStory.select("strong a").first().ownText();
                    String summary = mainStory.ownText();
                    String url = mainStory.select("strong a").attr("href");
                    Optional<List<ImmutableHeadline>> relatedHeadlines = relatedHeadlines(x);

                    return ImmutableHeadline.builder()
                        .reporter(reporter)
                        .source(source)
                        .title(title)
                        .summary(summary)
                        .url(url)
                        .relatedHeadlines(relatedHeadlines)
                        .build();
                })
                .collect(Collectors.toList());

            getResponse().json(headlines);
        } catch (IOException e) {
            getResponse().internalError();
        }
    }

    private Optional<List<ImmutableHeadline>> relatedHeadlines(Element topLink) {
        Elements relatedHeadlineElements = topLink.select(".itc1 .itc2 .item div.dbpt > span.bls").first().children();
        return Optional.of(
            relatedHeadlineElements.stream()
                .map(relatedHl -> {
                    Element hlElement = relatedHl.select("a").first();
                    return ImmutableHeadline.builder()
                        .url(hlElement.attr("href"))
                        .source(hlElement.ownText())
                        .build();
                })
                .collect(Collectors.toList())
        );
    }
}
