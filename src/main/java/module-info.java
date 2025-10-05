module es.unaizugaza {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires java.management;
    requires javafx.graphics;

    opens es.unaizugaza to javafx.fxml;
    exports es.unaizugaza;
    exports es.unaizugaza.modelos;
    opens es.unaizugaza.modelos to javafx.base, javafx.fxml;
    opens es.unaizugaza.controladores to javafx.fxml;
}
