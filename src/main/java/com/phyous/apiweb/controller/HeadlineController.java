package com.phyous.apiweb.controller;

import com.phyous.apiweb.model.ImmutableHeadline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.pippo.controller.Controller;
import ro.pippo.core.Param;
import ro.pippo.core.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class HeadlineController extends Controller {

    private static final String baseUrl = "http://www.techmeme.com/";

    public void getHeadlines(@Param("date") String headlineDate) {
        try {
            Document doc = Jsoup.connect(generateWebUrl(headlineDate)).get();
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

                    return ImmutableHeadline.builder()
                        .reporter(reporter)
                        .source(source)
                        .title(title)
                        .summary(summary)
                        .url(url)
                        .build();
                })
                .collect(Collectors.toList());

            getResponse().json(headlines);
        } catch (Exception e) {
            getResponse().internalError();
        }
    }
    
    private String generateWebUrl(String dateParam) {
        if (StringUtils.isNullOrEmpty(dateParam)) {
            return baseUrl;
        }
        
        try {
            final String formattedDate = 
                LocalDate
                    .parse(dateParam)
                    .format(DateTimeFormatter.ofPattern("yyMMdd"));
            return String.format("%s/%s/h1230", baseUrl, formattedDate);
        } catch (DateTimeParseException e) {
            return baseUrl;
        }
    }
}
