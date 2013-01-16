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

This package contains three types of classes:

* Classes ending in "Resource": The classes responding to the GET, POST, PUT and DELETE methods. They
  contain all the logic and validations at this moment because the model is a temporary one used only to
  mock the behavior. For more information, see [#]_
* Exceptions. HTML error codes, like 404, will be returned by throwing a subclass of
  javax.ws.rs.WebApplicationException. For more information, see [#]_.
* Mock model. All the classes that are accepted as a parameter and returned by the *Resource methods.

The reason the resources contain the whole logic at this moment is because the model may be required to change when
actually working with the back-end. Maybe the resources are also a good long term
place to keep the logic and validation.

In due time, the model should be changed for something different, maybe directly related with GeoStore.

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

.. [#] http://jersey.java.net/
.. [#] http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/new-in-3.0.html#new-feature-rest-support
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e188
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e88
.. [#] http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e435
.. [#] http://jackson.codehaus.org/
.. [#] https://github.com/nfms4redd/nfms-admin-interface/issues/12
