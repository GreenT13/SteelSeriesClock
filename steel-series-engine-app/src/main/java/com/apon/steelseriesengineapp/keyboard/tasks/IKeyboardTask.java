package com.apon.steelseriesengineapp.keyboard.tasks;

import java.util.List;
import java.util.Optional;

public interface IKeyboardTask {
    class Action {
        private final String url;
        private final String message;

        public Action(String url, String message) {
            this.url = url;
            this.message = message;
        }

        public String getUrl() {
            return url;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Returns how often the task should run in ms. If zero or lower is used, the task will only be executed once and not executed periodically.
     */
    int getPeriod();

    /**
     * Determines which action needs to be executed. This will be called every {@link #getPeriod()}.
     * @return The {@link Action} to post to the keyboard. If empty, nothing will be sent.
     */
    Optional<Action> run();

    /**
     * Returns a list of actions that need to be executed before the task can run.
     */
    List<Action> prepare();
}
