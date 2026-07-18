package mpadillamarcos.asynchotels.infra;

import java.time.Instant;
import java.util.UUID;

public record TestEntity(UUID id, Instant createdDate) {}
