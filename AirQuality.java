public class AirQuality extends EnvironmentalData implements DataOperations {
    private int aqi; // Air Quality Index

    // Reference to the BST instance that will store AirQuality objects
    private static EnvironmentalBST airQualityBST = new EnvironmentalBST();


    // Constructor
    public AirQuality(String locationName, double latitude, double longitude, int measurementTimestamp, int aqi) {
        super(locationName, latitude, longitude, measurementTimestamp);
        this.aqi = aqi;
    }

    // Getter and Setter for AQI
    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    // Implementing DataOperations methods
    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof AirQuality) {
            airQualityBST.insert((AirQuality) data);
            System.out.println("Inserted air quality data for " + data.getLocationName());
        }
    }

    @Override
    public void update(String location, double newValue) {
        AirQuality existingData = (AirQuality) search(location);
        if (existingData != null) {
            existingData.setAqi((int) newValue); // Update the AQI
            System.out.println("Updated air quality data for " + location);
        } else {
            System.out.println("Location not found for update: " + location);
        }
    }

    @Override
    public EnvironmentalData search(String location) {
        // Traverse the BST to find the AirQuality data based on the location
        return airQualityBST.searchByLocation(location);
    }

    @Override
    public void delete(String location) {
        AirQuality existingData = (AirQuality) search(location);
        if (existingData != null) {
            airQualityBST.delete(existingData);
            System.out.println("Deleted air quality data for " + location);
        } else {
            System.out.println("Location not found for deletion: " + location);
        }
    }

    @Override
    public void displayAll() {
        System.out.println("Displaying all air quality data:");
        airQualityBST.inorder();
    }

    @Override
    public void displayInfo() {
        System.out.println("Air Quality Data for " + getLocationName() + ":");
        System.out.println("  Latitude: " + getLatitude());
        System.out.println("  Longitude: " + getLongitude());
        System.out.println("  Timestamp: " + getMeasurementTimestamp());
        System.out.println("  Air Quality Index (AQI): " + aqi);
    }

    @Override
    public double getMeasurementValue() {
        return aqi;
    }

    @Override
    public void updateMeasurement(double newValue) {
        this.aqi = (int) newValue;
        System.out.println("Updated AQI to: " + newValue);
    }

    @Override
    public int compareTo(EnvironmentalData o) {
        if (o instanceof AirQuality) {
            AirQuality other = (AirQuality) o;
            return Integer.compare(this.aqi, other.aqi);
        }
        return 1; // Default comparison when compared with non-AirQuality objects
    }

    @Override
    public String toString() {
        return "City: " + getLocationName() + ", AQI: " + aqi;
    }
}
