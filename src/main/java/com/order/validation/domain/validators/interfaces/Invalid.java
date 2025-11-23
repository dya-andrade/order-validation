package com.order.validation.domain.validators.interfaces;

import java.util.List;

public record Invalid(List<String> errors) implements ValidationResult {}
