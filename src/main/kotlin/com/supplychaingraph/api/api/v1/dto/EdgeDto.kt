package com.supplychaingraph.api.api.v1.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive

@Schema(description = "Represents a Channel.")
data class EdgeDto(
    @get:Schema(description = "Represents the an edge parent", example = "1")
    @get:Positive(message = "id should be positive")
    val id: Int,
    @get:Schema(description = "Represents the child of an edge", example = "2")
    @get:Positive(message = "child_id should be positive")
    @JsonProperty("child_id")
    val childId: Int? = null,
)
