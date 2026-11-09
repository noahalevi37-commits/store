package store.reports;

/**
 * ReportWriter defines how a report is written.
 *
 * This interface represents the implementation side of the Bridge pattern.
 * Different implementations can output the report in different formats.
 *
 * author Noa Halevi
 *
 */
public interface ReportWriter {

    /**
     * Writes the given report content.
     *
     * @param content the report content
     */
    void write(String content);
}
