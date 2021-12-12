package com.example.wolt.business.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.server.ResponseStatusException

class InvalidOpeningHoursRequestTOException(message: String) : ResponseStatusException(BAD_REQUEST, message)