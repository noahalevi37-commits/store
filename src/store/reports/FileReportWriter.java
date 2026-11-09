package store.reports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileReportWriter writes report content to a CSV file.
 *
 * Implements ReportWriter (Bridge – Implementor).
 *
 * author Noa Halevi
 *
 */
public class FileReportWriter implements ReportWriter {

    @Override
    public void write(String content) {
        try {
            Files.writeString(Path.of("report.csv"), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
