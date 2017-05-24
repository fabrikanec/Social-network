package main.java.relation.resourceServer;

import main.java.relation.resource.TestResource;

public interface ResourceServerI {
    int getAge();
    String getName();
    void setResource(TestResource resource);
    TestResource getResource();
}
