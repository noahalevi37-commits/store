package store.reports;

/**
 * Abstract Report class.
 *
 * This class represents the abstraction part of the Bridge pattern.
 * It holds a reference to a ReportWriter, which defines how the report
 * output is performed.
 *
 * Concrete report types will extend this class.
 *
 * author Noa Halevi
 *
 */
public abstract class Report {

    protected ReportWriter writer;

    /**
     * Constructs a report with the given writer.
     *
     * @param writer the writer used to output the report
     */
    public Report(ReportWriter writer) {
        this.writer = writer;
    }

    /**
     * Generates the report content.
     */
    public abstract void generate();
}
