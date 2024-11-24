package com.supplychaingraph.api.service

import com.supplychaingraph.api.api.v1.dto.TreeDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TreeService(
    @Autowired val edgeService: EdgeService,
) {
    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    fun find(id: Int): TreeDto {
        edgeService.find(id)
        val treeDto = buildTree(id, TreeDto(id, mutableListOf()))
        LOGGER.info { "Tree $treeDto created" }
        return treeDto
    }

    fun buildTree(
        id: Int,
        treeDto: TreeDto,
    ): TreeDto {
        val edges =
            runCatching { edgeService.find(id) }
                .getOrElse { emptyList() }

        if (edges.isEmpty()) return treeDto

        val childrenTrees = edges.map { edge -> TreeDto(edge.childId) }
        childrenTrees.forEach { tree -> buildTree(tree.id!!, tree) }

        treeDto.children.addAll(childrenTrees)
        return treeDto
    }
}
