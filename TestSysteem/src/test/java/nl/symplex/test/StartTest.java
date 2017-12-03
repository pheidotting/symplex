package nl.symplex.test;

import org.junit.Test;

public class StartTest {

    @Test
    public void testConnectsToDocker() throws Exception {
        //        String imageNaam = "niaquinto/jetty";
        //
        //        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        //
        //        // Pull an image
        //        System.out.println("pull docker image " + imageNaam);
        //        docker.pull(imageNaam);
        //        System.out.println("# pull docker image " + imageNaam);
        //        // Bind container ports to host ports
        //        final String[] ports = {"8080", "22"};
        //        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        //        //        for (String port : ports) {
        //        List<PortBinding> hostPorts = new ArrayList<>();
        //        hostPorts.add(PortBinding.of("localhost", "1234"));
        //        portBindings.put("8080", hostPorts);
        //        //        }
        //
        //        //        PortBinding portBinding=new PortBinding() {
        //        //            @Override
        //        //            public String hostIp() {
        //        //                return "localhost";
        //        //            }
        //        //
        //        //            @Override
        //        //            public String hostPort() {
        //        //                return "1234";
        //        //            }
        //        //        };
        //        //
        //        //        portBindings.put("8080",portBinding);
        //
        //        // Bind container port 443 to an automatically allocated available host port.
        //        //        List<PortBinding> randomPort = new ArrayList<>();
        //        //        randomPort.add(PortBinding.randomPort("0.0.0.0"));
        //        //        portBindings.put("8080", randomPort);
        //
        //        final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).appendBinds("/Users/patrickheidotting/Documents/jetty:/opt/jetty").build();
        //
        //        // Create container with exposed ports
        //        final ContainerConfig containerConfig = ContainerConfig.builder().hostConfig(hostConfig).image(imageNaam).exposedPorts(ports).cmd("sh", "-c", "while :; do sleep 1; done").build();
        //
        //        final ContainerCreation creation = docker.createContainer(containerConfig);
        //        final String id = creation.id();
        //
        //        // Inspect container
        //        final ContainerInfo info = docker.inspectContainer(id);
        //
        //        try {
        //            // Start container
        //            docker.startContainer(id);
        //
        //            //FileCopy
        //            FileUtils.copyFile(new File("/Users/patrickheidotting/Documents/DEV/Java/Symplex/symplex/Relatiebeheer/target/dejonge.war"), new File("/Users/patrickheidotting/Documents/jetty/webapps/dejonge.war"));
        //            FileUtils.copyFile(new File("/Users/patrickheidotting/Documents/DEV/Java/Symplex/symplex/Relatiebeheer/src/main/resources/tst2/djfc.app.properties"), new File("/Users/patrickheidotting/Documents/jetty/djfc.app.properties"));
        //            FileUtils.copyFile(new File("/Users/patrickheidotting/Documents/DEV/Java/Symplex/symplex/Relatiebeheer/src/main/resources/tst2/djfc.log4j.xml"), new File("/Users/patrickheidotting/Documents/jetty/djfc.log4j.xml"));
        //
        //            //    System.out.println(  "http://"+ randomPort.get(0).hostIp()+":"+    randomPort.get(0).hostPort());
        //
        //            Thread.sleep(60000);
        //
        //            // Exec command inside running container with attached STDOUT and STDERR
        //            final String[] command = {"sh", "-c", "ls /opt/jetty/webapps"};
        //            final ExecCreation execCreation = docker.execCreate(id, command, DockerClient.ExecCreateParam.attachStdout(), DockerClient.ExecCreateParam.attachStderr());
        //            final LogStream output = docker.execStart(execCreation.id());
        //            final String execOutput = output.readFully();
        //
        //            System.out.println(execOutput);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        } finally {
        //            // Kill container
        //            docker.killContainer(id);
        //
        //            // Remove container
        //            docker.removeContainer(id);
        //
        //            // Close the docker client
        //            docker.close();
        //        }
    }
}
