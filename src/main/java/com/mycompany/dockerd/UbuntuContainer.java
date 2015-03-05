/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import com.spotify.docker.client.*;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class UbuntuContainer extends Container {
    private Object docker;

    @Override
    void run() {

        try {

          final  DockerClient docker = DefaultDockerClient.fromEnv().build();
            docker.pull("ubuntu");
            final String[] ports = {"80"
            , "22"};
            final ContainerConfig config = ContainerConfig.builder()
                    .image("ubuntu").exposedPorts(ports)
                    .cmd("sh", "-c", "while :; do sleep 1; done")
                    .build();

// bind container ports to host ports
            final Map<String, List<PortBinding>> portBindings = new HashMap<String, List<PortBinding>>();
            for (String port : ports) {
                List<PortBinding> hostPorts = new ArrayList<PortBinding>();
                hostPorts.add(PortBinding.of("0.0.0.0", port));
                portBindings.put(port, hostPorts);
            }
            final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

            final ContainerCreation creation = docker.createContainer(config);
            final String id = creation.id();

// Inspect container
            final ContainerInfo info = docker.inspectContainer(id);

// Start container
            docker.startContainer(id, hostConfig);

// Exec command inside running container with attached STDOUT and STDERR
            final String[] command = {"bash", "-c", "ls"};
            final String execId = docker.execCreate(id, command, DockerClient.ExecParameter.STDOUT, DockerClient.ExecParameter.STDERR);
            final LogStream output = docker.execStart(execId);
            final String execOutput = output.readFully();

// Kill container
            docker.killContainer(id);

// Remove container
            docker.removeContainer(id);
        } catch (DockerException | DockerCertificateException | InterruptedException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
