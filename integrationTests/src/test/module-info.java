module integration.tests {
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires cvm.core;

    opens integration to org.junit.platform.commons;
}
