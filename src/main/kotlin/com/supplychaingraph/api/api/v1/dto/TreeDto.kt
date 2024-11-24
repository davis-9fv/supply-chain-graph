package com.supplychaingraph.api.api.v1.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Represents a Channel.")
@JsonPropertyOrder("id", "children")
data class TreeDto(
    @get:Schema(description = "Represents the an edge parent", example = "1")
    val id: Int?,
    @get:Schema(description = "Represents the children of an edge", example = "2")
    @JsonProperty("child_id")
    val children: MutableList<TreeDto> = mutableListOf(),
)
