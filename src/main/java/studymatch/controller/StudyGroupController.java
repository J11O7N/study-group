package studymatch.controller;

import studymatch.service.StudyGroupService;
import studymatch.view.InputView;
import studymatch.domain.person.Leader;
import studymatch.domain.selection.SelectionResult;
import studymatch.view.OutputView;

public class StudyGroupController {
    private final StudyGroupService service;
    private final InputView input;
    private final OutputView output;

    public StudyGroupController(StudyGroupService service, InputView input, OutputView output) {
        this.service = service;
        this.input = input;
        this.output = output;
    }

    public void run() {
        Leader leader = input.inputLeader();
        int count = input.askApplicantCount();
        var pool = service.generateApplicants();
        SelectionResult result = service.matchStudyGroup(leader, pool);
        output.printResult(result);
    }
}
