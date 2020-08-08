package com.apon.steelseriesengineapp.keyboard;

import com.apon.steelseriesengineapp.keyboard.tasks.IKeyboardTask;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Component
public class KeyboardTaskScheduler {
    private final TaskScheduler taskScheduler;
    private final KeyboardServer keyboardServer;

    public KeyboardTaskScheduler(TaskScheduler taskScheduler, KeyboardServer keyboardServer) {
        this.taskScheduler = taskScheduler;
        this.keyboardServer = keyboardServer;

        // On setup, always register the game.
        initializeGame();
    }

    private void initializeGame() {
        keyboardServer.post(new IKeyboardTask.Action("game_metadata",
                "{ " +
                        "\"game\": \"SPRING\"," +
                        "\"game_display_name\": \"Configure what you show\"," +
                        "\"developer\": \"Rico Apon\"" +
                        "}"));
    }

    private ScheduledFuture<?> scheduledFuture;

    /**
     * Schedules a task to run. If a task was already scheduled, stop that task.
     * @param keyboardTask The task.
     */
    public void schedule(IKeyboardTask keyboardTask) {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        // If prepare actions are needed, execute these first.
        Optional.ofNullable(keyboardTask.prepare()).ifPresent(
                (list) -> list.forEach(keyboardServer::post)
        );

        // If the period is 0 or lower, we execute it only once.
        if (keyboardTask.getPeriod() <= 0) {
            Optional<IKeyboardTask.Action> action = keyboardTask.run();
            action.ifPresent(keyboardServer::post);
        } else {
            // Schedule the task at the given period.
            scheduledFuture = taskScheduler.scheduleAtFixedRate(() -> {
                Optional<IKeyboardTask.Action> action = keyboardTask.run();
                action.ifPresent(keyboardServer::post);
            }, keyboardTask.getPeriod());
        }

    }
}
