public class Shake extends State {
    private int belirleyici=0; //value for choosing which change executes
    public Shake(BasicAgent work_on) {
        super(work_on);
    }

    @Override
    public void step() {
        //changes coordinates according to value at belirleyici
        if(belirleyici==0){
            work_on.position.setX(work_on.position.getIntX()-2);
            work_on.position.setY(work_on.position.getIntY()-2);
        }
        if(belirleyici==1){
            work_on.position.setX(work_on.position.getIntX()+2);
            work_on.position.setY(work_on.position.getIntY()+2);
        }
        //changes belirleyici for each step
        belirleyici++;
        if(belirleyici==3)
            belirleyici=0;
    }

}