module UQuizzes {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    // Si realmente necesitas el ojdbc:
    requires ojdbc8;
    requires java.desktop;

    opens org.example.Main to javafx.fxml;
    exports org.example.Controllers.Ventanas.Docente to javafx.fxml;
    exports org.example.Controllers.Paneles.Docente to javafx.fxml;
    opens org.example.Controllers.Paneles.Docente to javafx.fxml;
    opens org.example.Controllers.Ventanas.Docente to javafx.fxml;
    opens org.example.Controllers.Ventanas to javafx.fxml;

    exports org.example.Main;
}
