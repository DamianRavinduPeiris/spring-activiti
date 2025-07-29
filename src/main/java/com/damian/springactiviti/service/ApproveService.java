package com.damian.springactiviti.service;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ApproveService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Executing ApproveService for process instance: {}", execution.getProcessInstanceId());
        var days = (Integer) execution.getVariable("days");
        if (days != null) {
            log.info("Days requested for leave: {}", days);
        } else {
            log.warn("No 'days' variable found in execution context.");
        }
    }
}
