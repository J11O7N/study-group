package studymatch.ui.fx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.selection.SelectionResult;
import studymatch.domain.time.TimeMask;
import studymatch.domain.time.TimeMaskFormatter;
import studymatch.domain.time.TimeMaskParser;
import studymatch.error.ValidationException;
import studymatch.service.StudyGroupService;

import java.util.List;
import java.util.Set;

public class MainViewController {

    // UI 요소들
    @FXML private TextField leaderNameField;
    @FXML private ChoiceBox<String> leaderFieldChoice;
    @FXML private ListView<String> regionListView;
    @FXML private TextField timeExpressionField;
    @FXML private TextField keywordsField;
    @FXML private Spinner<Integer> applicantCountSpinner;

    @FXML private Label commonTimeLabel;
    @FXML private TableView<Applicant> applicantTable;
    @FXML private TableColumn<Applicant, String> nameColumn;
    @FXML private TableColumn<Applicant, String> fieldColumn;
    @FXML private TableColumn<Applicant, String> regionsColumn;
    @FXML private TableColumn<Applicant, String> timeColumn;
    @FXML private TableColumn<Applicant, String> keywordsColumn;

    @FXML private TextArea logArea;

    // 비즈니스 로직 서비스
    private final StudyGroupService service = new StudyGroupService();

    @FXML
    public void initialize() {
        // ChoiceBox
        leaderFieldChoice.setItems(FXCollections.observableArrayList(
                "백엔드", "프론트엔드", "안드로이드"
        ));
        leaderFieldChoice.getSelectionModel().selectFirst();

        // 지역 리스트
        regionListView.setItems(FXCollections.observableArrayList(
                "강남", "홍대", "신촌", "건대"
        ));
        regionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Spinner 초기화
        applicantCountSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 300, 100)
        );

        // TableView 컬럼 설정
        nameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().name())
        );
        fieldColumn.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().field()));

        regionsColumn.setCellValueFactory(cd ->
                new SimpleStringProperty(
                        String.join(", ", cd.getValue().region())
                ));

        timeColumn.setCellValueFactory(cd ->
                new SimpleStringProperty(
                        TimeMaskFormatter.format(cd.getValue().availableTime())
                ));

        keywordsColumn.setCellValueFactory(cd ->
                new SimpleStringProperty(
                        String.join(", ", cd.getValue().motivation())
                ));
    }

    @FXML
    void onRunMatch() {
        try {
            Leader leader = buildLeaderFromForm();
            int count = applicantCountSpinner.getValue();

            List<Applicant> pool = service.generateApplicants();
            SelectionResult result = service.matchStudyGroup(leader, pool);

            renderResult(result);

        } catch (ValidationException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("알 수 없는 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Leader buildLeaderFromForm() {
        String name = leaderNameField.getText();
        String field = leaderFieldChoice.getValue();
        List<String> regions = regionListView.getSelectionModel().getSelectedItems();

        if (name == null || name.isBlank())
            throw new ValidationException("[ERROR] 리더 이름을 입력하세요.");

        if (regions.isEmpty())
            throw new ValidationException("[ERROR] 최소 1개 이상 지역을 선택하세요.");

        TimeMask timeMask = TimeMaskParser.parse(timeExpressionField.getText());

        List<String> keywords = List.of(keywordsField.getText().split(","))
                .stream().map(String::trim).filter(s -> !s.isEmpty()).toList();

        return new Leader(name, field, Set.copyOf(regions), timeMask, keywords);
    }

    private void renderResult(SelectionResult result) {
        String formatted = TimeMaskFormatter.format(result.commonTime());
        commonTimeLabel.setText("공통 시간: " + formatted);

        applicantTable.setItems(FXCollections.observableArrayList(result.selectedApplicants()));

        StringBuilder sb = new StringBuilder();
        sb.append("[선정 결과]\n")
                .append("- 리더: ").append(result.leader().name())
                .append(" / ").append(result.leader().field()).append("\n")
                .append("- 공통 시간: ").append(formatted).append("\n")
                .append("- 인원: ").append(result.selectedApplicants().size()).append("명\n");

        if (result.recommendCafe() != null) {
            sb.append("📍 추천 장소: ")
                    .append(result.recommendCafe().name())
                    .append(" (").append(result.recommendCafe().region()).append(")\n");
        }

        logArea.setText(sb.toString());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("오류");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
