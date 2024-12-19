package com.example.GraphQL.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(@JsonProperty("rue") String rue,
                      @JsonProperty("code_postal_ville") String codePostalVille,
                      @JsonProperty("complet") String complet) {}
