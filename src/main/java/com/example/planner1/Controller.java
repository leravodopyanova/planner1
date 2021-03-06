package com.example.planner1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.CheckClass;
import model.DrawStudies;
import model.Rectan;
import model.tableViewPlan;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;

import static javafx.embed.swing.SwingFXUtils.fromFXImage;

public class Controller implements Initializable {
    public TextField tfDisciplineSt;
    public CheckBox chbIsDone;
    public Canvas canvas1;
    public GraphicsContext gr;
    public Canvas canvas2;
    public Canvas canvas3;
    public Canvas canvas4;
    public Canvas canvas5;
    public Canvas canvas6;
    public Canvas canvas7;
    public Label lblMonth;
    public Label lblWeekNom;
    public Button btnSave;
    public Button btnClear;
    public Button btnDiagram;
    public Button btnRight;
    public Button btnLeft;
    public Button toNext;
    public Label lblColorBlock;
    public GridPane gridPaneNode = new GridPane();
    private final ObservableList<String> extensions = FXCollections.observableArrayList("Excel", "PNG", "JPEG");
    public ComboBox cmbTypeFile;
    public Button btnClose11;
    public Button btnClose111;
    private ObservableList<tableViewPlan> tableViewPlans = FXCollections.observableArrayList();
    public TableView <tableViewPlan>tvTablePlans;
    public TableColumn <tableViewPlan, Integer> colNo;
    public TableColumn <tableViewPlan, LocalDate> colDate;
    public TableColumn <tableViewPlan, String> colFrom;
    public TableColumn <tableViewPlan, String> colTo;
    public TableColumn <tableViewPlan, String> colDiscipl;
    public TableColumn <tableViewPlan, String> colStatus;
    public TableColumn <tableViewPlan, String> colComm;
    public TableColumn <tableViewPlan, String> colColor;
    public TableColumn <tableViewPlan, Integer> colWeekNo;
    private SnapshotParameters ssp;
    public DatePicker dpDate;
    public Button btnCloseSt;
    public Button btnCloseM;
    public TextField tfNameDiscipline;
    public TextField tfAcademicHours;
    public Button btnAddPlan;
    public Button btnDeletePlan;
    public Button btnSavePlan;
    public Button btnAddToPlanM;
    public ColorPicker cpBlockColor;
    public TextField tfFromTime;
    public TextField tfToTime;
    public TextArea taComment;
    public Button btnAddToPlanSt;
    @FXML
    private TextField tfProjectNameSt;
    @FXML
    private TextField tfProjectNameM;
    private Calendar calendar = Calendar.getInstance();
    private LocalDate date;
    private Locale locale = Locale.US;
    private GregorianCalendar gnow = new GregorianCalendar();
    private int countCol = 0;
    private int weekNo;
    int key = 0;
    int amount = 7;
    private String filename = "C:\\Users\\????????\\Downloads\\??????????1.xlsx";
    private CheckClass checkClass = new CheckClass();
    private Rectan rectan;
    private DrawStudies drawStudies = new DrawStudies();
    private String canvasDate = "";
    private Stage stage;
    private DirectoryChooser directoryChooser;
    private File selectedDirectory;
    private String saveDir = "C:\\";
    private HashMap<Canvas, String> canvasDateMap = new HashMap<>();
    private Alert alertSaveDir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseSaveDir();
        setToolTips();

        cmbTypeFile.setItems(extensions);
        weekNo = LocalDateTime.now().get(WeekFields.of(locale).weekOfWeekBasedYear()) - 1;
        lblMonth.setText(getLocaleMonth(calendar));
        lblWeekNom.setText("???????????? " + weekNo);
        ssp = new SnapshotParameters();

        // ?????? ?????????????????????? ?????????? ???????????????? ??????????????
        tfProjectNameSt.setStyle("-fx-text-fill: #AFEEEE; -fx-background-color: rgba(0,0,0,0);");
        //  tfProjectNameM.setStyle("-fx-text-fill: #AFEEEE; -fx-background-color: rgba(0,0,0,0);");

        // ???????????????? ???? ????????????
        btnCloseSt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

       /* btnCloseM.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
        */

        // ???????????????????? tableView


        rectan = new Rectan(Color.TRANSPARENT, 10, 10, 0, 0);
        gr = canvas1.getGraphicsContext2D();

