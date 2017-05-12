package relation.resourceServer;

import relation.resources.TestResource;

public interface ResourceServerI {
    int getAge();
    String getName();
    void setResource(TestResource resource);
    TestResource getResource();
}
