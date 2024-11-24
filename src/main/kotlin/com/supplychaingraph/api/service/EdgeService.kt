package com.supplychaingraph.api.service

import com.supplychaingraph.api.InvalidEdgeException
import com.supplychaingraph.api.NoEdgesFoundException
import com.supplychaingraph.api.api.v1.dto.EdgeDto
import com.supplychaingraph.api.postgres.repository.EdgeRepository
import com.supplychaingraph.api.toDao
import com.supplychaingraph.api.toDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EdgeService(
    @Autowired val edgeRepository: EdgeRepository,
) {
    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    fun find(id: Int): List<EdgeDto> =
        edgeRepository
            .findByParentId(id)
            .map { it.toDto() }
            .takeIf { it.isNotEmpty() }
            ?: throw NoEdgesFoundException(id)

    fun save(edgeDto: EdgeDto) {
        if (edgeDto.id == edgeDto.childId) throw InvalidEdgeException(edgeDto)
        edgeRepository.save(edgeDto.toDao())
        LOGGER.info { "Edge $edgeDto added" }
    }

    @Transactional
    fun delete(parentId: Int) {
        findIds(parentId, mutableSetOf(parentId))
            .onEach { edgeRepository.deleteByParentId(it) }
            .also { LOGGER.info { "Deleted nodes ${it.joinToString(", ")}" } }
    }

    fun findIds(
        parentId: Int,
        ids: MutableSet<Int>,
    ): MutableSet<Int> {
        val nodes = edgeRepository.findByParentId(parentId)
        if (nodes.isEmpty()) return ids

        val childNodes =
            nodes.mapNotNull {
                it.id?.childId
            }
        childNodes.forEach {
            findIds(it, ids)
        }
        ids.addAll(childNodes)

        return ids
    }
}
