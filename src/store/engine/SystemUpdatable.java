package store.engine;

/**
 * Interface for background updates. [cite: 118, 119]
 */
public interface SystemUpdatable {
    /** update logic for the system */
    void update();
}