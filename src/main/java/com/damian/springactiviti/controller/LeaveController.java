package com.damian.springactiviti.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/leave-controller", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Log4j2
public class LeaveController {
    private final RuntimeService runtimeService;
    private final org.flowable.engine.TaskService taskService;

    @PostMapping(path = "/start-leave-process/{days}")
    public String startLeaveProcess(@PathVariable int days) {
        var data = new java.util.HashMap<String, Object>();
        data.put("days", days);
        var processInstance = runtimeService.startProcessInstanceByKey("leave-approval", data);
        return "Leave process started. Process Instance ID = " + processInstance.getId() +
                ", Number of currently running process instances = " +
                runtimeService.createProcessInstanceQuery().count();

    }

    @PostMapping(path = "/complete-manager-tasks/{processInstanceId}")
    public String completeManagerTasks(@PathVariable String processInstanceId) {
        var task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("managerApproval")
                .singleResult();

        if (task != null) {
            log.info("Manager task found: {} ", task.getName());
            var taskVars = new HashMap<String, Object>();
            taskVars.put("isApproved", true);
            taskService.complete(task.getId(), taskVars);

            log.info("Manager approval completed for task ID: {}", task.getId());

        } else {
            log.warn("No manager task found for process instance: {}", processInstanceId);
        }
        return "Manager task completed for process instance: " + processInstanceId +
                ", Number of currently running process instances = " +
                runtimeService.createProcessInstanceQuery().count();
    }
}
