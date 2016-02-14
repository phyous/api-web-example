package com.phyous.apiweb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phyous.apiweb.model.ImmutableHeadline;

import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableHeadline.class)
public interface Headline {

    Optional<String> reporter();
    Optional<String> source();
    Optional<String> title();
    Optional<String> summary();
    Optional<String> url();
    Optional<List<ImmutableHeadline>> relatedHeadlines();
}