        // ???????????? ?????????? ?????? ?????????? ??????????????????
        canvas2.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas2.getGraphicsContext2D().fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        canvas4.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas4.getGraphicsContext2D().fillRect(0, 0, canvas4.getWidth(), canvas4.getHeight());
        canvas6.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas6.getGraphicsContext2D().fillRect(0, 0, canvas6.getWidth(), canvas6.getHeight());

        loadGanttDiagram();
    }

    public void btnAddToPlanM_OnAction(ActionEvent actionEvent) {

    }

    public void btnAddToPlanSt_OnAction(ActionEvent actionEvent) {
        init_Data();
        colNo.setCellValueFactory(new PropertyValueFactory<tableViewPlan, Integer >("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<tableViewPlan, LocalDate>("2000-02-02"));
        colDiscipl.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("????????????????????"));
        colFrom.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("2000"));
        colTo.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("2000"));
        colStatus.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("V"));
        colComm.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("Comment1"));
        colColor.setCellValueFactory(new PropertyValueFactory<tableViewPlan, String>("red"));
        colWeekNo.setCellValueFactory(new PropertyValueFactory<tableViewPlan, Integer>("????????????1"));

        tvTablePlans.setItems(tableViewPlans);
    }

    private void init_Data() {
        date = dpDate.getValue();
        weekNo = date.get(WeekFields.of(locale).weekOfWeekBasedYear());
        countCol++;
        tableViewPlans.add(new tableViewPlan(countCol, dpDate.getValue(),
                tfDisciplineSt.getText(), tfFromTime.getText(), tfToTime.getText(),
                checkClass.checkStatus(chbIsDone.isSelected()),
                taComment.getText(), cpBlockColor.getValue(), weekNo));
    }

    public void onCanva1_clicked(MouseEvent mouseEvent) {
        gr = canvas1.getGraphicsContext2D();
        tfFromTime.setText("");
        tfToTime.setText("");
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva2_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas2.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva3_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas3.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva4_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas4.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva5_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas5.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva6_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas6.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }

    public void onCanva7_clicked(MouseEvent mouseEvent) {
        tfFromTime.setText("");
        tfToTime.setText("");
        gr = canvas7.getGraphicsContext2D();
        drawStudies.drawActivity(gr, mouseEvent, rectan, tfFromTime, tfToTime);
    }
    public String getLocaleMonth(Calendar calendar){
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY){
            return "????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY){
            return "??????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.MARCH){
            return "????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.APRIL){
            return "????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.MAY){
            return "??????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.JUNE){
            return "????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.JULY){
            return "????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.AUGUST){
            return "????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER){
            return "????????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.OCTOBER){
            return "??????????????";
        }
        else if(calendar.get(Calendar.MONTH) == Calendar.NOVEMBER){
            return "????????????";
        }
        else {
            return "??????????????";
        }
    }
    public String getDateString(){
        String dateStr = calendar.getTime().toString();
        dateStr = dateStr.substring(4, 11) + dateStr.substring((dateStr.length() - 4));
        return dateStr;
    }
    public void loadGanttDiagram(){
        canvasDateMap.clear();
        clearDiagram();

        lblWeekNom.setText("???????????? " + weekNo);

        calendar.setWeekDate(2022, weekNo, Calendar.MONDAY);
        setCanvasColor(canvas1);

        calendar.setWeekDate(2022, weekNo, Calendar.TUESDAY);
        setCanvasColor(canvas2);

        calendar.setWeekDate(2022, weekNo, Calendar.WEDNESDAY);
        setCanvasColor(canvas3);

        calendar.setWeekDate(2022, weekNo, Calendar.THURSDAY);
        setCanvasColor(canvas4);

        calendar.setWeekDate(2022, weekNo, Calendar.FRIDAY);
        setCanvasColor(canvas5);

        calendar.setWeekDate(2022, weekNo, Calendar.SATURDAY);
        setCanvasColor(canvas6);

        calendar.setWeekDate(2022, weekNo, Calendar.SUNDAY);
        setCanvasColor(canvas7);

        lblMonth.setText(getLocaleMonth(calendar));
    }

    public void btnLeftClick(ActionEvent actionEvent) {
        weekNo--;
        loadGanttDiagram();
    }

    public void btnRightClick(ActionEvent actionEvent) {
        weekNo++;
        loadGanttDiagram();
    }

    public void setCanvasColor(Canvas cnv){
        canvasDateMap.put(cnv, getDateString());
        cnv.getGraphicsContext2D().setFill(Color.ORANGE);
        cnv.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
        cnv.getGraphicsContext2D().setFont(Font.font("Comic Sans MS", 18));
        cnv.getGraphicsContext2D().setTextBaseline(VPos.CENTER);
        cnv.getGraphicsContext2D().fillText(
                getDateString(),
                Math.round(cnv.getWidth()  / 2),
                Math.round(cnv.getHeight() / 2)
        );
    }

    public void clearDiagramOnAction(ActionEvent actionEvent) {
        tfToTime.setText("");
        tfFromTime.setText("");
        clearDiagram();
        loadGanttDiagram();
    }

    public void clearDiagram(){
        canvas1.getGraphicsContext2D().setFill(Color.PAPAYAWHIP);
        canvas1.getGraphicsContext2D().fillRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
        canvas2.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas2.getGraphicsContext2D().fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        canvas3.getGraphicsContext2D().setFill(Color.PAPAYAWHIP);
        canvas3.getGraphicsContext2D().fillRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        canvas4.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas4.getGraphicsContext2D().fillRect(0, 0, canvas4.getWidth(), canvas4.getHeight());
        canvas5.getGraphicsContext2D().setFill(Color.PAPAYAWHIP);
        canvas5.getGraphicsContext2D().fillRect(0, 0, canvas5.getWidth(), canvas5.getHeight());
        canvas6.getGraphicsContext2D().setFill(Color.LIGHTGOLDENRODYELLOW);
        canvas6.getGraphicsContext2D().fillRect(0, 0, canvas6.getWidth(), canvas6.getHeight());
        canvas7.getGraphicsContext2D().setFill(Color.PAPAYAWHIP);
        canvas7.getGraphicsContext2D().fillRect(0, 0, canvas7.getWidth(), canvas7.getHeight());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveAsPng(gridPaneNode, tfProjectNameSt.getText(), ssp);
    }

    public void btnDiagramOnAction(ActionEvent actionEvent) {
    }
    public void chooseSaveDir(){
        alertSaveDir = new Alert(Alert.AlertType.INFORMATION);
        stage = new Stage();
        directoryChooser = new DirectoryChooser();

        alertSaveDir.setTitle("???????? ?????? ???????????????????? ????????????");
        alertSaveDir.setHeaderText("????????????????!");

        directoryChooser.setTitle("???????????????? ?????????????? ?????? ????????????????????");
        selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
          saveDir = selectedDirectory.getAbsolutePath();
        }

        alertSaveDir.setContentText("?????? ???????????????????? ???????????? ???????? " + saveDir + "\n???????? ???????????? ???????????????? ????????, ?????????????????????????? ??????????????????.");
        alertSaveDir.showAndWait();
    }
    public void setToolTips(){
        btnSave.setTooltip(new Tooltip("?????????????????? ??????????????????"));
        btnClear.setTooltip(new Tooltip("???????????????? ?????????????????? ??????????"));
        btnDiagram.setTooltip(new Tooltip("?????????????????? ?????????????????? ?????? ??????????????"));
        btnRight.setTooltip(new Tooltip("?????????????? ?????????????????? ???? ???????????? ????????????"));
        btnLeft.setTooltip(new Tooltip("?????????????? ?????????????????? ???? ???????????? ??????????"));
        toNext.setTooltip(new Tooltip("?????????????? ???? ???????????????? ??????????????????"));
        btnAddToPlanSt.setTooltip(new Tooltip("???????????????? ?????????????? ?? ???????? (?????????????? ????????)"));
        btnCloseSt.setTooltip(new Tooltip("?????????????? ???????? ??????????????????"));
        lblColorBlock.setTooltip(new Tooltip("???????????????? ???????? ?????????????? ?????????? ?? ???????????? ??????????????????????"));
        tfProjectNameSt.setTooltip(new Tooltip("?????? ???????????????? ?????????? ?????????????? ?????? ???????????????????? ??????????"));
    }
    public void saveAsPng(Node node, String fname, SnapshotParameters ssp) {
        WritableImage image = node.snapshot(ssp, null);
        File file = new File(saveDir + "\\" + fname + ".png");
        try {
            ImageIO.write(fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }
}
