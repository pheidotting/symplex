package nl.symplex.test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartTest {

    @Test
    @Ignore
    public void testConnectsToDocker() throws Exception {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();

        // Pull an image
        docker.pull("jetty");
        // Bind container ports to host ports
        final String[] ports = {"80", "22"};
        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            List<PortBinding> hostPorts = new ArrayList<>();
            hostPorts.add(PortBinding.of("0.0.0.0", port));
            portBindings.put(port, hostPorts);
        }

        // Bind container port 443 to an automatically allocated available host port.
        List<PortBinding> randomPort = new ArrayList<>();
        randomPort.add(PortBinding.randomPort("0.0.0.0"));
        portBindings.put("443", randomPort);

        final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

        // Create container with exposed ports
        final ContainerConfig containerConfig = ContainerConfig.builder().hostConfig(hostConfig).image("busybox").exposedPorts(ports).cmd("sh", "-c", "while :; do sleep 1; done").build();

        final ContainerCreation creation = docker.createContainer(containerConfig);
        final String id = creation.id();

        // Inspect container
        final ContainerInfo info = docker.inspectContainer(id);

        // Start container
        docker.startContainer(id);

        // Exec command inside running container with attached STDOUT and STDERR
        final String[] command = {"sh", "-c", "ls"};
        final ExecCreation execCreation = docker.execCreate(id, command, DockerClient.ExecCreateParam.attachStdout(), DockerClient.ExecCreateParam.attachStderr());
        final LogStream output = docker.execStart(execCreation.id());
        final String execOutput = output.readFully();

        // Kill container
        docker.killContainer(id);

        // Remove container
        docker.removeContainer(id);

        // Close the docker client
        docker.close();
    }
}
