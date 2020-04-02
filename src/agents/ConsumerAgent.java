package agents;

import Containers.ConsumerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class ConsumerAgent extends GuiAgent {
    private transient ConsumerContainer gui;//reference du conteneur dans l'agent; transient ce n'est pas serializable
    @Override
    protected void setup() {
        if(getArguments().length==1) {//migration d'agent sans interface!
            gui = (ConsumerContainer) getArguments()[0];
        gui.setConsumerAgent(this);// transmission de l'agent vers l'interface
        }
       /* //simillaire a un constructeur
        //Notes
        //methode pour demarrage inutule pour le moment
            *//*System.out.println("===========");
            System.out.println("Agent Init"+this.getAID().getName());
            System.out.println("param"+getArguments()[0]);//recuperation des argument; getArguments.length : nb d'argument envoye
            System.out.println("===========");*//*
        //methode contient l'action a executer jusqu'a que done retourne true
        //ParallelBehaviour pb = new ParallelBehaviour();
        //pb.addSubBehaviour(new Behaviour);
        //addBehaviour(pb);
        //behaviour types :
        //0-Behavior : on a les deux methode done et action et c'est a nous de les programme
        //1-OneShot Behaviour : s'execute une seul fois => done return true
        //2-Cyclic Behaviour : s'execute toujours => done return true
        //3-Waker Behaviour : execute la methode on wake a un moment donne [periode specifier en milliseconde
        //4-Ticker Behaviour : execute onTick periodiquement [ on passe this et la periode en milliseconde ]
        //Warning : a l'interieur de chaque behaviour en utilise pas this pour refencier l'agent mais plutot myagent ; this est passer en parametre a tous les behaviours*/

        ParallelBehaviour pb=new ParallelBehaviour();
//        pb.addSubBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
//                System.out.println("this is a one shot behaviour and will be only fired once");
//            }
//        });
        pb.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
//                System.out.println("this is a cyclic behaviour and it will be running non stop");
//                this will not allow other behaviours to appear
                ACLMessage aclMessage=receive();
                if(aclMessage!=null){
                    /*System.out.println("===============");
                    System.out.println("Receiving messages");
                    System.out.println(aclMessage.getContent());
                    System.out.println(aclMessage.getSender().getName());
                    System.out.println(aclMessage.getPerformative());
                    System.out.println(aclMessage.getLanguage());
                    System.out.println(aclMessage.getOntology());
                    System.out.println("===============");*/
                    if(aclMessage.getPerformative()==ACLMessage.CONFIRM)
                    gui.logMessage(aclMessage);
                    //REPLY
//                    ACLMessage reply=aclMessage.createReply();
//                    reply.setContent("This is being sent on the holiday");
//                    send(reply);
                }
                else{
                    block();//subscribe> notify me when there is updatessubscribe> notify me when there is updates i'm no longer looking for messages
                }
            }
        });
    /*    pb.addSubBehaviour(new WakerBehaviour(this,30000) {
            @Override
            protected void onWake() {
                System.out.println("this is a waker behaviour and will fire after 30 seconds of launching the agen");
            }
        });
        pb.addSubBehaviour(new TickerBehaviour(this,20000) {
            @Override
            protected void onTick() {
                System.out.println("this is a Ticker behaviour and it will be fired each 20 seconds");
            }
        });*/
        addBehaviour(pb);
    }

    @Override
    protected void beforeMove() {
        //methode qui s'executent avant de migrer a un autre conteneur
        System.out.println("===========");
        System.out.println("AvangtMigration");
        System.out.println("===========");

    }

    @Override
    protected void afterMove() {
        //methode qui s'executent apres avoir migrer a un autre conteneur
        System.out.println("===========");
        System.out.println("ApresMigration");
        System.out.println("===========");

    }

    @Override
    protected void takeDown() {
        //Methode qui s'execute avant la mort de l'agent
        System.out.println("===========");
        System.out.println("FareWell ");
        System.out.println("===========");

    }

    @Override
    public void onGuiEvent(GuiEvent guiEvent) {
    if(guiEvent.getType()==1){
        String livre=  guiEvent.getParameter(0).toString();
        ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
//        aclMessage.setPerformative(ACLMessage.REQUEST);remplacer par la ligne au dessus
        aclMessage.setContent(livre);
        aclMessage.addReceiver(new AID("Acheteur",AID.ISLOCALNAME));// ceci est la seul maniere de specifier un agent ... c'est grace a AID
        send(aclMessage);
    }

    }
}
//1-GuiAgent a la place de agent
//2-relation bidirectionnel entre Consumer agent et Consumer Container
//3-transmission de l'interface dans createNewAgent  vers l'agent : de meme dans setup la gui est recuperer est ensuite l'agent est lier au conteneur grace au setter