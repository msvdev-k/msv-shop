package org.msvdev.ee.shop.api.exception;

public record AppError (int statusCode, String message) {}
