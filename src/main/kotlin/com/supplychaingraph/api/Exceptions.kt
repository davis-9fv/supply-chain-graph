package com.supplychaingraph.api

import com.supplychaingraph.api.api.v1.dto.EdgeDto

class NoEdgesFoundException(
    id: Int,
) : Exception("No edges found for parent id: $id")

class InvalidEdgeException(
    edgeDto: EdgeDto,
) : Exception("The id ${edgeDto.id} cannot be the same as the child")

abstract class ValidationException(
    message: String,
) : RuntimeException(message)
