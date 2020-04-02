package Containers;

import jade.core.ProfileImpl;
        import jade.core.Runtime;
        import jade.wrapper.AgentContainer;
        import jade.wrapper.ControllerException;

public class SimpleContainer {
    public static void main(String[] args) {
        Runtime runtime=Runtime.instance();
        ProfileImpl profileimpl=new ProfileImpl();
        profileimpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");//ceci est le main container
        AgentContainer container=runtime.createAgentContainer(profileimpl);
        try {
            container.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
}
