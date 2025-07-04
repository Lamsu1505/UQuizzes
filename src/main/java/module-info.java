open module UQuizzes {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    // Si realmente necesitas el ojdbc:
    requires ojdbc8;
    requires java.desktop;

    exports org.example.Controllers.Ventanas.Docente to javafx.fxml;
    exports org.example.Controllers.Paneles.Docente to javafx.fxml;
    exports org.example.Controllers.Paneles.Docente.TiposPregunta to  javafx.fxml;
    exports org.example.Controllers.Ventanas.Estudiante to javafx.fxml;
    exports org.example.Controllers.Paneles.Estudiante to javafx.fxml;
    exports org.example.Model ;

    exports org.example.Main;
    exports org.example.Controllers.Paneles.Estudiante.FormatosRespuestas to javafx.fxml;
}
