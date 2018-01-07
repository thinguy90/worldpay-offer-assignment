package com.worldpay.assignment.merchantoffer.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * Exception to indicate status 404 - not found
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
}
