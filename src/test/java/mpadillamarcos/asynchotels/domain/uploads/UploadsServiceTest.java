package mpadillamarcos.asynchotels.domain.uploads;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UploadsServiceTest {

    private final FileStorage fileStorage = mock(FileStorage.class);
    private final UploadsService service = new UploadsService(fileStorage);

    @Nested
    class UploadCsv {

        @Test
        void throws_exception_when_no_file_is_specified() {
            assertThatThrownBy(() -> service.uploadCsv(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("file must not be null");
        }

        @Test
        void throws_exception_when_csv_is_empty() {
            var fileContent = new byte[0];
            var fileName = "my_file.csv";
            var emptyFile = new UploadedFile(fileContent, fileName);

            assertThatThrownBy(() -> service.uploadCsv(emptyFile))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("file must not be empty");
        }

        @Test
        void throws_when_file_is_not_csv() {
            var fileContent = new byte[10];
            var fileName = "my_file.sav";
            var savFile = new UploadedFile(fileContent, fileName);

            assertThatThrownBy(() -> service.uploadCsv(savFile))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("file must have csv extension");
        }

        @Test
        void stores_uploaded_file() {
            // given
            var fileContent = "Hello!".getBytes(UTF_8);
            var fileName = "my_file.csv";
            var csvFile = new UploadedFile(fileContent, fileName);
            var path = randomUUID().toString();

            when(fileStorage.store(any(), any())).thenReturn(path);

            // when
            var metadata = service.uploadCsv(csvFile);

            // then
            assertThat(metadata.path()).isEqualTo(path);
            assertThat(metadata.name()).isEqualTo(fileName);

            verify(fileStorage).store(fileContent, "uploads/" + metadata.id() + "/" + fileName);
        }
    }
}