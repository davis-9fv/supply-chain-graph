package com.supplychaingraph.api.api.v1.controller

import com.fasterxml.jackson.core.JsonParseException
import com.supplychaingraph.api.api.v1.dto.TreeDto
import com.supplychaingraph.api.config.JacksonConfig
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TreeDto {
    private val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = factory.validator
    private val mapper = JacksonConfig().objectMapper()

    @Test
    fun `It should serialize and deserialize`() {
        val dto = TreeDto(1, mutableListOf(TreeDto(2), TreeDto(3)))
        val body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto)
        val actual = mapper.readValue(body, TreeDto::class.java)
        assertEquals(actual, dto)
    }

    @Test
    fun `It should fail on unparsable json`() {
        assertThrows<JsonParseException> {
            mapper.readValue("other", TreeDto::class.java)
        }
    }

    @Test
    fun `It should validate a correct dto`() {
        val dto = TreeDto(1, mutableListOf(TreeDto(2), TreeDto(3)))
        val violations = validator.validate(dto)
        assertEquals(0, violations.size)
    }
}
