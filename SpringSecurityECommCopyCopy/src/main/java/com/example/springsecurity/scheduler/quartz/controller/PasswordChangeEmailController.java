package com.example.springsecurity.scheduler.quartz.controller;

import com.example.springsecurity.scheduler.quartz.job.EmailNotResetIn30DaysJob;
import com.example.springsecurity.scheduler.quartz.payload.ScheduleEmailResponse;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class PasswordChangeEmailController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/sendMailToUserToChangePassword")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail() throws SchedulerException {

        JobDetail jobDetail = buildJobDetail();
        Trigger trigger = buildJobTrigger(jobDetail);
        scheduler.scheduleJob(jobDetail, trigger);

        ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
                jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
        return ResponseEntity.ok(scheduleEmailResponse);

    }

    private JobDetail buildJobDetail() {

        return JobBuilder.newJob(EmailNotResetIn30DaysJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs-passwords")
                .withDescription("Send Email Job to change password every 30 days")
//                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail) {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers-new")
                .withDescription("Send Email Trigger")
                .startAt(new Date())
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(00,00))
                .build();
    }

}
