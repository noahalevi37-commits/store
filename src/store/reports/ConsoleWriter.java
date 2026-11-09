package store.reports;

/**
 * ConsoleWriter writes report content to the console.
 *
 * This class is a concrete implementation of ReportWriter.
 * It prints the report directly to the screen.
 *
 * author Noa Halevi
 *
 */
public class ConsoleWriter implements ReportWriter {

    /**
     * Writes the report content to the console.
     *
     * @param content the report content
     */
    @Override
    public void write(String content) {
        System.out.println(content);
    }
}
