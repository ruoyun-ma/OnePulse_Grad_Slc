package rs2d.sequence.onpulsegradslc;


import rs2d.spinlab.sequence.Sequence;
import rs2d.spinlab.sequence.element.TimeElement;

public enum Events {
    Start
            (0, "Time1"),
    Pulse
            (5, S.Time_tx.name()),
    Delay
            (10, S.Time_te_delay.name()),
    Acq
            (11, S.Time_rx.name()),
    FIRDelay
            (12, S.Time_FIR_delay.name()),
    End
            (16, S.Time_last_delay.name());

    public final int ID;
    public final String shortcutName;

    Events(int id, String sname) {
        this.shortcutName = sname;
        ID = id;
    }

    static boolean  checkEventShortcut(Sequence sequence)throws Exception{
        Events[] interfaceFields = Events.values();
        for (Events f : interfaceFields) {

            if ( !f.shortcutName.equals(((TimeElement) sequence.getTimeChannel().get(f.ID)).getTime().getName()) ){

                String message = "PSD Event Error\n" +
                        " Shortcut of time ID#" + f.ID + " is not " + f.shortcutName+ "   but is "+((TimeElement) sequence.getTimeChannel().get(f.ID)).getTime().getName()
                        + " \n Check PSD Events and Events-Class";
                System.out.println(message);
                throw new Exception( message);
            }
        }
        return false;
    }
}