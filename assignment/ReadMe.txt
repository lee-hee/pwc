1.To run from eclipse import project as a Maven project. Go to Application file and run as Application.
2.Create a docker image 
  2.1 Clean Install the project from eclipse
  2.2 Run "mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)" from the project root location
  2.3 Build the docker image  "docker build -t lilantha/pwc ."
  2.4 Run the docker image "docker run -p 8080:8080 -t lilantha/pwc:latest"