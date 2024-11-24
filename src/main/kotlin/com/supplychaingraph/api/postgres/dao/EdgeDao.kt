package com.supplychaingraph.api.postgres.dao

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "edge")
data class EdgeDao(
    @EmbeddedId
    val id: EdgeDaoKey? = null,
) {
    constructor() : this(null)
}

@Embeddable
data class EdgeDaoKey(
    val id: Int?,
    val childId: Int? = null,
) {
    constructor() : this(null, null)
}
