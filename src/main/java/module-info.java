module ro.mta.se.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    //requires org.mockito;

    opens ro.mta.se.lab to javafx.fxml;
    exports ro.mta.se.lab;
    opens ro.mta.se.lab.model;
    opens ro.mta.se.lab.controller;
}