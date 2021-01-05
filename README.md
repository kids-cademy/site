# kids (a)cademy Portal

kids (a)cademy site hosted at [kids-cademy.com](http://kids-cademy.com/).

This Maven based project has two modules:

- site-server: implements server side logic written in Java and exposed via HTTP-RMI protocol,
- site-client: browser based thin client developed with standard HTML, CSS and ECMAScript.

Server and client code bases are completely separated. The only means of connection on runtime is the protocol HTTP-RMI: client invoke server services via HTTP requests with data encoded on body in _application/json_ format. 
