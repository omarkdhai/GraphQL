package com.example.GraphQL.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(@JsonProperty("nom_prenom") String nomPrenom,
                     @JsonProperty("telephone") String telephone,
                     @JsonProperty("email") String email,
                     @JsonProperty("adresse") Address adresse) {}

