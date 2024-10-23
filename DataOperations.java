public interface DataOperations {
    void insert(EnvironmentalData data);
    void update(String location, double newValue);
    EnvironmentalData search(String location);
    void delete(String location);
    void displayAll();
}
