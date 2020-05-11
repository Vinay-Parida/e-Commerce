package com.example.SpringSecurity.scheduler.quartz.controller;

import com.example.SpringSecurity.scheduler.quartz.job.EmailJob;
import com.example.SpringSecurity.scheduler.quartz.payload.ScheduleEmailResponse;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

@Configuration
public class EmailJobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

//    @GetMapping("/sendMailToSellerWithProduct")            //With API HIT
//    @EventListener(ApplicationReadyEvent.class)           //On startup
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail() throws SchedulerException {

//            ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequest.getDateTime(), scheduleEmailRequest.getTimeZone());
//            if(dateTime.isBefore(ZonedDateTime.now())) {
//                ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
//                        "dateTime must be after current time");
//                return ResponseEntity.badRequest().body(scheduleEmailResponse);
//            }

            JobDetail jobDetail = buildJobDetail();
            Trigger trigger = buildJobTrigger(jobDetail);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);

//        } catch (SchedulerException ex) {
//            logger.error("Error scheduling email", ex);
//
//            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
//                    "Error scheduling email. Please try later!");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
//        }
    }

    private JobDetail buildJobDetail() {
//        JobDataMap jobDataMap = new JobDataMap();
//
//        jobDataMap.put("email", scheduleEmailRequest.getEmail());
//        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
//        jobDataMap.put("body", scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
//                .usingJobData(jobDataMap)
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
