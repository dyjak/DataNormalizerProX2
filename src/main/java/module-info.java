module datanormalizer.datanormalizerprox2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;


    opens datanormalizer.datanormalizerprox2 to javafx.fxml;
    exports datanormalizer.datanormalizerprox2;
    exports datanormalizer.datanormalizerprox2.Controllers;
    opens datanormalizer.datanormalizerprox2.Controllers to javafx.fxml;
    exports datanormalizer.datanormalizerprox2.General;
    opens datanormalizer.datanormalizerprox2.General to javafx.fxml;
}