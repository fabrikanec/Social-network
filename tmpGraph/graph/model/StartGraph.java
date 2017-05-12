package graph.model;

/*
*
 * Call our {@link HikeManagerApplication} invoking the rest API via Arquillian.
 * <p>
 * Tests the creation of a new {@link Person} and the {@link Hike} he's organizing.
 *
 * @author Gunnar Morling
 *
*/
public class StartGraph {

   /* private static final String WEBAPP_SRC = "src/main/webapp/WEB-INF";



    @Test
    public void createAndGetPerson() throws Exception {
        // Create a person
        Person person = new Person();
        person.setFirstName("Saundra");
        person.setLastName("lastName");


        String location = response.getHeaderString( "Location");
        assertNotNull( location );
        response.close();

        // Get the person
        Invocation getPerson = invocationBuilder( webTarget, "/" + getId( location ) ).buildGet();
        response = getPerson.invoke();
        assertEquals( HttpStatus.SC_OK, response.getStatus() );

        JSONAssert.assertEquals(
                "{ 'firstName' : 'Saundra', 'lastName' : 'Smith' }",
                response.readEntity( String.class ),
                false
        );
        response.close();
    }

    @Test
    public void createPersonAndHike(@ArquillianResteasyResource( "hike-manager" ) ResteasyWebTarget webTarget) throws Exception {
        // Create a person
        Invocation createPerson = invocationBuilder( webTarget, "/persons" ).buildPost(
                jsonEntity( "{ 'firstName' : 'Bob', 'lastName' : 'Stamper' } " )
        );

        Response response = createPerson.invoke();
        assertEquals( HttpStatus.SC_CREATED, response.getStatus() );
        response.close();

        String personLocation = response.getHeaderString( "Location");
        assertNotNull( personLocation );

        // Create a hike
        Invocation createHike = invocationBuilder( webTarget, "/hikes" ).buildPost(
                jsonEntity(
                        "{" +
                                "'organizer' : '" + personLocation + "', " +
                                "'description' : 'My first hike', " +
                                "'date' : '2012-04-23', " +
                                "'difficulty' : 7.3," +
                                "'sections':[" +
                                "{ 'start' : 'Pendeen', 'end' : 'St. Yves'}" +
                                "]" +
                                "}"
                )
        );

        response = createHike.invoke();
        assertEquals( HttpStatus.SC_CREATED, response.getStatus() );
        response.close();

        String hikeLocation = response.getHeaderString( "Location");
        assertNotNull( hikeLocation );

        // Get the person
        Invocation getPerson = invocationBuilder( webTarget, "/persons/" + getId( personLocation ) ).buildGet();
        response = getPerson.invoke();
        assertEquals( HttpStatus.SC_OK, response.getStatus() );

        JSONAssert.assertEquals(
                "{ 'firstName' : 'Bob', 'lastName' : 'Stamper', 'organizedHikes' : ['" + hikeLocation + "'] }",
                response.readEntity( String.class ),
                false
        );

        response.close();

        // Get the hike
        Invocation getHike = invocationBuilder( webTarget, "/hikes/" + getId( hikeLocation ) ).buildGet();
        response = getHike.invoke();
        assertEquals( HttpStatus.SC_OK, response.getStatus() );

        JSONAssert.assertEquals(
                "{" +
                        "'organizer' : '" + personLocation + "', " +
                        "'description' : 'My first hike', " +
                        "'date' : '2012-04-23', " +
                        "'difficulty' : 7.3," +
                        "'sections':[" +
                        "{ 'start' : 'Pendeen', 'end' : 'St. Yves'}" +
                        "]" +
                        "}",
                response.readEntity( String.class ),
                false
        );

        response.close();
    }

    private String getId(String personLocation) {
        return personLocation.substring( personLocation.lastIndexOf( '/' ) + 1 );
    }

    private Entity<String> jsonEntity(String entity) {
        return Entity.entity( entity.replaceAll( "'", "\"" ) , MediaType.APPLICATION_JSON_TYPE );
    }

    private Invocation.Builder invocationBuilder(ResteasyWebTarget webTarget) {
        return invocationBuilder( webTarget, null );
    }

    private Invocation.Builder invocationBuilder(ResteasyWebTarget webTarget, String path) {
        Invocation.Builder builder = path != null ? webTarget.path( path ).request() : webTarget.request();

        builder.acceptEncoding( "UTF-8" );
        builder.accept( MediaType.APPLICATION_JSON );

        return builder;
    }*/
}