import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Startup extends Application{
    /**
     * Launches JavaFX
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage prmStage) throws Exception {
        Controller contr = new Controller();
        contr.setPrmStage(prmStage);
        contr.launchView();
        prmStage.setTitle("Project - Datalagring");
        prmStage.show();
    }
}