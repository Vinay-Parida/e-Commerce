package com.example.SpringSecurity.scheduler.quartz.controller;

import com.example.SpringSecurity.scheduler.quartz.job.EmailJob;
import com.example.SpringSecurity.scheduler.quartz.payload.ScheduleEmailResponse;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

@Configuration
public class EmailJobSchedulerController {

    @Autowired
    private Scheduler scheduler;

//    @GetMapping("/sendMailToSellerWithProduct")            //With API HIT
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail() throws SchedulerException {

            JobDetail jobDetail = buildJobDetail();
            Trigger trigger = buildJobTrigger(jobDetail);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);

    }

    private JobDetail buildJobDetail() {
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(new Date())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
