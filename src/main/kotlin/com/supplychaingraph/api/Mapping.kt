package com.supplychaingraph.api

import com.supplychaingraph.api.api.v1.dto.EdgeDto
import com.supplychaingraph.api.postgres.dao.EdgeDao
import com.supplychaingraph.api.postgres.dao.EdgeDaoKey

fun EdgeDto.toDao() =
    EdgeDao(
        id =
            EdgeDaoKey(
                id = id,
                childId = childId,
            ),
    )

fun EdgeDao.toDto() =
    EdgeDto(
        id = id?.id!!,
        childId = id.childId,
    )
