package main.java.relation.resourceServer;

import main.java.relation.resource.TestResource;

public class ResourceServer implements ResourceServerI {
    private TestResource resource;

    @Override
    public int getAge() {
        return resource.getAge();
    }

    @Override
    public String getName() { return resource.getName(); }

    @Override
    public void setResource(TestResource resource) { this.resource = resource; }

    @Override
    public TestResource getResource() { return resource; }
}
