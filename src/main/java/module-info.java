module com.example.samplejavafxproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.samplejavafxproject to javafx.fxml;
    exports com.example.samplejavafxproject;
}