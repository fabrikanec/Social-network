package relation.resourceServer;

public class ResourceServerController implements ResourceServerControllerMBean {
    private final ResourceServerI resourceServer;

    public ResourceServerController(ResourceServerI resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public int getAge() {
        return resourceServer.getAge();
    }

    @Override
    public String getName() {
        return resourceServer.getName();
    }
}
