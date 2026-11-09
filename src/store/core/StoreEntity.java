package store.core;
/**
 * something that can be shown to the user.
 */
public interface StoreEntity {
    /** short display name. */
    String getDisplayName();
    /** full details for display. */
    String getDisplayDetails();
}
