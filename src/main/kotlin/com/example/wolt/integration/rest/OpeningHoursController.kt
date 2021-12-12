package com.example.wolt.integration.rest

import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.service.OpeningHoursWriter
import com.example.wolt.service.mapper.OpeningHoursRequestTOMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OpeningHoursController(
    private val openingHoursRequestTOMapper: OpeningHoursRequestTOMapper,
    private val openingHoursWriter: OpeningHoursWriter
) {
    @PostMapping("/opening-hours")
    fun printOpeningHours(@RequestBody openingHoursTO: OpeningHoursRequestTO): ResponseEntity<String> {
        val openingHours = openingHoursRequestTOMapper.mapToOpeningHours(openingHoursTO)
        val response = openingHoursWriter.write(openingHours)
        return ResponseEntity.ok(response)
    }
}