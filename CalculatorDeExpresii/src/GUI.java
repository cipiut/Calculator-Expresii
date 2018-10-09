import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent> {
    private Button calculeaza;
    private TextField exp;
    private TextArea result;

    private void initValues(){
        calculeaza = new Button("Calculate");
        exp =new TextField();
        exp.setPromptText("ex.: 2+2*4");
        exp.setPrefColumnCount(48);
        result= new TextArea();
        result.setPrefColumnCount(50);
        result.setPrefRowCount(1);
        result.setPromptText("Rezultat");
        result.setEditable(false);
        result.setBackground(Background.EMPTY);
        calculeaza.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        String x1 = exp.getText();
        if(x1.equals(""))x1="0";
        Double x=Calculator.calculate(x1);
        if(!x1.equals("0") && x==null)
            result.setText("Expresia introdusa nu este buna!");
        else if (event.getSource() == calculeaza) result.setText(String.valueOf(x));
    }

    @Override public void start(Stage stage) {
        initValues();
        stage.setTitle("Calculator de Expresii");
        stage.setResizable(false);
        Label pol= new Label("Exp :");
        GridPane calc= new GridPane();
        calc.setPadding(new Insets(10,10,10,10));
        calc.setVgap(5);
        calc.setHgap(5);
        calc.add(pol,0,0);
        calc.add(exp,1,0);
        HBox h= new HBox();
        HBox res= new HBox();
        h.setSpacing(10);
        res.setSpacing(10);
        h.setPadding(new Insets(10,10,10,10));
        res.setPadding(new Insets(10,10,10,10));
        res.getChildren().addAll(result);
        h.getChildren().add(calculeaza);
        VBox v = new VBox();
        v.getChildren().addAll(calc,h,res);
        Scene scene = new Scene(v,710,150);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
