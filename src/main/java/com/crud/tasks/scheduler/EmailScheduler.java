package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ChoiceFormat;

@Component
public class EmailScheduler {

    public static final String SUBJECT = "Tasks: once a day mail";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    public String msg() {
        long size = taskRepository.count();
        final double[] limit = {0, 1, 2};
        final String[] msg = {" tasks", " task", " tasks"};
        final ChoiceFormat m = new ChoiceFormat(limit, msg);
        return size + m.format(size);
    }

    @Scheduled(cron="0 0 10 * * *")
   // @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
            simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "You currently have " + msg(),
                ""
        )); }

}
