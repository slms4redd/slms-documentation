=================
Developer's guide
=================

The administration interface is being rebuilt into a RESTful API and a rich client web interface.

RESTful interface
==================

The administration interface is using Jersey [#]_ to expose the resources of the API. Spring also
provides some facilities to build restful interface [#]_ but Jersey has been chosen instead because
it is the JAX-RS (JSR 311) Reference Implementation.

The usage of Jersey consists of a web.xml file containing the Jersey servlet and the 
*com.sun.jersey.spi.container.servlet.ServletContainer* parameter to 
specify the package that contains the resources being exposed by the RESTful API. See [#]_ for
more information.

At the same level as the *resource* package there are two packages: *model* and *json* that will
be explained later. 

The *resource* package contains two types of classes:

* Classes ending in "Resource": The classes responding to the GET, POST, PUT and DELETE methods. They
  contain all the logic and validations the API should do. For more information, see [#]_. These handlers
  interact with the persistence back-end (Geostore) through a set of interfaces defined in the *model* package.
  
* Exceptions. HTML error codes, like 404, will be returned by throwing a subclass of
  javax.ws.rs.WebApplicationException. For more information, see [#]_.

The *json* package contains beans that will be transformed to JSON automatically by Jackson. These classes have
an empty constructor for that purpose (Jackson requirement) and are received and returned in the Resource handlers.

The *model* package contains interfaces with the methods required to query or update the persistence
backend. Every model interface contains a *getJSON* method that returns an object from the *json* package and
allows to return a representation of any element in the model. For deployment there is an implementation 
based on Geostore but, at the same time, the use of the interface makes it possible to test the API without
need to actually connect to an external component.

One non-explored option is to use the model interfaces as the classes to be serialized to JSON. More information
can be found here [#]_.

Mock model-JSON mapping
------------------------

The mapping between the model objects, received and returned by the resource methods, and JSON is done
automatically by Jackson [#]_, as configured in the Jersey servlet in the *web.xml* file.

Testing
-----------

Testing is done by inheriting the *com.sun.jersey.test.framework.JerseyTest* class, that creates all the setup
in order to be able to call the resource methods.

It was considered to use jetty server to test the services actually on a container but it was discarded due to
the collisions of Jetty servlet/jsp APIs with standard APIs, that may result in kicking out the whole jetty
dependency in the future [#]_.

The model interfaces are mocked by Mockito [#]_ to simulate the Geostore behavior for the individual test cases.

Therefore, tests for the Geostore model implementation must be created and integration tests with both components
working at the same time would be great.

.. [#] http://jersey.java.net/
.. [#] http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/new-in-3.0.html#new-feature-rest-support
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e188
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e88
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e435
.. [#] http://stackoverflow.com/questions/6345325/jackson-json-immutable-classes-and-interfaces
.. [#] http://jackson.codehaus.org/
.. [#] https://github.com/nfms4redd/nfms-admin-interface/issues/12
.. [#] http://code.google.com/p/mockito/
