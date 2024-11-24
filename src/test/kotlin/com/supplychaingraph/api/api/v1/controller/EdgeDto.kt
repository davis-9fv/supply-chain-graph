package com.supplychaingraph.api.api.v1.controller

import com.fasterxml.jackson.core.JsonParseException
import com.supplychaingraph.api.api.v1.dto.EdgeDto
import com.supplychaingraph.api.config.JacksonConfig
import com.supplychaingraph.api.toDao
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EdgeDto {
    private val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = factory.validator
    private val mapper = JacksonConfig().objectMapper()

    @Test
    fun `It should map to dao`() {
        val dto = EdgeDto(1, 2)
        val dao = dto.toDao()
        assertEquals(dto.id, dao.id?.id)
        assertEquals(dto.childId, dao.id?.childId)
    }

    @Test
    fun `It should serialize and deserialize`() {
        val dto = EdgeDto(1, 2)
        val body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto)
        val actual = mapper.readValue(body, EdgeDto::class.java)
        assertEquals(actual, dto)
    }

    @Test
    fun `It should fail on unparsable json`() {
        assertThrows<JsonParseException> {
            mapper.readValue("other", EdgeDto::class.java)
        }
    }

    @Test
    fun `It should validate a correct dto`() {
        val dto = EdgeDto(1, 2)
        val violations = validator.validate(dto)
        assertEquals(0, violations.size)
    }

    @Test
    fun `It should validate fields`() {
        val dto = EdgeDto(-1, -1)

        val violations = validator.validate(dto)
        val actual = violations.map { it.message }
        val expected =
            listOf(
                "id should be positive",
                "child_id should be positive",
            )
        assertListEqualsInAnyOrder(actual, expected)
    }
}

fun <T> assertListEqualsInAnyOrder(
    x: List<T>,
    y: List<T>,
) = Assertions.assertTrue(x.size == y.size && x.containsAll(y) && y.containsAll(x))
