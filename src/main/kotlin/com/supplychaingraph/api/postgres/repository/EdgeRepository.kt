package com.supplychaingraph.api.postgres.repository

import com.supplychaingraph.api.postgres.dao.EdgeDao
import com.supplychaingraph.api.postgres.dao.EdgeDaoKey
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EdgeRepository : CrudRepository<EdgeDao, EdgeDaoKey> {
    @Modifying
    @Query("DELETE FROM EdgeDao e WHERE e.id.id = :id")
    fun deleteByParentId(id: Int)

    @Query("SELECT e FROM EdgeDao e WHERE e.id.id = :id")
    fun findByParentId(id: Int): List<EdgeDao>
}
