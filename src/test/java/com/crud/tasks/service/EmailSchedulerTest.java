package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.scheduler.EmailScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Test (expected = Exception.class)
    public void shouldSendEmail() {
        Mail mail = new Mail("test@test.com", "Test subject", "Test message", "");

        doThrow(new Exception()).when(simpleEmailService).send(mail);

        verify(simpleEmailService, times(1)).send(mail);
    }
}
