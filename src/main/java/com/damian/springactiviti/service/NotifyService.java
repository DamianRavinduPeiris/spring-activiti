package com.damian.springactiviti.service;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotifyService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        log.info("Executing NotifyService for process instance: {}", execution.getProcessInstanceId());
        var status = execution.getVariable("isApproved");
        if (status != null) {
            log.info("isApproved by manager: {}", status);
        } else {
            log.warn("No 'isApproved' variable found in execution context.");
        }
    }
}
