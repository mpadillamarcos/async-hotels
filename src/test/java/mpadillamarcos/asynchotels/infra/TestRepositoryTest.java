package mpadillamarcos.asynchotels.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestRepository.class)
public class TestRepositoryTest extends DbTestBase {

    @Autowired
    private TestRepository repository;

    @Test
    void uses_actual_database() {
        final var entity = new TestEntity(randomUUID(), now().truncatedTo(MILLIS));

        repository.insert(entity);
        final var stored = repository.find(entity.id());

        assertThat(stored).hasValue(entity);
    }
}
