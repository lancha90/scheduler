package co.com.dmha.scheduler.scheduler.conf;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import co.com.dmha.scheduler.scheduler.entities.Cron;

@Controller
public class Scheduler {

	@Autowired
	TaskScheduler taskScheduler;
	ScheduledFuture<?> scheduledFuture;

	private List<Cron> cron = new ArrayList<>();

	public Scheduler() {

		cron.add(new Cron("1", true, "0/30 * * * * *", "hiper"));
		cron.add(new Cron("2", true, "0/45 * * * * *", "carulla"));
		cron.add(new Cron("3", false, "0/20 * * * * *", "carulla_express"));
		cron.add(new Cron("4", true, "exito_subcorridor_252805", "sony"));
		cron.add(new Cron("5", false, "exito_subcorridor_900525", "alkosto"));

	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");

		cron.forEach(item -> {
			System.out.println(scheduledFuture);
			//scheduledFuture = taskScheduler.schedule(printHour(item), new CronTrigger(item.getName()));

		});

		scheduledFuture = taskScheduler.schedule(printHour(new Cron("5", false, "exito_subcorridor_900525", "alkosto")), EVERY_TEN_SECONDS);
		
	}

	ResponseEntity<Void> generate() {
		scheduledFuture = taskScheduler.schedule(printHour(null), EVERY_TEN_SECONDS);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public static final CronTrigger EVERY_TEN_SECONDS = new CronTrigger("0/10 * * * * *");

	@RequestMapping("start")
	ResponseEntity<Void> start() {
		scheduledFuture = taskScheduler.schedule(printHour(null), EVERY_TEN_SECONDS);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * @RequestMapping("stop") ResponseEntity<Void> stop() {
	 * scheduledFuture.cancel(false); return new
	 * ResponseEntity<Void>(HttpStatus.OK); }
	 */
	private void print(Cron cron) {
		System.out.println("hello " + Instant.now().toEpochMilli());
		System.out.println(cron.toString());
	}

	private Runnable printHour(Cron cron) {
		return () -> print(cron);
	}

}
