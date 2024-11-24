package com.supplychaingraph.api.api.v1.controller

import com.supplychaingraph.api.api.v1.dto.TreeDto
import com.supplychaingraph.api.service.TreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/v1/trees/")
class TreeController(
    @Autowired val treeService: TreeService,
) {
    @GetMapping("{id}")
    fun one(
        @PathVariable id: Int,
    ): TreeDto = treeService.find(id)
}
