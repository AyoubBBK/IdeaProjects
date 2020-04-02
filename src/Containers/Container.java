package Containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.ControllerException;

public class Container {
    public static void main(String[] args) {
        jade.core.Runtime runtime= Runtime.instance();
        Properties p=new ExtendedProperties();
        p.setProperty(ProfileImpl.GUI,"true");
        jade.core.ProfileImpl profileImpl=new ProfileImpl( p);
        jade.wrapper.AgentContainer container =runtime.createMainContainer(profileImpl) ;
        try {
            container.start() ;
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }


}
