module es.unaizugaza {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.slf4j;

    opens es.unaizugaza to javafx.fxml;
    exports es.unaizugaza;
    exports es.unaizugaza.modelos;
    opens es.unaizugaza.modelos to javafx.fxml;
    opens es.unaizugaza.controladores to javafx.fxml;
}
