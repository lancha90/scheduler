package co.com.dmha.scheduler.scheduler.conf;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.dmha.scheduler.scheduler.entities.Cron;

@RestController
public class Scheduler {

	@Autowired
	TaskScheduler taskScheduler;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");
		
		IntStream.range(1, 300).forEach(i -> {
			
			int x = new Random().nextInt(59)+1;
			
			Cron cron = new Cron(String.valueOf(x), false, "0/"+x+" * * * * *", "job "+x);
			taskScheduler.schedule(printHour(cron), new CronTrigger(cron.getName()));
		});  

	}

	@RequestMapping(method = RequestMethod.GET, value = "/start")
	public String start() {
		int x = new Random().nextInt(60);
		Cron cron = new Cron(String.valueOf(x), false, "0/"+x+" * * * * *", "job "+x);
		taskScheduler.schedule(printHour(cron), new CronTrigger(cron.getName()));
		return "insert "+x;
	}

	private void print(Cron cron) {
		System.out.println(cron.toString());
	}

	private Runnable printHour(Cron cron) {
		return () -> print(cron);
	}

}
