package studymatch;

import studymatch.controller.StudyGroupController;
import studymatch.service.StudyGroupService;
import studymatch.view.InputView;
import studymatch.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView output = new OutputView();
        InputView input   = new InputView();
        StudyGroupService service = new StudyGroupService();

        StudyGroupController controller = new StudyGroupController(service, input, output);
        controller.run();
    }
}
