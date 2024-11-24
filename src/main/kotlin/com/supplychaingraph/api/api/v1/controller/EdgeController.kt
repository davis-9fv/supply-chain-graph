package com.supplychaingraph.api.api.v1.controller

import com.supplychaingraph.api.api.v1.dto.EdgeDto
import com.supplychaingraph.api.service.EdgeService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/v1/edges/")
class EdgeController(
    @Autowired val edgeService: EdgeService,
) {
    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun add(
        @RequestBody
        edgeDto: EdgeDto,
    ): Unit = edgeService.save(edgeDto)

    @DeleteMapping("{parentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable parentId: Int,
    ): Unit = edgeService.delete(parentId)
}
