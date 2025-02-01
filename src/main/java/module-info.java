module org.example.soulofdarkness {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;
    requires javafx.graphics;

    opens org.example.soulofdarkness to javafx.fxml;

    exports org.example.soulofdarkness;
}