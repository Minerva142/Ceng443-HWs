public abstract class State {
    protected BasicAgent work_on; //Agent which effected by this state class

    //cons
    public State(BasicAgent work_on){this.work_on=work_on;}

    //abstract method
    public abstract void step();

}