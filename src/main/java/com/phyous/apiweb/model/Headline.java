package com.phyous.apiweb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phyous.apiweb.model.ImmutableHeadline;

import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableHeadline.class)
public interface Headline {

    String reporter();
    String source();
    String title();
    String summary();
    String url();
    Optional<List<ImmutableHeadline>> relatedHeadlines();
}
