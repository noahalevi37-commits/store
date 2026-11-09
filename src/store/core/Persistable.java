package store.core;
/**
 * interface for saving objects to a file.
 * real saving will be done in future exercises.
 *  my name is noa halevi
 */
public interface Persistable {
    /** save the object somewhere (not implemented yet). */
    void saveToFile(String path);
}
